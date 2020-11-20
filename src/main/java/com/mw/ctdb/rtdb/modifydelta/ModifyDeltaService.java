package com.mw.ctdb.rtdb.modifydelta;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.Precision;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mw.base.BaseConsts;
import com.mw.base.exception.ServiceException;
import com.mw.base.model.BaseData;
import com.mw.ctdb.CTDBConsts;
import com.mw.ctdb.rtdb.modifylog.ModifyLog;
import com.mw.ctdb.rtdb.modifylog.ModifyLogDao;
import com.mw.ctdb.rtdb.modifylog.ModifyLogService;
import com.mw.plugins.shiro.ShiroUtils;
import com.mw.utils.Arith;
import com.mw.utils.BeanValidators;
import com.mw.utils.DateTimeUtils;

@Service
public class ModifyDeltaService {
	@Resource
	public Validator validator;
	@Resource
	private ModifyDeltaDao modifyDeltaDao;
	@Resource
	private ModifyLogService modifyLogService;
	@Resource
	private ModifyLogDao modifyLogDao;

	private final static Integer MIN_HOUR = 0;
	private final static Integer MAX_HOUR = 23;

	/**
	 * get original or current field value first
	 * update table to set new field value
	 * insert into log table to save original value or update log table.
	 * 
	 * @param modifyDeltaForm
	 * @return
	 */
	private void saveModifyLog(ModifyDeltaForm modifyDeltaForm, Long batchNb) {
		String userName = ShiroUtils.findUserName();

		String tableName = modifyDeltaForm.getTableName();
		String fieldName = modifyDeltaForm.getFieldName();
		Date sensorDd = modifyDeltaForm.getSensorDd();

		//get value before modify 
		Double oriOrCurFieldValue = modifyDeltaDao.getOriFieldValue(tableName, fieldName, sensorDd);
		//save log
		ModifyLog modifyLog = new ModifyLog(tableName, fieldName, sensorDd);
		modifyLog.setBatchNb(batchNb);
		modifyLog.setOriFieldValue(oriOrCurFieldValue);//the update function will exclude this field. so the field value must be the first added value.
		modifyLog.setNewFieldValue(modifyDeltaForm.getNewFieldValue());
		modifyLog.setModifyReason(modifyDeltaForm.getModifyReason());
		modifyLog.setModifyMethod(modifyDeltaForm.getModifyMethod());
		modifyLog.setModifyBy(userName);
		modifyLog.setModifyDt(new Date());
		modifyLog.setStatusCd(1);
		modifyLogService.save(modifyLog);

		//update
		//		Double newFieldValue = modifyDeltaForm.getNewFieldValue();
		//		int updRst = modifyDeltaDao.updBy(tableName, fieldName, newFieldValue, sensorDd);
		//		if (updRst < 1) {
		//			throw new ServiceException("101", "Update failed");
		//		}
	}

	public BaseData batchSaveModifyLog(List<ModifyDeltaForm> formList) {
		return batchSaveModifyLog(null, formList);
	}

