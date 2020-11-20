package com.mw.ctdb.rtdb.sensor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Lists;
import com.mw.base.BaseConsts;
import com.mw.base.model.BaseData;
import com.mw.base.model.BaseQueryForm;
import com.mw.plugins.excel.ExportExcel;
import com.mw.plugins.excel.ImportExcel;
import com.mw.utils.DateTimeUtils;

@Controller
@RequestMapping(value = "/rtdb/scada2015sensor")
public class Scada2015SensorWeb {
	@Resource
	private Scada2015SensorDao scada2015SensorDao;
	@Resource
	private Scada2015SensorService scada2015SensorService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String list() {
		return "rtdb/scada2015Sensor";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody BaseData save(@RequestBody Scada2015Sensor scada2015Sensor) {
		int rst = scada2015SensorService.save(scada2015Sensor);
		if (rst > 0) {
			return new BaseData(BaseConsts.RST_CD.SUS);
		} else {
			return new BaseData("101", "Save failed!");
		}
	}

	@RequestMapping(value = "/delById", method = RequestMethod.POST)
	public @ResponseBody BaseData delById(@RequestParam("seqNo") Integer seqNo) {
		int rst = scada2015SensorDao.delById(seqNo);
		if (rst > 0) {
			return new BaseData(BaseConsts.RST_CD.SUS);
		} else {
			return new BaseData("101", "Delete failed!");
		}
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public @ResponseBody BaseData getBySeqNo(@PathVariable Integer id) {
		Scada2015Sensor scada2015Sensor = scada2015SensorDao.getById(id);
		if (scada2015Sensor != null) {
			return new BaseData(scada2015Sensor, null);
		} else {
			return new BaseData("101", "No data");
		}
	}

	@RequestMapping(value = "/getBy", method = RequestMethod.POST)
	public @ResponseBody BaseData getBy(@RequestBody BaseQueryForm baseQueryForm) {
		List<Scada2015Sensor> scada2015SensorList = scada2015SensorDao.getBy(baseQueryForm.getFfMap(), baseQueryForm.getPage());
		int scada2015SensorListCount = scada2015SensorDao.getCountBy(baseQueryForm.getFfMap());

		return new BaseData(scada2015SensorList, scada2015SensorListCount);
	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public String importExcel(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			ImportExcel importExcel = new ImportExcel(file, 0, 0);
			List<Scada2015Sensor> list = importExcel.getDataList(Scada2015Sensor.class);

			BaseData baseData = scada2015SensorService.batchAdd(list);
			redirectAttributes.addFlashAttribute("msg", baseData.getErrMsg());
		} catch (Exception e) {
			logger.warn("importExcel", e);
			redirectAttributes.addFlashAttribute("msg", "Import excel failed!");
		}
		return "redirect:/msg";
	}

	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			StringBuilder fileName = new StringBuilder("SCADA2015Sensor-");
			fileName.append(DateTimeUtils.date2Str(new Date(), DateTimeUtils.FORMAT_YYYYMMDDHHMMSS));
			fileName.append(".xlsx");

			Map<String, Object> params = WebUtils.getParametersStartingWith(request, null);
			List<Scada2015Sensor> list = scada2015SensorDao.getBy(params, null);

			new ExportExcel(null, Scada2015Sensor.class, 1).setDataList(list).write(response, fileName.toString()).dispose();
			return null;
		} catch (Exception e) {
			logger.warn("exportExcelTmpl", e);
			redirectAttributes.addFlashAttribute("msg", "Export excel template failed!");
		}
		return "redirect:/msg";
	}

	@RequestMapping(value = "/exportExcelTmpl")
	public String exportExcelTmpl(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "scada2015sensor-template.xlsx";
			List<Scada2015Sensor> list = Lists.newArrayList();
			new ExportExcel("scada2015sensor", Scada2015Sensor.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			logger.warn("exportExcelTmpl", e);
			redirectAttributes.addFlashAttribute("msg", "Export excel template failed!");
		}
		return "redirect:/msg";
	}

	@RequestMapping(value = "/getTableNameList", method = RequestMethod.GET)
	public @ResponseBody BaseData getTableNameList(@RequestParam(required = false) String fieldTypes) {
		String[] fieldTypeArray = null;
		if(StringUtils.isNotBlank(fieldTypes)){
			fieldTypeArray = StringUtils.split(fieldTypes, ",");
		}
		List<String> list = scada2015SensorDao.getTableNameList(fieldTypeArray);
		return new BaseData(list, list.size());
	}

	@RequestMapping(value = "/getFieldNameList", method = RequestMethod.GET)
	public @ResponseBody BaseData getFieldNameList(@RequestParam(required = false) String fieldTypes) {
		String[] fieldTypeArray = null;
		if(StringUtils.isNotBlank(fieldTypes)){
			fieldTypeArray = StringUtils.split(fieldTypes, ",");
		}
		List<Map<String, String>> list = scada2015SensorDao.getFieldNameList(fieldTypeArray);
		return new BaseData(list, list.size());
	}

	@RequestMapping(value = "/getFieldNameListByTableName", method = RequestMethod.GET)
	public @ResponseBody BaseData getFieldNameListByTableName(@RequestParam(required = true) String tableName) {
		List<Map<String, String>> list = scada2015SensorDao.getFieldNameListByTableName(tableName);
		return new BaseData(list, list.size());
	}
	
	@RequestMapping(value = "/getTableNameByFieldName", method = RequestMethod.GET)
	public @ResponseBody BaseData getTableNameByFieldName(@RequestParam(required = true) String fieldName) {
		String tableName = scada2015SensorDao.getTableNameByFieldName(fieldName);
		return new BaseData(tableName, 0);
	}

	private static final Logger logger = LoggerFactory.getLogger(Scada2015SensorWeb.class);

}
