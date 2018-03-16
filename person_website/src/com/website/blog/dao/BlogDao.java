package com.website.blog.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.website.blog.entity.Blog;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;

@Repository
public class BlogDao  extends BaseDaoWithQuery<Blog, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected BlogDao() {
		super(Blog.class);
	}
	
	/**
	 * 获取个人
	 * @param userId
	 * @return
	 */
	public List<Blog> getBlogsByUserId(String userId){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from blog where status=1 and userId='" )
		.append(userId).append("' order by createTime");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Blog> mq = new MapQuery<Blog>(Blog.class);
		List<Blog> list=mq.getResultList(query);
		return list;
	}

	/**
	 * 获取子节点
	 * @param parentId
	 * @return
	 */
	public List<Blog> getBlogsChild(String parentId){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from blog where status=1 and parentId='" )
		.append(parentId).append("' order by createTime");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Blog> mq = new MapQuery<Blog>(Blog.class);
		List<Blog> list=mq.getResultList(query);
		return list;
	}
	
	/**
	 * 获取热门博客
	 * @return
	 */
	public List<Blog> getHotBlogsList(){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from blog where status=1 " )
		.append(" order by likeTimes limit 5 ");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Blog> mq = new MapQuery<Blog>(Blog.class);
		List<Blog> list=mq.getResultList(query);
		return list;
	}
	
}
