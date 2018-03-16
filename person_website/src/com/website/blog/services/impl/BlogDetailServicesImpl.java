package com.website.blog.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.website.blog.dao.BlogDetailDao;
import com.website.blog.entity.BlogDetail;
import com.website.blog.services.BlogDetailServices;
import com.website.core.dao.QueryWhere;

@Service
public class BlogDetailServicesImpl implements BlogDetailServices{

	
	@Autowired
	private BlogDetailDao blogDetailDao;

	@Transactional
	@Override
	public boolean deleteById(BlogDetail detail) {
		try {
			 blogDetailDao.delete(detail);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public List<BlogDetail> findDetailsByBlogId(String blogId,int status) {
		QueryWhere where=new QueryWhere();
		where.and("blogId",blogId);
		if(status>0){
			where.and("status",status);
		}
		return  blogDetailDao.findByWhere(where);
	}

	@Transactional
	@Override
	public void save(BlogDetail detail) {
		detail.setCreateTime(DateTimeUtil.getCurDateTime());
		int sort=blogDetailDao.getMaxSort(detail.getBlogId());
		detail.setSort(sort+1);
		blogDetailDao.save(detail);
		
	}

	@Transactional
	@Override
	public void update(BlogDetail detail) {
		// TODO Auto-generated method stub
		
	}

}
