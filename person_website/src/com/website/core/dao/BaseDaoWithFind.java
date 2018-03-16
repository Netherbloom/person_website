package com.website.core.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.website.core.page.Page;
import com.website.core.page.Pageable;

/**
 * 带查询功能的Dao基类
 * @author liangli
 *
 */
public abstract class BaseDaoWithFind<T, ID> extends BaseDao<T, ID> {

	protected BaseDaoWithFind(Class<T> entityClass) {
		super(entityClass);
	}
	
	/**
	 * 匹配查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public List<T> findBy(String attribute, Object value) {

		if (null == attribute || attribute.length() < 1 || null == value) return null;
			
		StringBuilder sb = new StringBuilder(128);
		sb.append("select d from ").append(mEntityClass.getSimpleName());
		sb.append(" d where d.");
		sb.append(attribute).append("=?1");
		TypedQuery<T> query = getEntityManager().createQuery(sb.toString(), mEntityClass);
		query.setParameter(1, value);
		return defaultSort(query.getResultList());
	}
	
	/**
	 * 分页匹配查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findBy(String attribute, Object value, Pageable pageable) {
		
		QueryWhere where = new QueryWhere();
		where.and(attribute, value);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * 匹配查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public List<T> findBy(String[] attributes, Object[] values) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		return this.findByWhere(where);
	}
	
	/**
	 * 匹配查找
	 * @param by
	 * @return
	 */
	public List<T> findBy(Map<String, Object> by) {

		QueryWhere where = new QueryWhere(by);
		return this.findByWhere(where);
	}
	
