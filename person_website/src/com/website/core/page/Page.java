package com.website.core.page;

import java.util.List;



/**
 * 分页
 * @author PanPan wendellsheH
 * 
 */
public class Page<T> {
	
	private int pageCount;
	private int rowCount;
	private int pageno;
	private int displayCol; //显示栏;
	private List<T> results;
	
	private PageIndex pageIndex;
	
	public Page(List<T> results, Pageable pageable, int total) {
		
		this.pageno = pageable.getPageNumber();
		this.pageCount = total / pageable.getPageSize();
		if (0 != total % pageable.getPageSize()) this.pageCount ++;
		this.rowCount = total;
		this.results = results;
		this.setDisplayCol(5);//默认下标 最高为5，如果不足 以实际为准
		this.pageIndex = PageIndex.getPageIndex(this.displayCol, pageno, pageCount);
	}
	
	public Page(int page, int pageCount, int rowCount, List<T> results) {
		// TODO Auto-generated constructor stub
		this.pageno = 0 == page?1:page;
		this.pageCount = pageCount;
		this.rowCount = rowCount;
		this.setDisplayCol(5);//默认下标 最高为5，如果不足 以实际为准
		this.pageIndex = PageIndex.getPageIndex(this.displayCol, page, pageCount);
		this.results = results;
	}
	public Page(int page, int pageCount, int rowCount, int displayCol, List<T> results) {
		// TODO Auto-generated constructor stub
		this.pageno = 0 == page?1:page;
		this.pageCount = pageCount;
		this.rowCount = rowCount;
		this.setDisplayCol(displayCol);
		this.pageIndex = PageIndex.getPageIndex(this.displayCol, page, pageCount);
		this.results = results;
	}
	
	
	public PageIndex getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageno() {
		return pageno;
	}
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	public int getDisplayCol() {
		return displayCol;
	}
	public void setDisplayCol(int displayCol) {
		if(displayCol < 3){
			this.displayCol = 3;
		}else{
			this.displayCol = displayCol;
		}
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}	
}
