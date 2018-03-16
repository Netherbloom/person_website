package com.website.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期
 * 
 * @author liangli
 * 
 */
public class FastDate extends Date {

	private static final long serialVersionUID = 1L;
	public static final int YEAR = 1;
	public static final int MONTH = 2;
	public static final int DATE = 5;
	public static final int HOUR = 11;
	public static final int MINUTE = 12;
	public static final int SECOND = 13;
	public static final int MILLISECOND = 14;

	public FastDate() {
	}

	public FastDate(long date) {
		super(date);
	}

	public FastDate(String strDate) {
		if (strDate != null) {
			SimpleDateFormat format = null;
			if (strDate.length() == 19)
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else if (strDate.length() == 10)
				format = new SimpleDateFormat("yyyy-MM-dd");
			else if (strDate.length() == 8)
				format = new SimpleDateFormat("HH:mm:ss");
			if (format != null)
				try {
					setTime(format.parse(strDate).getTime());
				} catch (ParseException localParseException) {
				}
		}
	}

	public FastDate AddDate(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this);
		cal.add(field, amount);
		setTime(cal.getTime().getTime());
		return this;
	}

	public int compareTo(int field, java.util.Date newDate) {
		int result = 0;
		int y = 0;
		int m = 0;
		int d = 0;
		int h = 0;
		int mm = 0;
		Calendar cal = Calendar.getInstance();
		switch (field) {
		case 1:
			cal.setTime(this);
			y = cal.get(1);
			cal.setTime(newDate);
			result = y - cal.get(1);
			break;
		case 2:
			cal.setTime(this);
			y = cal.get(1);
			m = cal.get(2);
			cal.setTime(newDate);
			result = y - cal.get(1) * 12;
			result = m - cal.get(2) + result * 12;
			break;
		case 5:
			cal.setTime(this);
			y = cal.get(1);
			d = cal.get(6);
			cal.setTime(newDate);
			result = y - cal.get(1);
			result = d - cal.get(6) + result * 365;
			break;
		case 11:
			cal.setTime(this);
			y = cal.get(1);
			d = cal.get(6);
			h = cal.get(11);
			cal.setTime(newDate);
			result = y - cal.get(1);
			result = d - cal.get(6) + result * 365;
			result = h - cal.get(11) + result * 24;
			break;
		case 12:
			cal.setTime(this);
			y = cal.get(1);
			d = cal.get(6);
			h = cal.get(11);
			mm = cal.get(12);
			cal.setTime(newDate);
			result = y - cal.get(1);
			result = d - cal.get(6) + result * 365;
			result = h - cal.get(11) + result * 24;
			result = mm - cal.get(12) + result * 60;
		case 3:
		case 4:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		}
		return result;
	}

	public String dateStringToXMLText(String dateString) throws Exception {
		
		if ((dateString == null) || (dateString.equals("")))
			return "";

		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		FastDate date = new FastDate(format.parse(dateString).getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String dateText = sdf.format(date);
		return dateText;
	}

	public void exactAdd(int field, float amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this);
		cal.add(field, (int) amount);
		int n = 0;
		switch (field) {
		case 5:
			n = (int) (24.0F * (amount - (int) amount));
			if (n <= 0)
				break;
			cal.add(10, n);
			break;
		case 11:
			n = (int) (60.0F * (amount - (int) amount));
			if (n <= 0)
				break;
			cal.add(12, n);
			break;
		case 12:
			n = (int) (60.0F * (amount - (int) amount));
			if (n <= 0)
				break;
			cal.add(13, n);
		}

		setTime(cal.getTime().getTime());
	}

	public float exactCompareTo(int field, java.util.Date newDate) {
		float result = 0.0F;
		int y = 0;

		int d = 0;
		int h = 0;
		int minute = 0;
		Calendar cal = Calendar.getInstance();
		switch (field) {
		case 5:
			cal.setTime(this);
			y = cal.get(1);
			d = cal.get(6);
			h = cal.get(11);
			cal.setTime(newDate);
			result = y - cal.get(1);
			result = d - cal.get(6) + result * 365.0F;
			h -= cal.get(11);
			result = (float) (result + h / 24.0D);
			break;
		case 11:
			cal.setTime(this);
			y = cal.get(1);
			d = cal.get(6);
			h = cal.get(11);
			minute = cal.get(12);
			cal.setTime(newDate);
			result = y - cal.get(1);
			result = d - cal.get(6) + result * 365.0F;
			result = h - cal.get(11) + result * 24.0F;
			minute -= cal.get(12);
			result = (float) (result + minute / 60.0D);
		}

		return result;
	}

	public int get(int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this);
		if (field == 2) {
			return cal.get(field) + 1;
		}
		return cal.get(field);
	}

	public String getToday() {
		FastDate d = new FastDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}

	public String toDateText() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this);
	}

	public String toDBFormatText(String dateString) {
		if ((dateString == null) || (dateString.equals("")))
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			FastDate date = new FastDate(sdf.parse(dateString).getTime());
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String dateText = format.format(date);
			return dateText;
		} catch (Exception e) {
		}
		return null;
	}

	public String toText() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(this);
	}

	public String toText(java.sql.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public String toText1() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(this);
	}

	public String toText2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this);
	}

	public String toTimeText() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(this);
	}

	public String toChineseDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(this);
	}
}