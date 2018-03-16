package com.website.baseserver.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.website.baseserver.entity.City;
import com.website.baseserver.entity.News;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;

/**
 * 新闻
 * @author Administrator
 *
 */
@Repository
public class NewsDao extends BaseDaoWithQuery<News, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected NewsDao() {
		super(News.class);
	}
	
	/**
	 * 检查新闻是否存在
	 * @param title
	 * @return存在 true 
	 */
	public boolean checkNews(String title){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from news where  title='" +title+"'");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<City> mq = new MapQuery<City>(City.class);
		List<City>list=mq.getResultList(query);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
}
