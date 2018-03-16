package com.website.baseserver.services;

import com.website.baseserver.entity.News;
import com.website.core.page.Page;
import com.website.core.page.Pageable;

/**
 * 新闻
 * @author Administrator
 *
 */
public interface NewsService {

	/**
	 * 保存
	 * @param news
	 */
	public void save(News news);
	
	/**
	 * 修改
	 * @param news
	 */
	public void update(News news);
	
	/**
	 * 物理删除
	 * @param news
	 */
	public void delete(News news);
	
	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	public News getNewsById(String id);
	
	/**
	 * 同步新闻
	 */
	public void sysnNews()throws Exception;
	
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	public Page<News> findPage(Pageable pageable,String title);
}
