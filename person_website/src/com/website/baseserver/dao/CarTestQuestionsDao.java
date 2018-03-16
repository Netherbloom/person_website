package com.website.baseserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.website.baseserver.entity.CarTestQuestions;
import com.website.core.dao.BaseDaoWithQuery;

/**
 * 驾照试题dao
 * @author Administrator
 *
 */
@Repository
public class CarTestQuestionsDao  extends BaseDaoWithQuery<CarTestQuestions, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected CarTestQuestionsDao() {
		super(CarTestQuestions.class);
	}
}
