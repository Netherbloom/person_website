package com.website.blog.services;

import java.util.List;

import com.website.blog.entity.Blog;

public interface BlogServices {

	/**
	 * 根据userid获取blog
	 * @return
	 */
	public List<Blog> findBlogsList(String userId);
	
	/**
	 * 保存或修改blog
	 * @param blog
	 * @return
	 */
	public boolean saveOrUpdate(Blog blog);
	
	/**
	 * 删除一条记录
	 * @param blog
	 * @return
	 */
	public boolean delete(Blog blog);
	
	
	/**
	 * 根据主键获取记录
	 * @param id
	 * @return
	 */
	public Blog findBlogById(String id);
	
	/**
	 * 获取此节点下的所有数据
	 * @param blogId
	 * @return
	 */
	public List<Blog> findBlogsListById(String blogId);
	
	/**
	 * 获取热门博客
	 * @return
	 */
	public List<Blog> findHotBlogsList();
	
}
