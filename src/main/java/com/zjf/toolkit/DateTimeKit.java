package com.zjf.toolkit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * DateTimeKit
 *
 * 简称说明
 *
 * PATTEAN -> P
 * FORMATTER -> F
 *
 * DATE -> D
 * TIME -> T
 * MILLISECOND -> M
 *
 * DATE_TIME -> DT
 * DATE_TIME_MILLISECOND -> DTM
 * TIME_MILLISECOND -> TM
 *
 * LocalDateTime -> LDT
 * LocalDate -> LD
 * LocalTime -> LT
 *
 * @author mrathena on 2018-09-27 14:59:04
 */
public final class DateTimeKit {

	public static void main(String[] args) {
		System.out.println(LocalDate.parse("2018-06-01", FD));
		System.out.println(DateTimeKit.parseFromD("2018-06-01"));
	}

	private DateTimeKit() {};

	public static final String yyyy = "yyyy";
	public static final String MM = "MM";
	public static final String dd = "dd";
	public static final String HH = "HH";
	public static final String mm = "mm";
	public static final String ss = "ss";

	public static final String P = "yyyyMMddHHmmss";
	public static final String PD = "yyyy-MM-dd";
	public static final String PT = "HH:mm:ss";
	public static final String PTM = "HH:mm:ss.SSS";
	public static final String PDT = "yyyy-MM-dd HH:mm:ss";
	public static final String PDTM = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final DateTimeFormatter F = DateTimeFormatter.ofPattern(P);
	public static final DateTimeFormatter FD = DateTimeFormatter.ofPattern(PD);
	public static final DateTimeFormatter FT = DateTimeFormatter.ofPattern(PT);
	public static final DateTimeFormatter FTM = DateTimeFormatter.ofPattern(PTM);
	public static final DateTimeFormatter FDT = DateTimeFormatter.ofPattern(PDT);
	public static final DateTimeFormatter FDTM = DateTimeFormatter.ofPattern(PDTM);

	/**
	 * 判断两个日期区间是否有重叠部分
	 */
	public static boolean checkIntervalConflict(LocalDate sDate1, LocalDate eDate1, LocalDate sDate2, LocalDate eDate2) {
		if (sDate1 == null || eDate1 == null || sDate2 == null || eDate2 == null) {
			throw new RuntimeException("比较时间不合法");
		}
		if (sDate1.isAfter(eDate1) || sDate2.isAfter(eDate2)) {
			throw new RuntimeException("比较时间不合法");
		}
		LocalDate minDate = sDate1.isBefore(sDate2) ? sDate1 : sDate2;
		LocalDate maxDate = eDate1.isBefore(eDate2) ? eDate2 : eDate1;
		long days = maxDate.toEpochDay() - minDate.toEpochDay();
		long days1 = eDate1.toEpochDay() - sDate1.toEpochDay();
		long days2 = eDate2.toEpochDay() - sDate2.toEpochDay();
//		System.out.println("最大日期与最小日期天数差:" + days);
//		System.out.println("第一个日期区间天数:" + days1);
//		System.out.println("第二个日期区间天数:" + days2);
//		System.out.println("两个日期区间总天数:" + (days1 + days2));
		if (days > (days1 + days2)) {
			return false;
		}
		return true;
	}

	/**
	 * 一毫秒=1000000纳秒
	 */
	private static final int MILLI = 1000000;

	/**
	 * 纪元date 1970-01-01 00:00:00
	 */
	public static Date epoch() {
		return new Date(0);
	}

	/**
	 * 当前时间转String
	 */
	public static String now(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 当前时间转String
	 */
	public static String now(DateTimeFormatter formatter) {
		return LocalDateTime.now().format(formatter);
	}

	/**
	 * 当前时间转String
	 */
	public static String now() {
		return now(FDT);
	}

	/** 时间添加天数 */
	public static Date plusDays(Date date, int days) {
		return toD(toLDT(date).plusDays(days));
	}

	/** [使用date的年月日创建一个开始date(如:2018-01-01 00:00:00)] */
	public static Date toBeginDate(Date date) {
		return toD(toLD(date));
	}

	/** [使用date的年月日创建一个结束date(如:2018-01-31 23:59:59)] */
	public static Date toEndDate(Date date) {
		return toD(toLDT(date).with(LocalTime.MAX));
	}

	/** [使用年月日时分秒创建一个新的date] */
	public static Date newDate(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		return toD(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second));
	}

