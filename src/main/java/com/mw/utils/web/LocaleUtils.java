package com.mw.utils.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleUtils {
	public static Locale getLocale(String lang){
		String[] langs = StringUtils.split(lang, "_");
		Locale locale = new Locale(langs[0], langs[1]);
		return locale;
	}
	
	public static Locale getLocale(HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);
		return locale;
	}
	
	public static String getLocaleLanguage(HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);
		String lang = locale.getLanguage();
		return lang;
	}
	
	public static String getLocaleCountry(HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);
		String country = locale.getCountry();
		return country;
	}
	
	public static String getLocaleLanguageCountry(HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);
		String lang = locale.getLanguage();
		String country = locale.getCountry();
		String langCountry = lang+"_"+country;
		
		return langCountry;
	}
}