	/**
	 * 分页匹配查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findBy(String[] attributes, Object[] values, Pageable pageable) {
		
		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * 分页匹配查找
	 * @param by
	 * @param pageable
	 * @return
	 */
	public Page<T> findBy(Map<String, Object> by, Pageable pageable) {

		QueryWhere where = new QueryWhere(by);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * like查找
	 * @param attribute
	 * @param value
	 * @return
	 */
	public List<T> findLike(String attribute, String value) {
		
		QueryWhere where = new QueryWhere();
		where.likeAll(attribute, value);
		return this.findByWhere(where);
	}
	
	/**
	 * like分页查找
	 * @param attribute
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findLike(String attribute, String value, Pageable pageable) {

		QueryWhere where = new QueryWhere();
		where.likeAll(attribute, value);
		return this.findByWhere(where, pageable);
	}

	/**
	 * 匹配并like查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public List<T> findByAndLike(String[] attributes, Object[] values,
			String likeAttribute, String likeValue) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.likeAll(likeAttribute, likeValue);
		return this.findByWhere(where);
	}

	/**
	 * 匹配并like查找
	 * @param by
	 * @param likeAttribute
	 * @param likeValue
	 * @return
	 */
	public List<T> findByAndLike(Map<String, Object> by, String likeAttribute,
			String likeValue) {

		QueryWhere where = new QueryWhere(by);
		where.likeAll(likeAttribute, likeValue);
		return this.findByWhere(where);
	}
	
	/**
	 * 匹配并like查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findByAndLike(String[] attributes, Object[] values,
			String likeAttribute, String likeValue, Pageable pageable) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.likeAll(likeAttribute, likeValue);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * 匹配并like查找
	 * @param by
	 * @param likeAttribute
	 * @param likeValue
	 * @param pageable
	 * @return
	 */
	public Page<T> findByAndLike(Map<String, Object> by, String likeAttribute,
			String likeValue, Pageable pageable) {

		QueryWhere where = new QueryWhere(by);
		where.likeAll(likeAttribute, likeValue);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * not like查找
	 * @param attribute
	 * @param value
	 * @return
	 */
	public List<T> findNotLike(String attribute, String value) {
		
		QueryWhere where = new QueryWhere();
		where.andNotLikeAll(attribute, value);
		return this.findByWhere(where);
	}
	
	/**
	 * not like分页查找
	 * @param attribute
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findNotLike(String attribute, String value, Pageable pageable) {

		QueryWhere where = new QueryWhere();
		where.andNotLikeAll(attribute, value);
		return this.findByWhere(where, pageable);
	}

	/**
	 * 匹配并not like查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public List<T> findByAndNotLike(String[] attributes, Object[] values,
			String likeAttribute, String likeValue) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andNotLikeAll(likeAttribute, likeValue);
		return this.findByWhere(where);
	}

	/**
	 * 匹配并like查找
	 * @param by
	 * @param likeAttribute
	 * @param likeValue
	 * @return
	 */
	public List<T> findByAndNotLike(Map<String, Object> by, String likeAttribute,
			String likeValue) {

		QueryWhere where = new QueryWhere(by);
		where.andNotLikeAll(likeAttribute, likeValue);
		return this.findByWhere(where);
	}
	
	/**
	 * 匹配并like查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findByAndNotLike(String[] attributes, Object[] values,
			String likeAttribute, String likeValue, Pageable pageable) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andNotLikeAll(likeAttribute, likeValue);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * 匹配并like查找
	 * @param by
	 * @param likeAttribute
	 * @param likeValue
	 * @param pageable
	 * @return
	 */
	public Page<T> findByAndNotLike(Map<String, Object> by, String likeAttribute,
			String likeValue, Pageable pageable) {

		QueryWhere where = new QueryWhere(by);
		where.andNotLikeAll(likeAttribute, likeValue);
		return this.findByWhere(where, pageable);
	}
	
	/**
	 * 查询
	 * @param where
	 * @return
	 */
	public List<T> findByWhere(QueryWhere where) {

		// 无条件，查询所有
		if (null == where || !where.hasWhere()) return findAll();
			
		//JPQL
		StringBuilder sb = new StringBuilder(128);
		sb.append("select d from ").append(mEntityClass.getSimpleName());
		sb.append(" d where").append(where.toQL("d"));
		
		TypedQuery<T> query = getEntityManager().createQuery(sb.toString(), mEntityClass);
		where.toParameters(query);
		return defaultSort(query.getResultList());
	}
	
	/**
	 * 匹配并like查找
	 * @param name
	 * @param value
	 * @param pageable
	 * @return
	 */
	public Page<T> findByWhere(QueryWhere where, Pageable pageable) {
		
		// 无条件，查询所有
		if (null == where || !where.hasWhere()) return findAll(pageable);
		
		EntityManager em = this.getEntityManager();
		
		// JPQL
		int nTotal = 0;
		StringBuilder sb = new StringBuilder(128);
		sb.append(" from ").append(mEntityClass.getSimpleName());
		sb.append(" d where").append(where.toQL("d"));
		
		// 先取记录数
		TypedQuery<Long> countQuery = em.createQuery("select count(d)" + sb, Long.class);
		where.toParameters(countQuery);
		nTotal = countQuery.getSingleResult().intValue();
		if (nTotal < 1) return new Page<T>(null, pageable, nTotal);
			
		// 后查询
		if (null != pageable.getSort()) sb.append(pageable.getSort().toQL()); // 排序
		TypedQuery<T> query = em.createQuery("select d" + sb.toString(), mEntityClass);
		where.toParameters(query);
		return pageQuery(query, nTotal, pageable);
	}
	
	/**
	 * in查询
	 * @param attribute
	 * @param values
	 * @return
	 */
	public List<T> findIn(String attribute, Object... values) {

		QueryWhere where = new QueryWhere();
		where.andIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findIn(String attribute, int... values) {

		QueryWhere where = new QueryWhere();
		where.andIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findIn(String attribute, Collection<? extends Object> values) {

		QueryWhere where = new QueryWhere();
		where.andIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndIn(Map<String, Object> by, String attribute, Object... values) {

		QueryWhere where = new QueryWhere(by);
		where.andIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndIn(Map<String, Object> by, String attribute, int... values) {

		QueryWhere where = new QueryWhere(by);
		where.andIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndIn(Map<String, Object> by, String attribute, Collection<? extends Object> values) {

		QueryWhere where = new QueryWhere(by);
		where.andIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndIn(String[] attributes, Object[] values, String inAttribute, Object... inValues) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andIn(inAttribute, inValues);
		return this.findByWhere(where);
	}
	public List<T> findByAndIn(String[] attributes, Object[] values, String inAttribute, int... inValues) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andIn(inAttribute, inValues);
		return this.findByWhere(where);
	}
	public List<T> findByAndIn(String[] attributes, Object[] values, String inAttribute, Collection<? extends Object> inValues) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andIn(inAttribute, inValues);
		return this.findByWhere(where);
	}
	
	/**
	 * not in查询
	 * @param attribute
	 * @param values
	 * @return
	 */
	public List<T> findNotIn(String attribute, Object... values) {

		QueryWhere where = new QueryWhere();
		where.andNotIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findNotIn(String attribute, int... values) {

		QueryWhere where = new QueryWhere();
		where.andNotIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findNotIn(String attribute, Collection<? extends Object> values) {

		QueryWhere where = new QueryWhere();
		where.andNotIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndNotIn(Map<String, Object> by, String attribute, Object... values) {

		QueryWhere where = new QueryWhere(by);
		where.andNotIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndNotIn(Map<String, Object> by, String attribute, int... values) {

		QueryWhere where = new QueryWhere(by);
		where.andNotIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndNotIn(Map<String, Object> by, String attribute, Collection<? extends Object> values) {

		QueryWhere where = new QueryWhere(by);
		where.andNotIn(attribute, values);
		return this.findByWhere(where);
	}
	public List<T> findByAndNotIn(String[] attributes, Object[] values, String inAttribute, Object... inValues) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andNotIn(inAttribute, inValues);
		return this.findByWhere(where);
	}
	public List<T> findByAndNotIn(String[] attributes, Object[] values, String inAttribute, int... inValues) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andNotIn(inAttribute, inValues);
		return this.findByWhere(where);
	}
	public List<T> findByAndNotIn(String[] attributes, Object[] values, String inAttribute, Collection<? extends Object> inValues) {

		QueryWhere where = new QueryWhere();
		where.and(attributes, values);
		where.andNotIn(inAttribute, inValues);
		return this.findByWhere(where);
	}
}
