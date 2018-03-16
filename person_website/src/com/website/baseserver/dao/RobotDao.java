package com.website.baseserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.website.baseserver.entity.Robot;
import com.website.core.dao.BaseDaoWithQuery;

@Repository
public class RobotDao  extends BaseDaoWithQuery<Robot, Integer>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected RobotDao() {
		super(Robot.class);
	}
	
}
