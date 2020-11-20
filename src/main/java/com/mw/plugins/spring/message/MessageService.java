package com.mw.plugins.spring.message;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.mw.utils.web.LocaleUtils;

@Service
public class MessageService {

	@Resource
	private MessageSource messageSource;

	public String get(String key, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}

	public String get(String key, Object[] objs, Locale locale) {
		return messageSource.getMessage(key, objs, locale);
	}

	public String get(String key, HttpServletRequest request) {
		return this.get(key, LocaleUtils.getLocale(request));
	}

	public String get(String key, Object[] objs, HttpServletRequest request) {
		return this.get(key, objs, LocaleUtils.getLocale(request));
	}

	public String get(String key, String lang) {
		return messageSource.getMessage(key, null, LocaleUtils.getLocale(lang));
	}

	public String get(String key, Object[] objs, String lang) {
		return messageSource.getMessage(key, objs, LocaleUtils.getLocale(lang));
	}

	public String get(String key, String joinString, String[] langs) {
		StringBuilder sb = new StringBuilder();
		for (String lang : langs) {
			sb.append(get(key, lang)).append(joinString);
		}
		return sb.toString();
	}
	
	public String get(String[] keys, String joinString, String lang) {
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(get(key, lang)).append(joinString);
		}
		return sb.toString();
	}

	public void bindField(String key, Locale locale, BindingResult bindingResult, String field) {
		bindingResult.rejectValue(field, null, get(key, locale));
	}

	public void bindField(String key, HttpServletRequest request, BindingResult bindingResult, String field) {
		bindingResult.rejectValue(field, null, get(key, request));
	}

	public Message warning(String key, Object[] objs, Locale locale) {
		return Message.warning(get(key, objs, locale));
	}

	public Message error(String key, Object[] objs, Locale locale) {
		return Message.error(get(key, objs, locale));
	}

	public Message info(String key, Object[] objs, Locale locale) {
		return Message.info(get(key, objs, locale));
	}

	public Message success(String key, Object[] objs, Locale locale) {
		return Message.success(get(key, objs, locale));
	}

	public Message warning(String key, Object[] objs, HttpServletRequest request) {
		return Message.warning(get(key, objs, request));
	}

	public Message error(String key, Object[] objs, HttpServletRequest request) {
		return Message.error(get(key, objs, request));
	}

	public Message info(String key, Object[] objs, HttpServletRequest request) {
		return Message.info(get(key, objs, request));
	}

	public Message success(String key, Object[] objs, HttpServletRequest request) {
		return Message.success(get(key, objs, request));
	}

	public Message warning(String key, Locale locale) {
		return Message.warning(get(key, locale));
	}

	public Message error(String key, Locale locale) {
		return Message.error(get(key, locale));
	}

	public Message info(String key, Locale locale) {
		return Message.info(get(key, locale));
	}

	public Message success(String key, Locale locale) {
		return Message.success(get(key, locale));
	}

	public Message warning(String key, HttpServletRequest request) {
		return Message.warning(get(key, request));
	}

	public Message error(String key, HttpServletRequest request) {
		return Message.error(get(key, request));
	}

	public Message info(String key, HttpServletRequest request) {
		return Message.info(get(key, request));
	}

	public Message success(String key, HttpServletRequest request) {
		return Message.success(get(key, request));
	}
}
