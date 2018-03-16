package com.website.admin.service;

import java.util.List;

import com.website.admin.entity.EbookChapter;
import com.website.admin.entity.Ebooks;

public interface EbookChapterService {

	/**
	 * 获取最大章节
	 * @param ebookid
	 * @return
	 */
	public int getMaxPri(String ebookid);
	
	/**
	 * 批量新增
	 * @param list
	 */
	public void save(List<EbookChapter> list);
	
	/**
	 * 同步书籍章节
	 * @param ebook
	 * @param num
	 */
	public void syncinitChapter(Ebooks ebook,int num);
	
	/**
	 * 更新书籍
	 * @param ebooks
	 */
	public void updateEbook(Ebooks ebooks);
}
