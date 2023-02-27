package com.bcareapp.util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.json.ParseException;

public class DateUtil {
	private static final Logger logger = Logger.getLogger(DateUtil.class);

	private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");

	// 5hrs 30 mins
	public static long TIMEZONE_DIFFERENTIAL = 0;

	// Timezone Based Times
	// 4 AM
	public static long EARLY_MORNING_TIME = 14400000 - TIMEZONE_DIFFERENTIAL;
	// 8 AM
	public static long MORNING_TIME = 28800000 - TIMEZONE_DIFFERENTIAL;
	// 12 PM
	public static long NOON_TIME = 43200000 - TIMEZONE_DIFFERENTIAL;
	// 4 PM
	public static long EVENING_TIME = 57600000 - TIMEZONE_DIFFERENTIAL;
	// 8 PM;
	public static long NIGHT_TIME = 72000000 - TIMEZONE_DIFFERENTIAL;
	// 12 AM;
	public static long MIDNIGHT_TIME = 0;
	// NEXT 12 AM
	public static long NEXT_MIDNIGHT_TIME = 86400000 - TIMEZONE_DIFFERENTIAL;

	/**
	 * Time in Milliseconds
	 */

	public static final long ONE_DAY_IN_MILLISCONDS = 1 * 24 * 60 * 60 * 1000;
	public static final long ONE_HOUR_IN_MILLISCONDS = 1 * 60 * 60 * 1000;

	public static long[] TIME_SLOTS_4 = new long[] { MORNING_TIME, NOON_TIME, EVENING_TIME, NIGHT_TIME };
	public static long[] TIME_SLOTS_6 = new long[] { EARLY_MORNING_TIME, MORNING_TIME, NOON_TIME, EVENING_TIME,
			NIGHT_TIME, MIDNIGHT_TIME };
	public static String[] TIME_TITLES = new String[] { "EARLY MORNING", "MORNING", "AFTERNOON", "EVENING", "NIGHT",
			"MID NIGHT" };

	public static String getTimeTitle(long time) {
		String timeTitle = "";
		if (time > EARLY_MORNING_TIME && time <= MORNING_TIME) {
			timeTitle = TIME_TITLES[0];
		} else if (time > MORNING_TIME && time < NOON_TIME) {
			timeTitle = TIME_TITLES[1];
		} else if (time >= NOON_TIME && time < EVENING_TIME) {
			timeTitle = TIME_TITLES[2];
		} else if (time >= EVENING_TIME && time < NIGHT_TIME) {
			timeTitle = TIME_TITLES[3];
		} else if (time >= NIGHT_TIME) {
			timeTitle = TIME_TITLES[4];
		} else if (time > 0 && time <= EARLY_MORNING_TIME) {
			timeTitle = TIME_TITLES[5];
		}
		return timeTitle;
	}

	public static int getTimeIndex(long time) {
		int timeIndex = 0;
		if (time > EARLY_MORNING_TIME && time <= MORNING_TIME) {
			timeIndex = 0;
		} else if (time > MORNING_TIME && time < NOON_TIME) {
			timeIndex = 0;
		} else if (time >= NOON_TIME && time < EVENING_TIME) {
			timeIndex = 1;
		} else if (time >= EVENING_TIME && time < NIGHT_TIME) {
			timeIndex = 2;
		} else if (time >= NIGHT_TIME) {
			timeIndex = 3;
		} else if (time > 0 && time <= EARLY_MORNING_TIME) {
			timeIndex = 3;
		}
		return timeIndex;
	}

