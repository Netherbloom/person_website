package com.website.user.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.user.entity.Userinfo;

@Repository
public class UserinfoDao extends BaseDaoWithQuery<Userinfo, String>{
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public UserinfoDao(){
		super(Userinfo.class);
	}

	/**
	 * 获取最大会员号
	 * @return
	 */
	public int getMaxAccount() {
		String sql = "select max(cast(username as signed) ) from userinfo";
		Object result = em.createNativeQuery(sql).getSingleResult();
		if(null != result){
			return Integer.parseInt(result.toString());
		}
		return -1;
	}
	
	
}
