package com.zhaoyu.campus.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DateUtil {
	// public static final String HM = "HH:mm";
	// public static final String HMS = "HH:mm:ss";
	public static final String YMD = "yyyy-MM-dd";
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String KEY_INT_STARTTIME = "intStarttime";
	public static final String KEY_STR_STARTTIME = "strStarttime";
	public static final String KEY_INT_ENDTIME = "intEndtime";
	public static final String KEY_STR_ENDTIME = "strEndtime";
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取当前时间的时间戳
	 *
	 * @author lmc 2015年11月6日
	 * @return int值
	 */
	public static int getCurrentTimeIntValue() {
		int sencends = (int) (System.currentTimeMillis() / 1000);
		return sencends;
	}

	/**
	 * 获取指定时间的 时间戳
	 * 
	 * @author lmc 2015年11月6日
	 * @param time
	 *            指定时间
	 * @param format
	 *            时间格式
	 * @return 时间戳
	 * @throws ParseException
	 */
	public static int getAppointedTimeIntValue(String time, String format) throws ParseException {
		// "yyyy-MM-dd HH:mm:ss"
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = f.parse(time);
		return (int) (date.getTime() / 1000);

	}

	/**
	 * 根据时间戳获取时间
	 * 
	 * @author LMC 2015年11月12日
	 * @param timeStamp
	 *            时间戳（int格式）
	 * @param format
	 *            返回时间格式
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByTimeStamp(int timeStamp, String format) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat(format);
		String d = f.format(((long) timeStamp) * 1000);
		Date date = f.parse(d);
		return date;
	}

	/**
	 * 根据时间戳获取时间字符串
	 * 
	 * @author shixiongwei 2015年11月13日
	 * @param timeStamp
	 *            时间戳（int格式）
	 * @param format
	 *            返回时间格式
	 * @return string
	 * @throws ParseException
	 */
	public static String getDateStringByTimeStamp(Integer timeStamp, String format) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat(format);
		String d = "";
		if (timeStamp != null) {
			d = f.format(((long) timeStamp) * 1000);
		}
		return d;
	}

	/**
	 * 格式化时间
	 * 
	 * @author lichao 2015年12月10日
	 * @param datetime
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String formatDate(Object datetime, String format) throws Exception {
		SimpleDateFormat f = new SimpleDateFormat(format);
		if (datetime instanceof Date) {
			return f.format((Date) datetime);
		} else if (datetime instanceof Long) {
			return f.format(new Date((Long) datetime));
		} else {
			throw new Exception("param datetime must be a Date or Long.");
		}
	}

	/**
	 * 获得当天0点时间戳
	 * 
	 * @author shixiongwei 2015年11月17日
	 * @param date
	 * @return int
	 */
	public static int getTimesmorning(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 获得当天24点时间戳
	 * 
	 * @author shixiongwei 2015年11月17日
	 * @param date
	 * @return int
	 */
	public static int getTimesnight(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 获取昨天0点的时间戳
	 * 
	 * @return
	 */
	public static int getLastDayMorning() {
		Date date = new Date();// 取时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(cal.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		return (int) (cal.getTimeInMillis() / 1000); // 这个时间就是日期往后推一天的结果
	}
	
	/**
	 * 获取+n天0点的时间戳
	 * 
	 * @return
	 */
	public static int gettimestamp(Integer n) {
		Date date = new Date();// 取时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(cal.DATE, n);// 把日期往后增加一天.整数往后推,负数往前移动
		return (int) (cal.getTimeInMillis() / 1000); // 这个时间就是日期往后推一天的结果
	}

	/**
	 * 获取日期格式化字符串
	 * 
	 * @author shixiongwei 2015年12月9日
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String formatDateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date == null) {
			return "";
		}
		return sdf.format(date);
	}

	/**
	 * 时间格式检验
	 * 
	 * @param format
	 * @param strStarttime
	 * @param strEndtime
	 * @param intStarttime
	 * @param intEndtime
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> formatStarttimeAndEndtime(String format, String strStarttime, String strEndtime,
			Integer intStarttime, Integer intEndtime) throws ParseException {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		if (format == null) {
			timeMap.put(KEY_STR_STARTTIME, null);
			timeMap.put(KEY_INT_STARTTIME, null);
			timeMap.put(KEY_STR_ENDTIME, null);
			timeMap.put(KEY_INT_ENDTIME, null);
			return timeMap;
		}
		boolean ymd = false, ymdhms = false;
		format = format.trim();
		int formatLen = format.length();
		int ymdLen = YMD.length();
		int ymdhmsLen = YMDHMS.length();
		if (formatLen == ymdLen) {
			ymd = true;
		} else if (formatLen == ymdhmsLen) {
			ymdhms = true;
		}
		// 验证默认值
		if (strStarttime != null && "".equals(strStarttime.trim())) {
			strStarttime = null;
		}
		if (strEndtime != null && "".equals(strEndtime.trim())) {
			strEndtime = null;
		}
		if (intStarttime != null && intStarttime <= 0) {
			intStarttime = null;
		}
		if (intEndtime != null && intEndtime <= 0) {
			intEndtime = null;
		}
		// **开始时间计算**********************************************************************************************
		Date currentTime = new Date();
		// 获取当前时间秒数
		int intCurrentTime = (int) (currentTime.getTime() / 1000);
		// 获取当天0时
		int intTodayTimesmorning = DateUtil.getTimesmorning(currentTime);
		String strTodayTimesmorning = DateUtil.getDateStringByTimeStamp(intTodayTimesmorning, format);
		// 获取当天24时
		int intTodayTimesnight = DateUtil.getTimesnight(currentTime);
		String strTodayTimesnight = DateUtil.getDateStringByTimeStamp(intTodayTimesnight, format);

		if (strStarttime != null || strEndtime != null) {// 如果字符串时间有一个不为空
			if (strStarttime != null && strEndtime != null) {// 如果两个字符串时间不为空
				// format str time to int time
				intStarttime = DateUtil.getAppointedTimeIntValue(strStarttime, format);
				intEndtime = DateUtil.getAppointedTimeIntValue(strEndtime, format);
			} else if (strStarttime != null) {// 否则字符串开始时间不为空，字符串结束时间为空
				// format str time to int time
				intStarttime = DateUtil.getAppointedTimeIntValue(strStarttime, format);
				// set end time is default today times night
				intEndtime = intTodayTimesnight;
				strEndtime = strTodayTimesnight;
			} else {// 否则字符串结束时间不为空，字符串开始时间为空
					// format str time to int time
				intEndtime = DateUtil.getAppointedTimeIntValue(strEndtime, format);
				// set start time is default
				if (ymdhms) {
					strStarttime = "1970-01-01 00:00:00";
				} else {
					strStarttime = "1970-01-01";
				}
				intStarttime = DateUtil.getAppointedTimeIntValue(strStarttime, format);
			}
			if (ymd) {
				// 设置开始时间为当天0时
				intStarttime = DateUtil.getTimesmorning(DateUtil.getDateByTimeStamp(intStarttime, format));
				strStarttime = DateUtil.getDateStringByTimeStamp(intStarttime, format);
				// 设置结束时间为当天24时
				intEndtime = DateUtil.getTimesnight(DateUtil.getDateByTimeStamp(intEndtime, format));
				strEndtime = DateUtil.getDateStringByTimeStamp(intEndtime, format);
			} else {

			}
			// 判断时间是否符合常规
			if (intStarttime > intEndtime) {// 如果开始时间大于结束时间，则将开始时间设置为结束时间当天0时
				intStarttime = DateUtil.getTimesmorning(DateUtil.getDateByTimeStamp(intEndtime, format));
				strStarttime = DateUtil.getDateStringByTimeStamp(intStarttime, format);
			}
			if (intStarttime > intCurrentTime) {// 如果开始时间大于当前时间，刚将开始时间设置为当天0时
				intStarttime = intTodayTimesmorning;
				strStarttime = DateUtil.getDateStringByTimeStamp(intStarttime, format);
			}
			if (intStarttime.equals(intEndtime)) {// 如果开始时间与结束时间相等
				// 设置开始时间为当天0时
				intStarttime = DateUtil.getTimesmorning(DateUtil.getDateByTimeStamp(intStarttime, format));
				strStarttime = DateUtil.getDateStringByTimeStamp(intStarttime, format);
			}
		} else if (intStarttime != null || intEndtime != null) {// 否则数字时间有一个不为空
			if (strStarttime != null && strEndtime != null) {// 如果两个数字时间不为空

			} else if (strStarttime != null) {// 如果数字开始时间不为空

			} else {// 否则数字结束时间不为空

			}
		} else {// 所有时间值为空

		}
		timeMap.put(KEY_STR_STARTTIME, strStarttime);
		timeMap.put(KEY_INT_STARTTIME, intStarttime);
		timeMap.put(KEY_STR_ENDTIME, strEndtime);
		timeMap.put(KEY_INT_ENDTIME, intEndtime);
		return timeMap;
	}

	public static Set<Integer> strTimesToIntTimes(String strTimes, String split, String foratter) {
		// 接收生效时间
		Set<Integer> intTimeSet = new HashSet<Integer>();
		String[] dts = strTimes.split(split);
		for (String str : dts) {
			if (str != null && (!"".equals(str.trim()))) {
				try {
					intTimeSet.add(DateUtil.getAppointedTimeIntValue(str.trim(), foratter));
				} catch (Exception e) {

				}
			}
		}
		return intTimeSet;
	}

	public static String foratterIntTime(Integer intTime) {
		if (intTime == null || intTime <= 0) {
			return "00:00";
		}
		int h = intTime / 60;
		int m = intTime % 60;
		String hh = h + "", mm = m + "";
		if (hh.length() == 1) {
			hh = "0" + h;
		}
		if (mm.length() == 1) {
			mm = "0" + m;
		}
		return hh + ":" + mm;
	}

	/**
	 * 
	 * @param strTime
	 * @return
	 */
	public static Integer foratterStrTime(String strTime) {
		String strDateTime = "1970-01-01 " + strTime + ":00";
		Integer result = 0;
		try {
			Integer intTime = DateUtil.getAppointedTimeIntValue(strDateTime, DateUtil.YMDHMS);
			String hh = DateUtil.getDateStringByTimeStamp(intTime, "HH");
			String mm = DateUtil.getDateStringByTimeStamp(intTime, "mm");
			int h = Integer.parseInt(hh, 10);
			int m = Integer.parseInt(mm);
			result = h * 60 + m;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 计算日期时间数值。strDateTime的数据请符合时间格式【"yyyy-MM-dd HH:mm:ss"】,数据前的0可以去掉
	 * 如果strDateTime没有年份，时间从00:00:00开始计算（忽略年份）；
	 * 如果strDateTime有年份，strDateTime为截止时间； 格式化时例： --计算"8:00"分钟数 --计算"8:00:12"秒数
	 * --计算截止"2015-12"月份数 --计算截止"2015-12-24 12:12:00"秒数 --其它类推....
	 * 
	 * @param strDateTime
	 * @return 时间数
	 */
	public static Integer foratterDateTime(String strDateTime) {
		Integer resultIntTime = 0;
		if (strDateTime == null) {
			return resultIntTime;
		} else {
			strDateTime = strDateTime.trim();
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		if ("".equals(strDateTime)) {
			// 获取当天截止当前时间的时间戳
		} else if (strDateTime.indexOf("date") == 0) {// 获取截止当前日其的时间戳，精确到天

		} else if (strDateTime.indexOf("time") == 0) {// 获取当天截止当前时间的时间
			if (strDateTime.length() > 4) {
				switch (strDateTime.toCharArray()[4]) {
				case 'h':
				case 'H':
					sdf = new SimpleDateFormat("HH");
					break;
				case 'm':
				case 'M':
					sdf = new SimpleDateFormat("HH:mm");
					break;
				case 's':
					sdf = new SimpleDateFormat("HH:mm:ss");
					break;
				case 'S':
					sdf = new SimpleDateFormat("HH:mm:ss:SSS");
					break;
				}
				strDateTime = sdf.format(new Date());
			} else {
				// 默认当前时间秒数
				sdf = new SimpleDateFormat("HH:mm:ss");
				strDateTime = sdf.format(new Date());
			}
			resultIntTime = foratterDateTime(strDateTime);
		} else if (strDateTime.indexOf("datetime") == 0) {// 获取截止当前日其的时间戳，精确到秒

		} else {
			if (strDateTime.indexOf("-") > 0) {// 有年份

			} else if (strDateTime.indexOf("/") > 0) {// 有年份

			} else {// 没有年份，只有时间
				String[] strArrTime = strDateTime.split(":");
				int strArrLenTime = strArrTime.length;
				if (strArrLenTime == 1) {// 时
					resultIntTime = Integer.parseInt(strArrTime[0]);
				} else if (strArrLenTime == 2) {// 分
					resultIntTime = Integer.parseInt(strArrTime[0]) * 60 + Integer.parseInt(strArrTime[1]);
				} else if (strArrLenTime == 3) {// 秒
					resultIntTime = Integer.parseInt(strArrTime[0]) * 60 * 60 + Integer.parseInt(strArrTime[1]) * 60
							+ Integer.parseInt(strArrTime[2]);
				} else {// 不符合规范
					resultIntTime = 0;
				}
			}
		}
		return resultIntTime;
	}

	/**
	 * 获取当前时间的分钟数
	 * 
	 * @return
	 */
	public static int getCurrentTimeMinuteValue() {
		Integer result = 0;
		try {
			Integer intTime = DateUtil.getCurrentTimeIntValue();
			String hh = DateUtil.getDateStringByTimeStamp(intTime, "HH");
			String mm = DateUtil.getDateStringByTimeStamp(intTime, "mm");
			int h = Integer.parseInt(hh, 10);
			int m = Integer.parseInt(mm);
			result = h * 60 + m;
		} catch (Exception e) {
		}
		return result;

	}

	/**
	 * 日期运算
	 * 
	 * @param date
	 *            要进行运算的日期
	 * @param value
	 *            value为正则往后,为负则往前
	 * @param field
	 *            field取1加一年,取2加半年,取3加一季度,取4加一周,取5加一天
	 * @return 计算后的日期
	 */
	public static Date gregorianCalendar(Date date, int value, int field) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(field, value);
		date = gc.getTime(); // 这个时间就是日期往后推一天的结果
		return date;
	}

	/**
	 * 为日期增加天数
	 * 
	 * @param intDate
	 *            日期秒数
	 * @param day
	 *            天数，为正 则往后,为负 则往前
	 * @return
	 */
	public static Integer addDay(Integer intDate, int day) {
		Integer result = 0;
		int oneDay = 24 * 60 * 60;
		result = intDate + (day * oneDay);
		return result;
	}

	/**
	 * 为日期增加天数
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数，为正 则往后,为负 则往前
	 * @return
	 */
	public static Integer addDay(Date date, int day) {
		Integer intDate = (int) (date.getTime() / 1000);
		Integer result = 0;
		int oneDay = 24 * 60 * 60;
		result = intDate + (day * oneDay);
		return result;
	}

	/**
	 * * 获取指定日期所在周的起始日期 参数为null时表示获取当前日期
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getWeekBeginOfDate(Date date) {
		// String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
		// "星期六"};
		Integer beginDate = 0;
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		} else {
			date = new Date();
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		beginDate = addDay(date, -w);
		return beginDate;
	}

	/**
	 * * 获取指定日期所在周的起始日期 参数为null时表示获取当前日期
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getWeekEndOfDate(Date date) {
		// String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
		// "星期六"};
		Integer endDate = 0;
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		} else {
			date = new Date();
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		endDate = addDay(date, 6 - w);
		return endDate;
	}

	// 获得本周日0点时间
	public static int getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	// 获得本周日24点时间
	public static int getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return (int) ((cal.getTime().getTime() + (7 * 24 * 60 * 60 * 1000)) / 1000);
	}

	// 获得本月第一天0点时间
	public static int getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return (int) (cal.getTimeInMillis() / 1000);
	}

	// 获得本月最后一天24点时间
	public static int getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 根据时间戳获得当天的00:00:00
	 * 
	 * @param timestamp
	 * @return
	 */
	public static int getTimesmorning(int timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((long) (timestamp * 1000.0));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 根据时间戳获得当天的23:59:59
	 * 
	 * @param timestamp
	 * @return
	 */
	public static int getTimesnight(int timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((long) (timestamp * 1000.0));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 根据时间戳间隔获得中间的相差天数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static LinkedHashSet<String> getDays(int startTime, int endTime, String format) {
		LinkedHashSet<String> days = new LinkedHashSet<>();
		if (startTime > endTime) {
			return days;
		}
		int startTimeMorning = getTimesmorning(startTime);
		int endTimeNight = getTimesnight(endTime);
		int dayInt = 24 * 60 * 60;
		int minus = (int) Math.floor((endTimeNight - startTimeMorning) * 1.0 / dayInt);// 相隔天数
		for (int i = 0; i <= minus; i++) {
			try {
				days.add(getDateStringByTimeStamp(startTimeMorning + (int) (dayInt * i * 1.0), format));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return days;

	}

	public static Integer getDatekey() {
		Integer result = 0;
		try {
			String t = getDateStringByTimeStamp(getCurrentTimeIntValue(), "YYYYMMdd");
			result = Integer.valueOf(t.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 时间戳转str
	 * 
	 * @return
	 */
	public static String timestampToStr(Long timestamp) {
		return DATE_TIME_FORMAT.format(timestamp);
	}

	/**
	 * 
	 * @param time
	 *            HH:mm:ss
	 * @return
	 */
	public static boolean isValidTime_HHmmss(String time) {
		return time.matches("^(20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9]$");
	}

	/**
	 * 
	 * @param date
	 *            yyyy-MM-dd
	 * @return
	 */
	public static boolean isValidTDate_yyyy_MM_dd(String date) {
		return date.matches(
				"((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30)))");
	}

	/**
	 * 
	 * @param date
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean isValidDate(String date) {
		return date.matches(
				"^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$");
	}

	/**
	 * 获取查询时间年月
	 * 
	 * @return
	 */
	public static String getCurrentDateString() throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("yyyyMM");

		String d = f.format(new Date().getTime());

		return d;
	}

	/**
	 * 时间格式转换
	 * 
	 * @return
	 */
	public static String getStartDateString(String startDate) throws ParseException {

		String str = startDate.replaceAll("-", "");
		str = str.substring(0, 6);
		return str;
	}

	/**
	 * 格式化时间时间单位是【毫秒】，一定要注意
	 *
	 * @param time   time
	 * @param format format
	 * @return return
	 */
	public static String foramtTime(Long time, String format) {
		String result = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

			result = simpleDateFormat.format(time * 1000);
		} catch (Exception e) {
//            e.printStackTrace();
		}
		return result;
	}
}
