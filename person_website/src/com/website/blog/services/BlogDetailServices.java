package com.website.blog.services;

import java.util.List;

import com.website.blog.entity.BlogDetail;

public interface BlogDetailServices {

	/**
	 * 根据主键删除一条信息
	 * @param detail
	 * @return
	 */
	public boolean deleteById(BlogDetail detail);
	
	/**
	 * 根据博客id获取详细
	 * @return
	 */
	public List<BlogDetail> findDetailsByBlogId(String blogId,int status);
	
	/**
	 * 添加一条信息
	 */
	public void save(BlogDetail detail);
	
	/**
	 * 修改
	 * @param detail
	 */
	public void update(BlogDetail detail);
	
}
