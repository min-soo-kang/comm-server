package com.its.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	private static String currentTime;
	private static String fiveMinCurrent;
	private static String fiveMinFrom;
	private static String fiveMinTo;
	
	public static String getCurrentTime() {
		return currentTime;
	}

	public static String getFiveMinCurrent() {
		return fiveMinCurrent;
	}

	public static String getFiveMinFrom() {
		return fiveMinFrom;
	}

	public static String getFiveMinTo() {
		return fiveMinTo;
	}

	public static void initAnalysisTime() {
		currentTime = getCurrentTimeString();
		fiveMinCurrent = getCurrentFiveMinString();
		fiveMinFrom = getBeforeFiveMinString();
		fiveMinTo = getCurrentFiveMinToString();
	}
	
	/* 정주기 현재 5분 시각 */
	public static String getCurrentFiveMinString() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int min = cal.get(Calendar.MINUTE);
		cal.add(Calendar.MINUTE, -(min % 5));
		return new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
	}
	public static String getCurrentFiveMinToString() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int min = cal.get(Calendar.MINUTE);
		cal.add(Calendar.MINUTE, -(min % 5));
		cal.add(Calendar.SECOND, -1);	/* 정주기 현재 5분 시각에서 1초를 뺀 시각 */
		return new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
	}

	/* 정주기 이전 5분 시각 */
	public static String getBeforeFiveMinString() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int min = cal.get(Calendar.MINUTE);
		cal.add(Calendar.MINUTE, -(min % 5) - 5);	/* 정주기 현재 5분에서 5분을 뺀 시각 */
		return new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
	}

	/* 현재 시각을 문자열로 리턴 */
	public static String getCurrentTimeString() {
		Calendar cal = Calendar.getInstance();
		return new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
	}
	public static String getCurrentTimeString(String parmaFmt) {
		Calendar cal = Calendar.getInstance();
		return new SimpleDateFormat(parmaFmt).format(cal.getTime());
	}
	
	/* 현재 시각을 Date 형으로 리턴 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/* 현재 시각을 second로 리턴 */
	public static long getCurrentTimeSeconds() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
		//return System.currentTimeMillis();
	}
}