	public static int calculateAge(long dob) {

		int age = 0;
		try {
			if (dob != 0) {
				Date date = new Date();
				date.setTime(dob);
				Period period = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
						LocalDate.now());
				age = period.getYears();
			}
		} catch (Exception e) {
			logger.error("Exception while calculating age from DOB in DateUtil", e);
		}
		return age;
	}

	public static int calculateAgeInMonths(long dob) {

		logger.debug("dob" + dob);
		int age = 0;
		try {
			Date date = new Date();
			date.setTime(dob);
			Period period = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					LocalDate.now());
			logger.debug(period.getYears() + "::" + period.getMonths());
			age = (period.getMonths() + period.getYears() * 12);
		} catch (Exception e) {
			logger.error("Exception while calculating age from DOB in months in DateUtil", e);
		}
		return age;
	}

	public static String getAge(long dob) {
		String age = "";
		if (dob == 0) {
			return age;
		}
		int _age = calculateAge(dob);
		if (_age >= 18) {
			age = _age + " Yrs";
		} else if (_age > 0 && _age < 18) {
			age = _age + " Yrs ";
			long totalMonth = calculateAgeInMonths(dob);
			long month = (totalMonth % 12);
			age += month + " Mths";
		} else {
			_age = calculateAgeInMonths(dob);
			age = _age + " Mths";
		}
		return age;
	}

	public static int Immunization_PHR_TYPES(long dob, String gender) {

		int age = calculateAgeInMonths(dob);
		int phrType = 1;
		if (age >= (19 * 12)) {
			phrType = 2;
		}
		return phrType;
	}

	public static Calendar getCalenderDate(long inputDatetime) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTimeInMillis(inputDatetime);
		return calendar;
	}

	public static long setHoursMinsSecs(Calendar calendar, int hrs, int min, int sec, int millisec) {
		long result = 0;
		try {
			calendar.set(Calendar.HOUR_OF_DAY, hrs);
			calendar.set(Calendar.MINUTE, min);
			calendar.set(Calendar.SECOND, sec);
			calendar.set(Calendar.MILLISECOND, millisec);
			result = calendar.getTimeInMillis();
		} catch (Exception e) {
			logger.error("Exception while setting time in DateUtil", e);
		}
		return result;
	}

	public static long setYears(Calendar calendar, int year) {
		calendar.set(Calendar.YEAR, year);
		return calendar.getTimeInMillis();
	}

	public static long fetchTimeFromDateTime(long inputDateTime) {
		long result = 0;
		try {
			Calendar calendar = getCalenderDate(inputDateTime);
			result = (calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000)
					+ (calendar.get(Calendar.MINUTE) * 60 * 1000);
		} catch (Exception e) {
		}
		return result;
	}

	public static long addORsubDaysFromDateTime(long inputDateTime, int days, boolean isadd) {
		if (isadd) {
			return addTime(inputDateTime, 5, days);
		} else {
			return addTime(inputDateTime, 5, days * -1);
		}
	}

	public static int nextTimeSlot(long inputDateTime) {
		int timeSlotIndex = 0;
		try {
			long currentTime = fetchTimeFromDateTime(inputDateTime);
			currentTime = currentTime - TIMEZONE_DIFFERENTIAL;
			if (currentTime < MORNING_TIME) {
				return 0;
			} else if (currentTime > MORNING_TIME && currentTime < NOON_TIME) {
				return 1;
			} else if (currentTime > NOON_TIME && currentTime < EVENING_TIME) {
				return 2;
			} else if (currentTime > EVENING_TIME && currentTime < NIGHT_TIME) {
				return 3;
			} else if (currentTime > NIGHT_TIME) {
				return 4;
			}
		} catch (Exception e) {
			logger.error("Exception while nextTimeSlot in DateUtil", e);
		}
		return timeSlotIndex;
	}

	public static int fetchsessionString(long time) {
		int string = 0;
		try {
			int[] timings = { 19, 16, 12 };
			Calendar calendar = getCalenderDate(time);
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			for (int i = 0; i < timings.length; i++) {
				if (hours >= timings[i]) {
					string = timings.length - i;
					break;
				}
			}
			logger.debug("sessionTime" + string);
		} catch (Exception e) {
			logger.error("Exception while fetchsessionString in DateUtil", e);
		}
		return string;

	}

	public static String getDateFromTimeStamp(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("dd-MMM-yyyy").format(date);
	}

	public static String getDateFromTimeStampStartWithYear(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String getTimeFromTimeStamp(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("hh:mm a").format(date);
	}

	public static String getDateTimeFromTimeStamp(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("dd-MMM-yyyy hh:mm a").format(date);
	}

	public static Long convertTimeToUTC(Long timestamp) {
		return timestamp + 0;
	}

	public static Long convertUTCToLocalTime(Long timestamp) {
		return timestamp - 0;
	}

	public static Long getvalidToDate(long timeStamp, long dayOfYear) {
		Calendar calendar = getCalenderDate(timeStamp);
		int days = (int) (calendar.get(Calendar.DAY_OF_YEAR) + dayOfYear);
		calendar.set(Calendar.DAY_OF_YEAR, days);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public static Long setDateSecAndmilliZero(long dateTimeStamp) {
		Calendar calendar = getCalenderDate(dateTimeStamp);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static Long getTodayDateInMilli() {
		return removeTimeFromDateTime(getTimeStamp());
	}

	public static Long getCurrentHourInMilli() {
		Calendar calendar = getCalenderDate(getTimeStamp());
		return setHoursMinsSecs(calendar, calendar.get(Calendar.HOUR_OF_DAY), 0, 0, 0);
	}

	public static long removeTimeFromDateTime(long inputDateTime) {
		long result = 0;
		Calendar calendar = getCalenderDate(inputDateTime);
		result = setHoursMinsSecs(calendar, 0, 0, 0, 0);
		return result;
	}

	public static long fetchTimeInSecFromDateTime(long inputDateTime) {
		Calendar calendar = getCalenderDate(getTimeStamp());
		return (calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60) + (calendar.get(Calendar.MINUTE) * 60);
	}

	public static long getTimeForPassedStringTime(int hrs, int min, int sec) {
		Calendar calendar = getCalenderDate(getTimeStamp());
		calendar.set(1970, 0, 1, hrs, min, sec);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long getTimeOfTimeZone(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		calendar.set(1970, 0, 1, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long getCurrentTime() {
		return getTimeOfTimeZone(getTimeStamp());
	}

	public static long addCurrentTimetohrs(int hrs) {
		return addTime(getCurrentTime(), 4, hrs);
	}

	public static long stringDateTotimeStamp(String date) {
		long time = 0;
		try {
			Date d = format.parse(date);
			time = d.getTime();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String getDateFromTime(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}

	public static String getStringTimeFromTime(long timeStamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("hh:mm aa").format(date);
	}

	public static long getRoundedTime(long time, int level) {
		Calendar calendar = getCalenderDate(time);
		if (level >= 1) {
			calendar.set(Calendar.MILLISECOND, 0);
		}
		if (level >= 2) {
			calendar.set(Calendar.SECOND, 0);
		}
		if (level >= 3) {
			calendar.set(Calendar.MINUTE, 0);
		}
		if (level >= 4) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (level >= 5) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (level >= 6) {
			calendar.set(Calendar.MONTH, 1);
		}
		return calendar.getTimeInMillis();
	}

	public static long addTime(long time, int level, int frequency) {
		Calendar calendar = getCalenderDate(time);
		switch (level) {
		case 1:
			calendar.add(Calendar.MILLISECOND, frequency);
			break;
		case 2:
			calendar.add(Calendar.SECOND, frequency);
			break;
		case 3:
			calendar.add(Calendar.MINUTE, frequency);
			break;
		case 4:
			calendar.add(Calendar.HOUR_OF_DAY, frequency);
			break;
		case 5:
			calendar.add(Calendar.DAY_OF_MONTH, frequency);
			break;
		case 6:
			calendar.add(Calendar.MONTH, frequency);
			break;
		case 7:
			calendar.add(Calendar.YEAR, frequency);
			break;
		default:
			break;
		}
		return calendar.getTimeInMillis();
	}

	public static long convertStringToDate(String dateString) {
		long date = 0;
		List<SimpleDateFormat> knownPatterns = new ArrayList<>();
		knownPatterns.add(new SimpleDateFormat("dd-MM-yyyy"));

		knownPatterns.add(new SimpleDateFormat("dd/M/yy"));
		knownPatterns.add(new SimpleDateFormat("dd/MM/yy"));
		knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy"));
		knownPatterns.add(new SimpleDateFormat("dd/M/yyyy"));
		knownPatterns.add(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"));
		knownPatterns.add(new SimpleDateFormat("dd/M/yy HH:mm:ss z "));
		knownPatterns.add(new SimpleDateFormat("dd/M/yy HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z "));
		knownPatterns.add(new SimpleDateFormat("dd/M/yyyy HH:mm:ss z "));
		knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("dd/M/yyyy HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));

		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd"));

		knownPatterns.add(new SimpleDateFormat("dd-M-yy"));
		knownPatterns.add(new SimpleDateFormat("dd-MM-yy"));
		knownPatterns.add(new SimpleDateFormat("dd-M-yyyy"));
		knownPatterns.add(new SimpleDateFormat("dd-MMM-yyyy"));

		knownPatterns.add(new SimpleDateFormat("MM/dd/yyyy"));
		knownPatterns.add(new SimpleDateFormat("MMM d, yyyy"));// eg: "Sep 5, 2022"

		for (SimpleDateFormat pattern : knownPatterns) {
			try {
				return pattern.parse(dateString).getTime();
			} catch (java.text.ParseException pe) {
				// Loop on
			}
		}
		logger.debug("No known Date format found: " + dateString);
		return date;
	}

	public static String getFormattedTimeBasedOnTimestamp(long timeStamp, String formate) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat(formate).format(date);
	}

	public static long getDateTimeFromFormat(String dateTime, String type) {
		long timeInmillis = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(type);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			timeInmillis = sdf.parse(dateTime).getTime();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return timeInmillis;

	}

	public static String getLastDay(String year, String month) {

		// get a calendar object
		GregorianCalendar calendar = new GregorianCalendar();

		// convert the year and month to integers
		int yearInt = Integer.parseInt(year);
		int monthInt = Integer.parseInt(month);

		// adjust the month for a zero based index
		monthInt = monthInt - 1;

		// set the date of the calendar to the date provided
		calendar.set(yearInt, monthInt, 1);

		int dayInt = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return Integer.toString(dayInt);
	} // end getLastDay method

	public static int caluclateDobToAge(String DOB) {
		Calendar cal = getCalenderDate(getTimeStamp());
		int currentYear = cal.getWeekYear();

		String input = DOB;
		String[] out = input.split("-");

		int dobYear = Integer.parseInt(out[2]);
		int Age = (currentYear - dobYear);
		return Age;
	}

	public static long calculateAgeToDob(String AGE) {
		// TODO why this method
		long millis = 0;
		try {
			Calendar cal = Calendar.getInstance();
			int currentYear = cal.getWeekYear();
			int result = Integer.parseInt(AGE);
			@SuppressWarnings("deprecation")
			String dob1 = new Integer(currentYear - result).toString();
			String yyyy = dob1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date1;
			date1 = sdf.parse(yyyy);
			millis = date1.getTime();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return millis;
	}

	/* inputString = "00:01:30.500"; */
	public static long getTimeTomili(String inputString) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = sdf.parse("1970-01-01 " + inputString);
		return date.getTime();
	}

	public static long changeValueIntoDate(long time, int level, int value) {

		Calendar calendar = getCalenderDate(time);
		switch (level) {
		case 1:
			calendar.set(Calendar.MILLISECOND, value);
			break;
		case 2:
			calendar.set(Calendar.SECOND, value);
			break;
		case 3:
			calendar.set(Calendar.MINUTE, value);
			break;
		case 4:
			calendar.set(Calendar.HOUR_OF_DAY, value);
			break;
		case 5:
			calendar.set(Calendar.DAY_OF_MONTH, value);
			break;
		case 6:
			calendar.set(Calendar.DAY_OF_WEEK, value);
			break;
		case 7:
			calendar.set(Calendar.MONTH, value);
			break;
		case 8:
			calendar.set(Calendar.YEAR, value);
			break;
		default:
			break;
		}
		return calendar.getTimeInMillis();
	}

	public static long convertingDateFormatToLong(String date) {
		return getDateTimeFromFormat(date, "yyyy-MM-dd");
	}

	public static long getMergedDateAndTime(long date, long time) {
		return convertTimeToUTC(date + time);
	}

	public static Date getDefaultDateObj() throws java.text.ParseException {
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return isoFormat.parse("1970-01-01T00:00:01");
	}

	public static double getDistanceInkm(double lat1, double lon1, double lat2, double lon2) {
		long R = 6371; // Radius of the earth in km
		double dLat = deg2rad(lat2 - lat1); // deg2rad below
		double dLon = deg2rad(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c; // Distance in km
		return d;
	}

	public static double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}

	public static String getMonthNameAndYear(long timeStamp) {
		String date = DateUtil.getDateFromTimeStampStartWithYear(timeStamp);
		LocalDate currentDate = LocalDate.parse(date);
		// Get month & year from date
		Month month = currentDate.getMonth();
		int year = currentDate.getYear();
		return month.getDisplayName(TextStyle.SHORT, Locale.US) + "-" + year;
	}

	public static long getFirstDayOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTimeInMillis();
	}

	public static long convertDateStringToMillisec(String dateString) throws java.text.ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf1.parse(dateString);
		return date.getTime();
	}

	public static long getFirstDayOfNexttMonth() {

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, 1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.clear(Calendar.MINUTE);
		cal1.clear(Calendar.SECOND);
		cal1.clear(Calendar.MILLISECOND);
		// cal1.set(Calendar.DAY_OF_MONTH, 1);
		cal1.set(Calendar.DATE, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal1.getTimeInMillis();
	}

	public static long addNoOfDays(int num) {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, num);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);
		dt = c.getTime();
		return dt.getTime();
	}

	public static long convertDateTimeStringToMillisec(String dateString) throws ParseException {
		// String dateString ="28 Oct 2021 04:15 PM";
		long date = 0;
		List<SimpleDateFormat> knownPatterns = new ArrayList<>();

		knownPatterns.add(new SimpleDateFormat("dd MMM yyyy, hh:mm a"));
		knownPatterns.add(new SimpleDateFormat("dd MMM yyyy hh:mm a"));
		knownPatterns.add(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"));
		knownPatterns.add(new SimpleDateFormat("HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("dd-MMM-yyyy"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		knownPatterns.add(new SimpleDateFormat("MMM d, yyyy"));
		for (SimpleDateFormat pattern : knownPatterns) {
			try {
				date = pattern.parse(dateString).getTime();
			} catch (java.text.ParseException pe) {
			}
		}
		return date;
	}

	public static long convertDateTimeStringToMilliseconds(String dateString) throws ParseException {
		long date = 0;
		List<SimpleDateFormat> knownPatterns = new ArrayList<>();
		knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy"));
		for (SimpleDateFormat pattern : knownPatterns) {
			try {
				date = pattern.parse(dateString).getTime();
			} catch (java.text.ParseException pe) {
			}
		}
		return date;
	}

	public static String convertingTimeFormatToLong(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("HH:mm").format(date);
	}

	public static String convertingDateFormatToLong(long timeStamp) {
		Calendar calendar = getCalenderDate(timeStamp);
		Date date = calendar.getTime();
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public static String getTimeDifference(long startDateTime, long endDateTime) throws java.text.ParseException {

		String time1 = DateUtil.getFormattedTimeBasedOnTimestamp(startDateTime, "HH:mm:ss");
		String time2 = DateUtil.getFormattedTimeBasedOnTimestamp(endDateTime, "HH:mm:ss");

		// Creating a SimpleDateFormat object
		// to parse time in the format HH:MM:SS
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		// Parsing the Time Period
		Date date1 = simpleDateFormat.parse(time1);
		Date date2 = simpleDateFormat.parse(time2);

		// Calculating the difference in milliseconds
		long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());

		// Calculating the difference in Hours
		String differenceInHours = String.valueOf(((differenceInMilliSeconds / (60 * 60 * 1000)) % 24));

		// Calculating the difference in Minutes
		String differenceInMinutes = String.valueOf(((differenceInMilliSeconds / (60 * 1000)) % 60));

		// Calculating the difference in Seconds
		String differenceInSeconds = String.valueOf(((differenceInMilliSeconds / 1000) % 60));

		return (differenceInHours.length() == 1 ? (0 + differenceInHours) : differenceInHours) + ":"
				+ (differenceInMinutes.length() == 1 ? (0 + differenceInMinutes) : differenceInMinutes) + ":"
				+ (differenceInSeconds.length() == 1 ? (0 + differenceInSeconds) : differenceInSeconds);

	}
	
	public static long getMilliMinuteFromNanoSec(String nanoTime) {
		String startTime = nanoTime.substring(0, nanoTime.length() - 6);
		long milisTime = removeSecondFromDateTime(Long.valueOf(startTime));
		return milisTime;
	}
	public static long removeSecondFromDateTime(long inputDateTime) {
		long result = 0;
		Calendar calendar = getCalenderDate(inputDateTime);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		result = calendar.getTimeInMillis();
		return result;
	}
	public static long getMilliSecFromNanoSec(String nanoSecValue) {
		BigInteger bigInt = new BigInteger(nanoSecValue);
		long miliSec = bigInt.divide(BigInteger.valueOf(1000000l)).longValueExact();
		return miliSec;
	}

}
