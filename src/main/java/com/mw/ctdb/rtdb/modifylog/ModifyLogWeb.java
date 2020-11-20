package com.mw.ctdb.rtdb.modifylog;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.mw.base.BaseWeb;
import com.mw.base.model.BaseData;
import com.mw.base.model.BaseQueryForm;
import com.mw.ctdb.rtdb.modifydelta.ModifyDeltaForm;
import com.mw.ctdb.rtdb.modifydelta.ModifyDeltaQueryForm;
import com.mw.ctdb.rtdb.modifydelta.ModifyDeltaView;
import com.mw.plugins.excel.ExportExcel;
import com.mw.utils.DateTimeUtils;

@Controller
@RequestMapping(value = "/rtdb/modifyLog")
public class ModifyLogWeb extends BaseWeb {
	@Resource
	private ModifyLogDao modifyLogDao;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String list() {
		return "rtdb/modifyLog";
	}

	@RequestMapping(value = "/getBy", method = RequestMethod.POST)
	public @ResponseBody BaseData getBy(@RequestBody BaseQueryForm baseQueryForm) {
		List<ModifyLog> list = modifyLogDao.getBy(baseQueryForm.getFfMap(), baseQueryForm.getPage());
		int ct = modifyLogDao.getCountBy(baseQueryForm.getFfMap());

		return new BaseData(list, ct);
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String export(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			Map<String, Object> params = WebUtils.getParametersStartingWith(request, null);
			List<ModifyLog> list = modifyLogDao.getBy(params, null);

			String fileName = new StringBuilder("adjustment-log-").append(DateTimeUtils.date2Str(new Date(), DateTimeUtils.FORMAT_YYYYMMDDHHMMSS)).append(".xlsx").toString();
			new ExportExcel(null, ModifyLog.class, 1).setDataList(list).write(response, fileName).dispose();

			return null;
		} catch (Exception e) {
			logger.warn("export", e);
			redirectAttributes.addFlashAttribute("msg", "Export failed");
		}
		return "redirect:/msg";
	}

	private static final Logger logger = LoggerFactory.getLogger(ModifyLogWeb.class);
}
