package com.mw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtils {
	public final static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public final static String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
	public final static String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_HH_MM_SS = "HH:mm:ss";
	public final static String FORMAT_YYYYMMDD = "yyyyMMdd";
	public final static String FORMAT_YYYYMM = "yyyyMM";
	public final static String FORMAT_YYMMDD = "yyMMdd";
	public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public final static String FORMAT_HHMMSS = "HHmmss";
	public final static String FORMAT_MMMDDYYYY_AT_HHMM_A = "MMM dd, yyyy 'at' hh:mm a";

	public static Date str2Date(String dateStr, String dateFormat) throws ParseException {
		return DateUtils.parseDate(dateStr, dateFormat);
	}

	public static Date str2Date(String dateStr, String dateFormat, Date defaultValue) throws ParseException {
		if (StringUtils.isBlank(dateStr))
			return defaultValue;

		return DateUtils.parseDate(dateStr, dateFormat);
	}

	public static String date2Str(Date date, String dateFormat) {
		return DateFormatUtils.format(date, dateFormat);
	}

	public static String date2Str(Date date, String dateFormat, String defaultValue) {
		if (date == null)
			return defaultValue;

		return DateFormatUtils.format(date, dateFormat);
	}

	public static long getTimeStamp() {
		return new Date().getTime();
	}

	public static Date getYyyymmdd(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static String getYyyymmddStr(Date date, int days, String formatter) {
		return DateTimeUtils.date2Str(getYyyymmdd(date, days), formatter);
	}

	public static String getYyyymmddStr(String yyyymmdd, int days, String formatter) throws ParseException {
		Date date = DateTimeUtils.str2Date(yyyymmdd, formatter);
		return DateTimeUtils.date2Str(getYyyymmdd(date, days), formatter);
	}

	public static Date getYyyymmdd(int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static String getYyyymmddStr(int days, String formatter) {
		return DateTimeUtils.date2Str(getYyyymmdd(days), formatter);
	}

	public static int compare(String dateStr, String dateFormat, String comparedDateStr, String comparedFormat) throws ParseException {
		Date date = new SimpleDateFormat(dateFormat).parse(dateStr);
		Date comparedDate = new SimpleDateFormat(comparedFormat).parse(comparedDateStr);

		return DateTimeComparator.getDateOnlyInstance().compare(date, comparedDate);
	}

	public static int compare(String dateStr, String dateFormat, String comparedDateStr, String comparedFormat, int defaultValue) {
		try {
			if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(comparedDateStr)) {
				logger.info(String.format("compareDate, dateStr:%s; dateFormat:%s; comparedDateStr:%s; comparedFormat:%s; defaultValue:%s;", dateStr, dateFormat, comparedDateStr, comparedFormat, defaultValue));
				return defaultValue;
			}
			Date date = new SimpleDateFormat(dateFormat).parse(dateStr);
			Date comparedDate = new SimpleDateFormat(comparedFormat).parse(comparedDateStr);

			return DateTimeComparator.getDateOnlyInstance().compare(date, comparedDate);
		} catch (Exception e) {
			logger.info(String.format("compareDate, dateStr:%s; dateFormat:%s; comparedDateStr:%s; comparedFormat:%s; defaultValue:%s;", dateStr, dateFormat, comparedDateStr, comparedFormat, defaultValue));
			return defaultValue;
		}
	}

	public static int compare(Date date, Date comparedDate) {
		return DateTimeComparator.getDateOnlyInstance().compare(date, comparedDate);
	}

	public static int daysBetween(String dateStr, String comparedDateStr) {
		DateTime start = new DateTime(dateStr);
		DateTime end = new DateTime(comparedDateStr);
		return Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
	}

	public static String str2Str(String src, String fmtFrom, String fmtTo) {
		String[] fmtFromArray = new String[] { fmtFrom };
		try {
			if (StringUtils.isBlank(src))
				return "";
			return DateFormatUtils.format(DateUtils.parseDate(src, fmtFromArray), fmtTo);
		} catch (ParseException e) {
			// logger.warn(String.format("formatDate-- src: %s, fmtFrom: %s, fmtTo: %s", src, fmtFrom, fmtTo));
			// logger.warn(e.getMessage());
			return "";
		}
	}

	public static int getCurrYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return year;
	}

	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
	
	public static void main(String[] args){
		System.out.println(DateTimeUtils.getCurrYear());
	}
}
