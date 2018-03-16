package com.website.core.page;

/**
 * 类描述： 根据页码个数、当前页面、总页数,计算下标 起始位置&结束位置
 */
public class PageIndex {
	
	private long startindex;
	private long endindex;
	
	public PageIndex(long startindex,long endindex){
		this.startindex = startindex;
		this.endindex = endindex;
	}

	public long getStartindex() {
		return startindex;
	}

	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}

	public long getEndindex() {
		return endindex;
	}

	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}
	
	/**
	 * 方法名: getPageIndex 
	 * 描述: 根据所需参数,计算下标 起始位置&结束位置
	 * 参数：
	 * @param pageCode		显示下标个数
	 * @param currentPage	当前页面
	 * @param totalPage		总页数
	 * @return   
	 * @return PageIndex   
	 * @throws
	 */
	public static PageIndex getPageIndex(long pageCode,int currentPage,long totalPage){
		long startpage = currentPage- (pageCode%2 == 0 ? pageCode/2-1 : pageCode/2);
		long endpage= currentPage+ pageCode/2;
		if(startpage< 1 ){
			startpage= 1;
			if(totalPage >= pageCode)endpage= pageCode;
			else endpage = totalPage;
		}
		if(endpage> totalPage){
			endpage= totalPage;
			if((endpage- pageCode)> 0)startpage= endpage- pageCode+ 1;
			else startpage= 1;
		}
		return new PageIndex(startpage,endpage);
	}
	
	public static void main(String[] args){
		long pageCode = 5;
		int currentPage = 1;
		long tatalPage = 2;
		PageIndex p= getPageIndex(pageCode,currentPage,tatalPage);
		for(long i= p.getStartindex();i <= p.getEndindex();i++){
			System.out.print(currentPage == i ? "【"+i+"】" : "["+i+"]");
		}
	}

}
