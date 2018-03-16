package com.website.core.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期范围
 * @author liangli
 *
 */
public class DateRange implements Serializable {

	/** 序列号 */
	private static final long serialVersionUID = 1L;
	
	// 开始日期
	private String start;
	// 结束日期
	private String end;
	// 天数
	private int dayCount = 0;
	
	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	/**
	 * 指定日期范围
	 * @param start
	 * @param end
	 */
	public DateRange(Date start, Date end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.start = df.format(start);
		this.end = df.format(end);
	}
	
	/**
	 * 指定日期范围
	 * @param start
	 * @param end
	 */
	public DateRange(String start, String end) {
	
		this.start = start;
		this.end = end;
	}
	
	/**
	 * 当前日期范围
	 * @param field Calendar.DAY_OF_YEAR 日，Calendar.MONTH 月
	 * @param amount >0向后，<0向前
	 */
	public DateRange(int field, int amount) {
		
		init(System.currentTimeMillis(), field, amount);
	}
	
	/**
	 * 指定日期范围
	 * @param date
	 * @param field Calendar.DAY_OF_YEAR 日，Calendar.MONTH 月
	 * @param amount >0向后，<0向前
	 */
	public DateRange(Date date, int field, int amount) {
		
		init(date.getTime(), field, amount);
	}
	
	/**
	 * 当前时间范围
	 * @param range 格式“[n]X”，n为数量没有表示（今天/本周/本月），X为单位“d天,w周,m月”
	 */
	public DateRange(String range) {
		
		if (null == range || range.length() < 1) return;
		char c = range.charAt(range.length() - 1);
		int amount = 0;
		if (range.length() > 1)
			amount = Integer.parseInt(range.substring(0, range.length() - 1));
		long now = System.currentTimeMillis();
		if ('d' == c || 'D' == c) {
			
			// 天
			init(now, Calendar.DAY_OF_YEAR, amount);
		}
		else if ('w' == c || 'W' == c) {
			
			// 周
			if (0 == amount)
				init(now, Calendar.WEEK_OF_YEAR, amount);
			else
				init(now, Calendar.DAY_OF_YEAR, amount * 7);
		}
		else if ('m' == c || 'M' == c) {
			
			// 月
			init(now, Calendar.MONTH, amount);
		}
	}
	
	/**
	 * 指定日期范围
	 * @param date 日期
	 * @param range 格式“nX”，n为数量，X为单位“d天,w周,m月”
	 */
	public DateRange(Date date, String range) {
		
		if (null == range || range.length() < 2) return;
		char c = range.charAt(range.length() - 1);
		int amount = Integer.parseInt(range.substring(0, range.length() - 1));
		if ('d' == c || 'D' == c) {
			
			// 天
			init(date.getTime(), Calendar.DAY_OF_YEAR, amount);
		}
		else if ('w' == c || 'W' == c) {
			
			// 周
			if (0 == amount)
				init(date.getTime(), Calendar.WEEK_OF_YEAR, amount);
			else
				init(date.getTime(), Calendar.DAY_OF_YEAR, amount * 7);
		}
		else if ('m' == c || 'M' == c) {
			
			// 月
			init(date.getTime(), Calendar.MONTH, amount);
		}
	}
	
	/**
	 * 生成范围
	 * @param base
	 * @param field
	 * @param amount
	 */
	private void init(long base, int field, int amount) {

		FastDate now = new FastDate(base);
		if (0 == amount) {
		
			if (Calendar.MONTH == field) {
				
				// 本月
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				now.setTime(cal.getTime().getTime());
				this.start = now.toDateText();
				now = now.AddDate(Calendar.MONTH, 1);
				this.end = now.toDateText();
			}
			else if (Calendar.WEEK_OF_YEAR == field) {
				
				// 本周
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				cal.set(Calendar.DAY_OF_WEEK, 1);
				now.setTime(cal.getTime().getTime());
				this.start = now.toDateText();
				now = now.AddDate(Calendar.DAY_OF_YEAR, 7);
				this.end = now.toDateText();
			}
			else {
				
				// 今天
				dayCount = 1;
				this.start = now.toDateText();
				now = now.AddDate(Calendar.DAY_OF_YEAR, 1);
				this.end = now.toDateText();
			}
			
			return;
		}
		
		if (Calendar.DAY_OF_YEAR == field) {
			
			// 天
			dayCount = Math.abs(amount);
			if (amount > 0) {
				
				// 后
				this.start = now.toDateText();
				now = now.AddDate(Calendar.DAY_OF_YEAR, amount);
				this.end = now.toDateText();
			}
			else {
				
				// 前
				now = now.AddDate(Calendar.DAY_OF_YEAR, 1);
				this.end = now.toDateText();
				now = now.AddDate(Calendar.DAY_OF_YEAR, amount);
				this.start = now.toDateText();
			}
		}
		else if (Calendar.MONTH == field) {
			
			// 月
			if (amount > 0) {
				
				// 后
				this.start = now.toDateText();
				now = now.AddDate(Calendar.MONTH, amount);
				this.end = now.toDateText();
			}
			else {
				
				// 前
				now = now.AddDate(Calendar.MONTH, amount);
				this.start = now.toDateText();
				now = new FastDate(base);
				now = now.AddDate(Calendar.DAY_OF_YEAR, 1);
				this.end = now.toDateText();
			}
		}
	}
	
	/**
	 * 取天数
	 * @return
	 */
	public int getDayCount() {
		
		if (0 == dayCount) {
			
			// 计算
			FastDate d2 = new FastDate(this.start);
			FastDate d1 = new FastDate(this.end);
			dayCount = d1.compareTo(Calendar.DAY_OF_MONTH, d2);
		}
		return dayCount;
	}
}