	@Transactional("transactionManager")
	public BaseData batchSaveModifyLog(Long batchNb, List<ModifyDeltaForm> formList) {
		logger.info("Validate start:" + formList.size());
		//start validate
		int errTt = 0;
		StringBuilder errMsg = new StringBuilder();
		for (ModifyDeltaForm modifyDeltaForm : formList) {
			try {
				BeanValidators.validateWithException(validator, modifyDeltaForm);
			} catch (ConstraintViolationException e) {
				List<String> msgList = BeanValidators.extractPropertyAndMessageAsList(e, ": ");
				for (String msg : msgList) {
					errTt++;
					errMsg.append("<br/>").append(msg);
				}
			}
		}
		if (errTt > 0) {
			errMsg.insert(0, errTt + " records validate failed");
			return new BaseData("101", errMsg.toString());
		}
		logger.info("Validate end");

		logger.info("Save start");
		//start save
		Map<String, ModifyDeltaQueryForm> previewQueryFormMap = new LinkedHashMap<String, ModifyDeltaQueryForm>();//may be have multiple fields 

		if (batchNb == null || batchNb == 0) {
			batchNb = modifyLogDao.getNextBatchSeq();
		}
		int susTt = 0;
		for (ModifyDeltaForm modifyDeltaForm : formList) {
			saveModifyLog(modifyDeltaForm, batchNb);
			susTt++;

			if (!previewQueryFormMap.containsKey(modifyDeltaForm.getFieldName())) {
				ModifyDeltaQueryForm modifyDeltaQueryForm = new ModifyDeltaQueryForm(modifyDeltaForm.getTableName(), modifyDeltaForm.getFieldName());
				modifyDeltaQueryForm.setBatchNb(batchNb);
				previewQueryFormMap.put(modifyDeltaForm.getFieldName(), modifyDeltaQueryForm);
			}
		}
		logger.info("Save end");

		BaseData baseData = new BaseData("0", susTt + " records saved");

		try {
			Map<String, String> sensorDtMap = modifyLogDao.getMinMaxSensorDtByBatchNb(batchNb);
			String minSensorDt = sensorDtMap.get("MINSENSORDT");
			minSensorDt = DateTimeUtils.getYyyymmddStr(minSensorDt, -7, DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
			String maxSensorDt = sensorDtMap.get("MAXSENSORDT");
			maxSensorDt = DateTimeUtils.getYyyymmddStr(maxSensorDt, 7, DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);

			for (Map.Entry<String, ModifyDeltaQueryForm> entry : previewQueryFormMap.entrySet()) {
				ModifyDeltaQueryForm modifyDeltaQueryForm = entry.getValue();
				modifyDeltaQueryForm.setBegDt(minSensorDt);
				modifyDeltaQueryForm.setEndDt(maxSensorDt);
			}
		} catch (Exception e) {
			logger.warn("getMinMaxSensorDtByBatchNb", e);
		}

		Map<String, Object> previewFormData = new HashMap<String, Object>();
		previewFormData.put("batchNb", batchNb);
		previewFormData.put("previewQueryFormMap", previewQueryFormMap);
		baseData.setDt(previewFormData);
		return baseData;
	}

	public List<ModifyDeltaView> getModifyLogs(ModifyDeltaQueryForm modifyDeltaQueryForm) throws ParseException {
		Long batchNb = modifyDeltaQueryForm.getBatchNb();

		Map<String, String> sensorDtMap = modifyLogDao.getMinMaxSensorDtByBatchNb(batchNb);
		String minSensorDt = sensorDtMap.get("MINSENSORDT");
		minSensorDt = DateTimeUtils.getYyyymmddStr(minSensorDt, -7, DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
		String maxSensorDt = sensorDtMap.get("MAXSENSORDT");
		maxSensorDt = DateTimeUtils.getYyyymmddStr(maxSensorDt, 7, DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);

		List<ModifyDeltaView> list = modifyDeltaDao.previewBy(modifyDeltaQueryForm);
		return list;
	}

	@Transactional("transactionManager")
	public BaseData updFieldValueByBatchNb(Long batchNb) {
		int ct = modifyLogDao.updStatusByBatchNb(batchNb, 2, new Date());

		int updCt = 0;
		List<ModifyLog> modifyLogList = modifyLogDao.getByBatchNbAndStatusCd(batchNb, 2);
		for (ModifyLog modifyLog : modifyLogList) {
			int rst = saveModifyDelta(modifyLog.getTableName(), modifyLog.getFieldName(), modifyLog.getNewFieldValue(), modifyLog.getSensorDd());//modifyDeltaDao.updBy(modifyLog.getTableName(), modifyLog.getFieldName(), modifyLog.getNewFieldValue(), modifyLog.getSensorDd());
			updCt += rst;
		}

		if (ct != updCt) {
			throw new ServiceException("101", "Invalid data!");
		}

		return new BaseData(BaseConsts.RST_CD.SUS, ct + " records saved.");
	}

	private int saveModifyDelta(String tableName, String fieldName, Double newFieldValue, Date sensorDd) {
		int rst = modifyDeltaDao.updBy(tableName, fieldName, newFieldValue, sensorDd);
		if (rst < 1) {
			rst = modifyDeltaDao.add(tableName, fieldName, newFieldValue, sensorDd);
		}
		return rst;
	}

	/**
	 * 
	 Re: requirement 20150716
	 * Susie CHEN
	 * to:
	 * Jian Xing CUI
	 * 2015/09/01 下午 03:07
	 * 
	 * Dear Xing,
	 * 改數時間段內求和，請基於原始值；
	 * 前3天同期平均數作為中間步驟，可以取整後再進行下一步。
	 * 
	 * @param modifyDeltaQueryForm
	 * @param lastDays
	 * @param formulaFlag
	 * @return
	 * @throws ParseException
	 */
	public List<ModifyDeltaForm> getLastAve(ModifyDeltaQueryForm modifyDeltaQueryForm, int lastDays, int formulaFlag) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);

		DateTime begDateTime = formatter.parseDateTime(modifyDeltaQueryForm.getBegDt());
		String begDt = begDateTime.toString(DateTimeUtils.FORMAT_YYYY_MM_DD);
		int begHour = begDateTime.getHourOfDay();

		DateTime endDateTime = formatter.parseDateTime(modifyDeltaQueryForm.getEndDt());
		String endDt = endDateTime.toString(DateTimeUtils.FORMAT_YYYY_MM_DD);
		int endHour = endDateTime.getHourOfDay();

		String begDtOfLast = begDateTime.minusDays(lastDays).toString(DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
		String endDtOfLast = begDateTime.minusHours(1).toString(DateTimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);

		ModifyDeltaQueryForm modifyDeltaQueryFormOfLast = new ModifyDeltaQueryForm();
		modifyDeltaQueryFormOfLast.setTableName(modifyDeltaQueryForm.getTableName());
		modifyDeltaQueryFormOfLast.setFieldName(modifyDeltaQueryForm.getFieldName());
		modifyDeltaQueryFormOfLast.setBegDt(begDtOfLast);
		modifyDeltaQueryFormOfLast.setEndDt(endDtOfLast);
		List<ModifyDeltaView> modifyDeltaViewListOfLast = modifyDeltaDao.getBy(modifyDeltaQueryFormOfLast);

//		for (ModifyDeltaView modifyDeltaView : modifyDeltaViewListOfLast) {
//			logger.info("modifyDeltaView->{}", modifyDeltaView);
//		}

		Map<Integer, Queue<Double>> hourValMap = new HashMap<Integer, Queue<Double>>();
		if(lastDays == 1){
			if (StringUtils.equals(begDt, endDt)) {
				for (int i = begHour; i <= endHour; i++) {
					hourValMap.put(i, new LinkedList<Double>());
				}
			} else {
				for (int i = begHour; i <= MAX_HOUR; i++) {
					hourValMap.put(i, new LinkedList<Double>());
				}
				for (int i = MIN_HOUR; i <= endHour; i++) {
					hourValMap.put(i, new LinkedList<Double>());
				}
			}
		}else {
			for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
				hourValMap.put(i, new LinkedList<Double>());
			}
		}

		StringBuilder nullExceptionMsg = new StringBuilder();
		for (ModifyDeltaView modifyDeltaView : modifyDeltaViewListOfLast) {
			if (modifyDeltaView.getCurFieldValue() == null) {//根據LiYin要求如果碰到空值則拋出異常並顯示。
				nullExceptionMsg.append(String.format("<br/>Null value: Field name=%s, Date=%s", modifyDeltaView.getFieldName(), modifyDeltaView.getSensorDdStr()));
			}

			int hh = new DateTime(modifyDeltaView.getSensorDd()).getHourOfDay();

			if (hourValMap.containsKey(hh)) {
				hourValMap.get(hh).add(modifyDeltaView.getCurFieldValue());
			}
		}

		if (nullExceptionMsg.length() > 0) {
			throw new ServiceException("1001", nullExceptionMsg.toString());
		}

		List<ModifyDeltaView> listOfEdit = modifyDeltaDao.getBy(modifyDeltaQueryForm);

		double totalOfEditValue = 0d;//改數時間段內求和
		double totalOfAvgValue = 0d;//前3天同期平均數求和

		List<ModifyDeltaForm> modifyDeltaFormList = new ArrayList<ModifyDeltaForm>();
		for (ModifyDeltaView modifyDeltaView : listOfEdit) {
			totalOfEditValue += modifyDeltaView.getOriFieldValue()==null?0:modifyDeltaView.getOriFieldValue();//防止空指針異常

			int hh = new DateTime(modifyDeltaView.getSensorDd()).getHourOfDay();
			double val = cal(hourValMap, hh, lastDays);
			totalOfAvgValue += val;

			ModifyDeltaForm modifyDeltaForm = new ModifyDeltaForm();
			modifyDeltaForm.setTableName(modifyDeltaView.getTableName());
			modifyDeltaForm.setFieldName(modifyDeltaView.getFieldName());
			modifyDeltaForm.setSensorDd(modifyDeltaView.getSensorDd());
			modifyDeltaForm.setNewFieldValue(val);
			modifyDeltaForm.setModifyReason(modifyDeltaQueryForm.getModifyReason());
			modifyDeltaForm.setModifyMethod(modifyDeltaQueryForm.getModifyMethod());

			modifyDeltaFormList.add(modifyDeltaForm);
		}

		if (formulaFlag == CTDBConsts.FORMULA_FLAG.AVG_PCT) {
			for (ModifyDeltaForm modifyDeltaForm : modifyDeltaFormList) {
				double avgValue = modifyDeltaForm.getNewFieldValue();
				double percentValue = Arith.div(avgValue, totalOfAvgValue);//前3天同期比例
				double newValue = Arith.mul(totalOfEditValue, percentValue);
				newValue = Precision.round(newValue, 0);
				modifyDeltaForm.setNewFieldValue(newValue);
			}
		}

		return modifyDeltaFormList;
	}

	private double cal(Map<Integer, Queue<Double>> hourValMap, Integer hour, Integer lastDays) {
		//logger.info("hour-> {}", hour);
		Queue<Double> vals = hourValMap.get(hour);
		//logger.info("vals-> {}", vals);
		double sum = 0;
		for (Double val : vals) {
			sum = Arith.add(sum, val);
		}
		double avg = Precision.round(sum / lastDays, 0);

		if (vals.size() > 0) {
			vals.remove();
		}
		vals.add(avg);
		return avg;
	}

	private static final Logger logger = LoggerFactory.getLogger(ModifyDeltaService.class);
}
