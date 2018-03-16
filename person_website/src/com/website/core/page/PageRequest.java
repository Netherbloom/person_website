package com.website.core.page;

/**
 * 分页请求
 * @author liangli
 *
 */
public class PageRequest implements Pageable {
	
	private int pageNumber;
	private int pageSize;
	private Sort sort;

	public PageRequest(int page, int size) {
		
		if (page < START_INDEX) page = START_INDEX;
		this.pageNumber = page;
		this.pageSize = size;
	}

	public int getOffset() {

		return (this.pageNumber - START_INDEX) * this.pageSize;
	}

	public int getPageNumber() {

		return this.pageNumber;
	}

	public int getPageSize() {

		return this.pageSize;
	}

	public Sort getSort() {

		return this.sort;
	}
	
	public PageRequest setSort(Sort sort) {
		this.sort = sort;
		return this;
	}
}
