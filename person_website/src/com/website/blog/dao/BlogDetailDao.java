package com.website.blog.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.website.blog.entity.BlogDetail;
import com.website.core.dao.BaseDaoWithQuery;

@Repository
public class BlogDetailDao extends BaseDaoWithQuery<BlogDetail, String>{
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected BlogDetailDao() {
		super(BlogDetail.class);
	}
	
	/**
	 * 根据blogId删除
	 * @param blogId
	 */
	public void deleteByBlogId(String blogId){
		Query query=em.createQuery("delete from  BlogDetail  where blogId=:blogId");
		query.setParameter("blogId", blogId);
		query.executeUpdate();
	}
	
	/**
	 * 获取最大排序
	 * @param blogId
	 * @return
	 */
	public int getMaxSort(String blogId){
		int sort=0;
		Query q = em.createQuery("select max(pri) from ActivityComment");
		Object result = q.getSingleResult();
		if(null != result){
			sort =Integer.parseInt(result.toString()); 
		}
		return sort;
	}
}
