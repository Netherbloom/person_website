package com.website.baseserver.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.website.baseserver.entity.Weather;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;

/**
 * 天气接口
 * @author Administrator
 *
 */
@Repository
public class WeatherDao extends BaseDaoWithQuery<Weather, String>{
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected WeatherDao() {
		super(Weather.class);
	}
	/**
	 * 获取城市最新天气
	 * @param citycode
	 * @return
	 */
	public Weather getNewWeather(String citycode){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from weather where  citycode='" +citycode+"' order by updatetime desc limit 1" );
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Weather> mq = new MapQuery<Weather>(Weather.class);
		List<Weather>list=mq.getResultList(query);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
