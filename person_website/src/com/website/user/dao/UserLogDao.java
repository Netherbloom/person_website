package com.website.user.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.website.core.dao.BaseDaoWithQuery;
import com.website.user.entity.UserLog;

@Repository
public class UserLogDao extends BaseDaoWithQuery<UserLog, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected UserLogDao() {
		super(UserLog.class);
	}

}
