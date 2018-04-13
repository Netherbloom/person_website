package com.website.admin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.website.admin.entity.Menu;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;

/**
 * 菜单
 * @author Administrator
 *
 */
@Repository
public class MenuDao extends BaseDaoWithQuery<Menu, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected MenuDao() {
		super(Menu.class);
	}
	
	/**
	 * 获取菜单
	 * @param parentid
	 * @param level
	 * @return
	 */
	public List<Menu> getmenu(String parentid,int level){
		StringBuilder sb=new StringBuilder();
		if(!StringUtils.isBlank(parentid)){
			sb.append(" and parentid='"+parentid+"'");
		}
		sb.append(" and level="+level)
		.append(" order by sort ");
		String sql="select * from menu where 1=1"+sb.toString();
		Query query=em.createNativeQuery(sql);
		MapQuery<Menu> mq = new MapQuery<Menu>(Menu.class);
		List<Menu> list=mq.getResultList(query);
		return list;
	}
}
