package com.website.baseserver.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.website.baseserver.entity.Config;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;

/**
 * 系统参数配置
 * @author dream
 *
 */
@Repository
public class ConfigDao extends BaseDaoWithQuery<Config, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected ConfigDao() {
		super(Config.class);
	}
	
	/**
	 * 检查key是否存在
	 * @param key
	 * @return 是true;否 false
	 */
	public boolean checkCity(String key){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from config where  systemkey='" +key+"'");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Config> mq = new MapQuery<Config>(Config.class);
		List<Config>list=mq.getResultList(query);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据key值查询
	 * @param key
	 * @return
	 */
	public Config getConfigByKey(String key){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from config where  systemkey='" +key+"'");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Config> mq = new MapQuery<Config>(Config.class);
		List<Config>list=mq.getResultList(query);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
