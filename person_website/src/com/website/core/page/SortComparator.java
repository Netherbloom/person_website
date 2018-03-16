package com.website.core.page;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * 排序比较器
 * @author liangli
 *
 * @param <T>
 */
public class SortComparator<T> implements Comparator<T> {
	
	List<Sort> mSorts = null;
	
	public SortComparator(Sort sort) {

		if (null != sort) mSorts = sort.getAll();
	}

	public int compare(T item1, T item2) {

		if (null == mSorts) return 0;
		
		PropertyDescriptor pd;
		int nRet = 0;
		Object v1 = null, v2 = null;
		for (Sort sort : mSorts) {
			
			pd = BeanUtils.getPropertyDescriptor(item1.getClass(), sort.name);
			String name = pd.getPropertyType().getSimpleName().toLowerCase();
			Method m = pd.getReadMethod();
			
			try {
				
				v1 = m.invoke(item1);
				v2 = m.invoke(item2);
			}
			catch (Exception e) {

				// 无效属性
				e.printStackTrace();
			}
			
			if (null == v1 && null == v2)
				nRet = 0;
			else if (null == v1)
				nRet = 1;
			else if (null == v2)
				nRet = -1;
			else {
				
				if ("int".equals(name)) {
					
					nRet = this.compareInt(v1, v2);
				}
				else if ("long".equals(name)) {
					
					nRet = this.compareLong(v1, v2);
				}
				else if ("double".equals(name)) {
					
					nRet = this.compareDouble(v1, v2);
				}
				else if ("date".equals(name)) {
					
					nRet = this.compareDate(v1, v2);
				}
				else
					nRet = this.compareString(v1, v2);
			}
			
			if (0 != nRet) {
				
				if (!sort.isAsc) nRet = -nRet; // 倒序
				break;
			}
		}
		return nRet;
	}
	
	private int compareInt(Object item1, Object item2) {
		
		return item1.hashCode() - item2.hashCode();
	}
	
	private int compareLong(Object item1, Object item2) {
		
		return (item1.hashCode() - item2.hashCode());
	}
	
	private int compareDouble(Object item1, Object item2) {
		
		return ((Number)item1).doubleValue() > ((Number)item2).doubleValue() ? 1 : -1;
	}
	
	private int compareDate(Object item1, Object item2) {
		
		return ((Date)item1).getTime() > ((Date)item2).getTime() ? 1 : -1;
	}
	
	private int compareString(Object item1, Object item2) {
		
		return item1.toString().compareToIgnoreCase(item2.toString());
	}

}
