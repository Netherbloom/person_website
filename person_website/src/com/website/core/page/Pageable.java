package com.website.core.page;

/**
 * 分页定义
 * @author liangli
 *
 */
public interface Pageable {
	
	/** 页号开始数 */
	public static final int START_INDEX = 1;

	public int getOffset();

	public int getPageNumber();

	public int getPageSize();

	public Sort getSort();
}
