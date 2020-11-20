package com.mw.base;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mw.base.exception.ServiceException;
import com.mw.base.model.BaseData;
import com.mw.plugins.spring.message.MessageService;
import com.mw.utils.BeanValidators;

@Controller
public class BaseWeb {
	@Resource
	private Validator validator;
	@Resource
	protected MessageService messageService;

	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	public String msg() {
		return "msg";
	}
	
	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public String unauthorized(Model model) {
		model.addAttribute("msg", "unauthorized");
		return "msg";
	}

	protected <T> void validate(T t) {
		try {
			BeanValidators.validateWithException(validator, t);
		} catch (ConstraintViolationException e) {
			List<String> msgList = BeanValidators.extractPropertyAndMessageAsList(e, ": ");
			StringBuilder errMsg = new StringBuilder();
			for (String msg : msgList) {
				errMsg.append("<br/>").append(msg);
			}
			logger.info("BaseWeb.valid-> errMsg={}", errMsg.toString());
			throw new ServiceException(BaseConsts.RST_CD.PARAM, messageService.get("msg.error.invalid.parameters", "; ", new String[] { WebConsts.LANG.ZH_TW, WebConsts.LANG.EN_US }));
		}
	}

	protected <T> BaseData validateAndReturn(T t) {
		try {
			BeanValidators.validateWithException(validator, t);
		} catch (ConstraintViolationException e) {
			List<String> msgList = BeanValidators.extractPropertyAndMessageAsList(e, ": ");
			StringBuilder errMsg = new StringBuilder();
			for (String msg : msgList) {
				errMsg.append("<br/>").append(msg);
			}
			logger.info("BaseWeb.valid-> errMsg={}", errMsg.toString());
			return new BaseData(BaseConsts.RST_CD.PARAM, errMsg.toString());
		}

		return null;
	}

	private static final Logger logger = LoggerFactory.getLogger(BaseWeb.class);
}
