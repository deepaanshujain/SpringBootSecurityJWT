package com.dj.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtility {
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	// protected static SimpleDateFormat dbDateFormat = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getCurrentDateTime() {
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public static Date getExp(Long now, Long ttlMillis) {
		Date exp = null;
		if (ttlMillis >= 0) {
			long expMillis = now + ttlMillis;
			exp = new Date(expMillis);
		}
		return exp;
	}
	
	public static Date tokentimeformat(String tokenTime) {
		Date date1 = null;
		SimpleDateFormat tokentimeformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		try {
			date1 = tokentimeformat.parse(tokenTime);
		} catch (ParseException e) {
			System.out.println("Parsing problem in tokentimeformat method.");
		}

		return date1;
	}

}
