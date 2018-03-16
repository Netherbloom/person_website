package com.website.baseserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.website.baseserver.entity.RequestLog;
import com.website.core.dao.BaseDaoWithQuery;

@Repository
public class RequestLogDao extends BaseDaoWithQuery<RequestLog, Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected RequestLogDao() {
		super(RequestLog.class);
	}
	
	/**
	 * 查询请求次数
	 * @param ip
	 * @param type
	 * @return
	 */
	public int getrequestcount(String ip,String type){
		int total=0;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from requestlog where createtime>=SUBDATE(now(),interval 2 minute)");
		sb.append(" and ip='"+ip+"'");
		sb.append(" and type= '"+type+"'");
		Object oTotal = em.createNativeQuery(sb.toString()).getSingleResult();
		if(null != oTotal){
			total =Integer.parseInt(oTotal.toString());
		}
		return total;
	}
	
}