	/** [使用年月日时分秒纳秒(9位数)创建一个新的date] */
	public static Date newDate(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
		return toD(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond));
	}

	/** [使用年月日创建一个新的date] */
	public static Date newDate(int year, int month, int dayOfMonth) {
		return toD(LocalDate.of(year, month, dayOfMonth));
	}

	/** [使用时分秒创建一个新的date] ] */
	public static Date newTime(int hour, int minute, int second) {
		return toD(LocalTime.of(hour, minute, second));
	}

	/** [使用时分秒毫秒创建一个新的date] ] */
	public static Date newTime(int hour, int minute, int second, int milliOfSecond) {
		return toD(LocalTime.of(hour, minute, second, milliOfSecond * MILLI));
	}

	/** [Date转LocalDateTime] */
	public static LocalDateTime toLDT(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/** [Date转LocalDate] */
	public static LocalDate toLD(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/** [Date转LocalTime] */
	public static LocalTime toLT(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}

	/** [LocalDateTime转Date] */
	public static Date toD(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/** [LocalDate转Date 00:00:00] */
	public static Date toD(LocalDate localDate) {
		return toD(localDate.atStartOfDay());
	}

	/** [LocalTime转Date 1970-01-01] */
	public static Date toD(LocalTime localTime) {
		return toD(localTime.atDate(toLD(epoch())));
	}

	/** [Date转String pattern] */
	public static String format(Date date, String pattern) {
		return toLDT(date).format(DateTimeFormatter.ofPattern(pattern));
	}

	/** [Date转String formatter] */
	public static String format(Date date, DateTimeFormatter formatter) {
		return toLDT(date).format(formatter);
	}

	/** [Date转String FDTM(yyyy-MM-dd HH:mm:ss.SSS)] */
	public static String formatToDTM(Date date) {
		return toLDT(date).format(FDTM);
	}

	/** [Date转String FDT(yyyy-MM-dd HH:mm:ss)] */
	public static String formatToDT(Date date) {
		return toLDT(date).format(FDT);
	}

	/** [Date转String FD(yyyy-MM-dd)] */
	public static String formatToD(Date date) {
		return toLDT(date).format(FD);
	}

	/** [Date转String FTM(HH:mm:ss.SSS)] */
	public static String formatToTM(Date date) {
		return toLDT(date).format(FTM);
	}

	/** [Date转String FT(HH:mm:ss)] */
	public static String formatToT(Date date) {
		return toLDT(date).format(FT);
	}

	/** [String转Date pattern(必须包含年yyyy月MM日dd时HH分mm秒ss(毫秒SSS随意))] (可使用Commons-lang3包中的DateUtils) */
	public static Date parse(String string, String pattern) {
		String regex = "";
		Pattern regPattern = Pattern.compile(regex);
		StringBuilder stringSB = new StringBuilder(string);
		StringBuilder patternSB = new StringBuilder(pattern);
		if (!pattern.contains(yyyy)) {
			patternSB.append(yyyy);
			stringSB.append("1970");
		}
		if (!pattern.contains(MM)) {
			patternSB.append(MM);
			stringSB.append("01");
		}
		if (!pattern.contains(dd)) {
			patternSB.append(dd);
			stringSB.append("01");
		}
		if (!pattern.contains(HH)) {
			patternSB.append(HH);
			stringSB.append("00");
		}
		if (!pattern.contains(mm)) {
			patternSB.append(mm);
			stringSB.append("00");
		}
		if (!pattern.contains(ss)) {
			patternSB.append(ss);
			stringSB.append("00");
		}
		System.out.println(patternSB);
		System.out.println(stringSB);
		return toD(LocalDateTime.parse(stringSB.toString(), DateTimeFormatter.ofPattern(patternSB.toString())));
	}

	/** [String转Date formatter(必须包含年yyyy月MM日dd时HH分mm秒ss(毫秒SSS随意))] */
	public static Date parse(String string, DateTimeFormatter formatter) {
		return toD(LocalDateTime.parse(string, formatter));
	}

	/** [String转Date FDTM(yyyy-MM-dd HH:mm:ss.SSS)] */
	public static Date parseFromDTM(String string) {
		return toD(LocalDateTime.parse(string, FDTM));
	}

	/** [String转Date FDT(yyyy-MM-dd HH:mm:ss)] */
	public static Date parseFromDT(String string) {
		return toD(LocalDateTime.parse(string, FDT));
	}

	/** [String转Date FD(yyyy-MM-dd) 00:00:00] */
	public static Date parseFromD(String string) {
		return toD(LocalDate.parse(string, FD).atStartOfDay());
	}

	/** [String转Date 1970-01-01 FTM(HH:mm:ss.SSS)] */
	public static Date parseFromTM(String string) {
		return toD(LocalTime.parse(string, FTM));
	}

	/** [String转Date 1970-01-01 FT(HH:mm:ss)] */
	public static Date parseFromT(String string) {
		return toD(LocalTime.parse(string, FT));
	}
}