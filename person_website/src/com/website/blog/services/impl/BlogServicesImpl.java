package com.website.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.website.blog.dao.BlogDao;
import com.website.blog.dao.BlogDetailDao;
import com.website.blog.entity.Blog;
import com.website.blog.services.BlogServices;

@Service
public class BlogServicesImpl implements BlogServices{

	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private BlogDetailDao blogDetailDao;

	@Override
	public List<Blog> findBlogsList(String userId) {
		return blogDao.getBlogsByUserId(userId);
	}

	@Transactional
	@Override
	public boolean saveOrUpdate(Blog blog) {
		try {
			if(!StringUtils.isBlank(blog.getId())){//修改
				Blog blog2=blogDao.getById(blog.getId());
				blog2.setTitle(blog.getTitle());
				blogDao.update(blog);
			}else{//新增
				blog.setStatus(1);
				blog.setCreateTime(DateTimeUtil.getCurDateTime());
				blog.setBrowseTimes(0);
				blogDao.save(blog);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean delete(Blog blog) {
		try {
			List<Blog> list=getBlogChilds(blog);//下一步：删除子节点
			blogDao.delete(blog);
			blogDetailDao.deleteByBlogId(blog.getId());
			for(Blog blog2:list){
				blogDao.delete(blog2);
				blogDetailDao.deleteByBlogId(blog2.getId());
			}
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public Blog findBlogById(String id) {
		Blog blog=blogDao.getById(id);
		if(blog!=null) {
			if(blog.getContent()!=null){
				blog.setContentstr(new String(blog.getContent()));
			}else{
				blog.setContentstr("");
			}
			return blog;
		}
		return null;
	}
	
	public List<Blog> getBlogChilds(Blog blog){  
        List<Blog> result=new ArrayList<Blog>();  
        List<Blog> list=blogDao.getBlogsChild(blog.getId());  
        if(list!=null &&list.size()>0){  
            for(Blog blog1:list){  
            //	blog1. (getBlogChilds(blog1));
                result.add(blog1);  
                result.addAll(getBlogChilds(blog1));
            }  
        }  
        return result;  
    }

	@Override
	public List<Blog> findBlogsListById(String blogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Blog> findHotBlogsList() {
		return blogDao.getHotBlogsList();
	}  
	
}
