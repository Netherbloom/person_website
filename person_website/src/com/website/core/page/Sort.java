package com.website.core.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序
 * @author liangli
 *
 */
public class Sort {

	protected String name;
	protected boolean isAsc;
	
	private List<Sort> mSorts;
	
	/**
	 * 解析
	 * @param str
	 * @return
	 */
	public static Sort parese(String str) {

		if (null == str || str.length() < 1) return null;

		String[] strs = str.split(",");
		if (null != strs && strs.length > 0) {

			Sort s = null;
			boolean bAsc = true;
			int nIdx = 0;
			for (String item : strs) {

				item = item.trim();
				nIdx = item.indexOf(' ');
				if (nIdx > 0) {

					if ("desc".equalsIgnoreCase(item.substring(1 + nIdx).trim())) bAsc = false;

					item = item.substring(0, nIdx).trim();
				}
				if (null == s)
					s = new Sort(item, bAsc);
				else
					s.and(new Sort(item, bAsc));
			}
			return s;
		} 
		else
			return null;
	}
	
	/**
	 * 构造函数
	 * @param name
	 * @param asc
	 */
	public Sort(String name, boolean asc) {
		
		this.name = name;
		this.isAsc = asc;
	}
	
	/**
	 * 构造函数
	 * @param name
	 * @param asc
	 */
	public Sort(boolean asc, String... names) {
		
		if (null == names) return;
		this.name = names[0];
		this.isAsc = asc;
		if (names.length > 1) {
			
			for (int i=1; i<names.length; i++) this.and(new Sort(names[i], asc));
		}
	}

	/**
	 * 添加排序
	 * @param sort
	 * @return
	 */
	public Sort and(Sort sort) {

		if (null == mSorts) mSorts = new ArrayList<Sort>(4);
		mSorts.add(sort);
		return this;
	}
	
	/**
	 * 转查询语句
	 * @return
	 */
	public String toQL() {
		
		StringBuilder sb = new StringBuilder(64);
		sb.append(" order by ");
		toTheQL(sb, null);
		return sb.toString();
	}
	
	/**
	 * 转查询语句
	 * @return
	 */
	public String toQL(String alias) {
		
		StringBuilder sb = new StringBuilder(64);
		sb.append(" order by ");
		toTheQL(sb, alias);
		return sb.toString();
	}

	/**
	 * 写入查询语句
	 * @param ql
	 */
	private void toTheQL(StringBuilder ql, String alias) {

		if (null != alias && alias.length() > 0) ql.append(alias).append('.');
		ql.append(name);
		if (!isAsc) ql.append(" DESC");
		
		if (null != mSorts) {

			for (Sort item : mSorts) {
				
				ql.append(',');
				item.toTheQL(ql, alias);
			}
		}
	}
	
	/**
	 * 取所有
	 * @return
	 */
	protected List<Sort> getAll() {

		ArrayList<Sort> ret = new ArrayList<Sort>();
		ret.add(this);
		if (null != mSorts) ret.addAll(mSorts);
		return ret;
	}
}
