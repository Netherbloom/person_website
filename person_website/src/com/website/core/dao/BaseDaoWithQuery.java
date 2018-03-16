package com.website.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.website.core.page.Page;
import com.website.core.page.Pageable;

/**
 * 带高级查询功能的Dao基类
 * @author liangli
 *
 */
public abstract class BaseDaoWithQuery<T, ID> extends BaseDaoWithFind<T, ID> {

	protected BaseDaoWithQuery(Class<T> entityClass) {
		super(entityClass);
	}
	
	/**
	 * 高级查询
	 * @param where 条件
	 * @param values 值
	 * @param pageable
	 * @return
	 */
	protected Page<T> queryBy(String where, Object[] values, Pageable pageable) {

		EntityManager em = getEntityManager();
		if (null == where || null == values || values.length == 0) {
			
			// 所有
			return findAll(pageable);
		}
			
		int nTotal = 0;
		StringBuilder sb = new StringBuilder(128);
		sb.append(" from ").append(mEntityClass.getSimpleName());
		sb.append(" d where ").append(where);
		
		// 先取记录数
		TypedQuery<Long> queryCount = em.createQuery("select count(d)" + sb.toString(), Long.class);
		for (int i=0; i<values.length; i++) queryCount.setParameter(1+i, values[i]);
		nTotal = queryCount.getSingleResult().intValue();
		if (nTotal < 1) return new Page<T>(null, pageable, nTotal); // 没有数据
		
		// 再查询
		if (null != pageable.getSort()) sb.append(pageable.getSort().toQL()); // 排序
		TypedQuery<T> query = getEntityManager().createQuery("select d" + sb, mEntityClass);
		for (int i=0; i<values.length; i++) query.setParameter(1+i, values[i]);
		
		return pageQuery(query, nTotal, pageable);
	}
	
	/**
	 * 高级查询-列表
	 * @param where 条件
	 * @param values 值
	 * @return
	 */
	protected List<T> queryBy(String where, Object[] values) {

		if (null == where || null == values || values.length == 0) {
			
			// 所有
			return findAll();
		}
			
		StringBuilder sb = new StringBuilder(128);
		sb.append("select d from ").append(mEntityClass.getSimpleName());
		sb.append(" d where ").append(where);

		TypedQuery<T> query=getEntityManager().createQuery(sb.toString(), mEntityClass);
		for (int i=0; i<values.length; i++) query.setParameter(1+i, values[i]);
		return defaultSort(query.getResultList());
	}
	
	/**
	 * 完全自定义高级查询-列表
	 * @param tsql语句
	 * @return
	 */
	public List<T> queryBy(String sql) {

		if (null == sql) {
			
			// 所有
			return findAll();
		}
			
		TypedQuery<T> query=getEntityManager().createQuery(sql, mEntityClass);
		return query.getResultList();
	}	
	


}
