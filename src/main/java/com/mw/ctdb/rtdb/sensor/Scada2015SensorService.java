package com.mw.ctdb.rtdb.sensor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mw.base.model.BaseData;
import com.mw.plugins.shiro.ShiroUtils;
import com.mw.utils.BeanValidators;

@Service
public class Scada2015SensorService {
	@Resource
	private Validator validator;
	@Resource
	private Scada2015SensorDao scada2015SensorDao;

	public int save(Scada2015Sensor scada2015Sensor) {
		String username = ShiroUtils.findUserName();
		Date now = new Date();
		
		int rst;
		if (scada2015Sensor.getSeqNo() == null) {
			Integer nextSeqNo = scada2015SensorDao.getNextSeqNo();
			scada2015Sensor.setSeqNo(nextSeqNo);
			scada2015Sensor.setAddDt(now);
			scada2015Sensor.setAddBy(username);
			rst = scada2015SensorDao.add(scada2015Sensor);
		} else {
			scada2015Sensor.setUpdDt(now);
			scada2015Sensor.setUpdBy(username);
			rst = scada2015SensorDao.updById(scada2015Sensor);
		}

		return rst;
	}

	@Transactional("transactionManager")
	public BaseData batchAdd(List<Scada2015Sensor> list) {
		logger.info("batchAdd validate start");
		//start validate
		int errTt = 0;
		StringBuilder errMsg = new StringBuilder();
		for (Scada2015Sensor bean : list) {
			try {
				BeanValidators.validateWithException(validator, bean);
			} catch (ConstraintViolationException e) {
				List<String> msgList = BeanValidators.extractPropertyAndMessageAsList(e, ": ");
				for (String msg : msgList) {
					errTt++;
					errMsg.append("<br/>").append(msg);
				}
			} catch (Exception e) {
				logger.warn("validate throw other exception", e);
				errTt++;
				errMsg.append("<br/>validate failure: ").append(e.getMessage());
			}
		}
		if (errTt > 0) {
			errMsg.insert(0, errTt + " rows data validate failure: ");
			return new BaseData("101", errMsg.toString());
		}
		logger.info("batchAdd validate end");

		logger.info("batchAdd save start");
		//start save
		int susTt = 0;
		for (Scada2015Sensor bean : list) {
			save(bean);
			susTt++;
		}
		logger.info("batchAdd save end");

		return new BaseData("0", susTt + " rows data saved");
	}

	private static final Logger logger = LoggerFactory.getLogger(Scada2015SensorService.class);
}
