package com.mw.ctdb.rtdb.modifydelta;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.lang.StringUtils;
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

import com.mw.base.BaseConsts;
import com.mw.base.BaseWeb;
import com.mw.base.model.BaseData;
import com.mw.plugins.excel.ExportExcel;
import com.mw.plugins.excel.ImportExcel;
import com.mw.utils.DateTimeUtils;

@Controller
@RequestMapping(value = "/rtdb/modifyDelta")
public class ModifyDeltaWeb extends BaseWeb {
	@Resource
	private ModifyDeltaDao modifyDeltaDao;
	@Resource
	private ModifyDeltaService modifyDeltaService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String list() {
		return "rtdb/modifyDelta";
	}

	@RequestMapping(value = "/getBy", method = RequestMethod.POST)
	public @ResponseBody BaseData getBy(@RequestBody ModifyDeltaQueryForm modifyDeltaQueryForm) {
		validate(modifyDeltaQueryForm);
		List<ModifyDeltaView> list = modifyDeltaDao.getBy(modifyDeltaQueryForm);
		return new BaseData(list, list.size());
	}

	@RequestMapping(value = "/saveModifyLogs/{batchNb}", method = RequestMethod.POST)
	@ResponseBody
	public BaseData saveModifyLogs(@PathVariable Long batchNb, @RequestBody List<ModifyDeltaForm> modifyDeltaFormList) throws ParseException {
		BaseData baseData = modifyDeltaService.batchSaveModifyLog(batchNb, modifyDeltaFormList);
		return baseData;
	}

	@RequestMapping(value = "/getPreviewData", method = RequestMethod.POST)
	@ResponseBody
	public BaseData getPreviewData(@RequestBody ModifyDeltaQueryForm modifyDeltaQueryForm) {
		List<ModifyDeltaView> list = modifyDeltaDao.previewBy(modifyDeltaQueryForm);
		return new BaseData(list, list.size());
	}

	@RequestMapping(value = "/updFieldValueByBatchNb", method = RequestMethod.POST)
	@ResponseBody
	public BaseData updFieldValueByBatchNb(@RequestParam("batchNb") Long batchNb) {
		BaseData baseData = modifyDeltaService.updFieldValueByBatchNb(batchNb);
		return baseData;
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody BaseData importExcel(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {

		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());

		try {
			ImportExcel importExcel = new ImportExcel(file, 0, 0);
			List<ModifyDeltaForm> list = importExcel.getDataList(ModifyDeltaForm.class);

			BaseData baseData = modifyDeltaService.batchSaveModifyLog(list);
			return baseData;
		} catch (Exception e) {
			logger.warn("importExcel", e);
			return new BaseData(BaseConsts.RST_CD.SYS, "Import failed");
		}
	}

	@RequestMapping(value = "/previewLastAve/{lastDays}/{formulaFlag}", method = RequestMethod.POST)
	public @ResponseBody BaseData previewLastAve(@PathVariable Integer lastDays, @PathVariable Integer formulaFlag, @RequestBody ModifyDeltaQueryForm modifyDeltaQueryForm) throws ParseException {
		validate(modifyDeltaQueryForm);

		List<ModifyDeltaForm> list = modifyDeltaService.getLastAve(modifyDeltaQueryForm, lastDays, formulaFlag);
		BaseData baseData = modifyDeltaService.batchSaveModifyLog(list);
		return baseData;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String export(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			Map<String, Object> params = WebUtils.getParametersStartingWith(request, null);
			ModifyDeltaQueryForm modifyDeltaQueryForm = new ModifyDeltaQueryForm(params);
			logger.info("1===>>>>");
			BaseData baseData = this.getBy(modifyDeltaQueryForm);
			logger.info("2===>>>>");
			if (StringUtils.equals(baseData.getErrCd(), "0")) {
				List<ModifyDeltaView> modifyDeltaViewList = (List<ModifyDeltaView>) baseData.getDt();
				List<ModifyDeltaForm> modifyDeltaFormList = new LinkedList<ModifyDeltaForm>();
				for(ModifyDeltaView modifyDeltaView : modifyDeltaViewList){
					ModifyDeltaForm modifyDeltaForm = new ModifyDeltaForm();
					modifyDeltaForm.setTableName(modifyDeltaView.getTableName());
					modifyDeltaForm.setFieldName(modifyDeltaView.getFieldName());
					modifyDeltaForm.setSensorDd(modifyDeltaView.getSensorDd());
					modifyDeltaForm.setOriFieldValue(modifyDeltaView.getOriFieldValue());
					modifyDeltaForm.setNewFieldValue(modifyDeltaView.getNewFieldValue());
					modifyDeltaForm.setModifyReason(modifyDeltaView.getModifyReason());
					modifyDeltaForm.setModifyMethod(modifyDeltaView.getModifyMethod());
					modifyDeltaFormList.add(modifyDeltaForm);
				}
				
				String fileName = new StringBuilder("adjustment-data-").append(DateTimeUtils.date2Str(new Date(), DateTimeUtils.FORMAT_YYYYMMDDHHMMSS)).append(".xlsx").toString();
				logger.info("3===>>>>");
				new ExportExcel(null, ModifyDeltaForm.class, 1).setDataList(modifyDeltaFormList).write(response, fileName).dispose();
				
				logger.info("4===>>>>");
				return null;
			} else {
				redirectAttributes.addFlashAttribute("msg", "Get data failed");
			}
		} catch (Exception e) {
			logger.warn("export", e);
			redirectAttributes.addFlashAttribute("msg", "Export failed");
		}
		return "redirect:/msg";
	}

	private static final Logger logger = LoggerFactory.getLogger(ModifyDeltaWeb.class);
}
