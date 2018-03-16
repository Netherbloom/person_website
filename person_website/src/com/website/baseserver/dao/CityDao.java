package com.website.baseserver.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.website.baseserver.entity.City;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;

/**
 * 城市dao
 * @author dream
 *
 */
@Repository
public class CityDao extends BaseDaoWithQuery<City, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected CityDao() {
		super(City.class);
	}
	
	/**
	 * 检查城市是否存在
	 * @param code
	 * @return 是true;否 false
	 */
	public boolean checkCity(String code){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from city where  citycode='" +code+"'");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<City> mq = new MapQuery<City>(City.class);
		List<City>list=mq.getResultList(query);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
}
