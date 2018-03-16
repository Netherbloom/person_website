package com.website.core.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.website.core.annotation.SortAnnotation;
import com.website.core.page.Page;
import com.website.core.page.Pageable;
import com.website.core.page.Sort;
import com.website.core.page.SortComparator;

/**
 * Dao基类
 * @author liangli
 * @param <T, ID>
 */
public abstract class BaseDao<T, ID> {
	
	/** logger */
	private static final Logger _Log = LoggerFactory.getLogger(BaseDao.class);

	/** 别名 */
	public static final String ALIAS = "d";
	
	/** 查询：所有 */
	protected static final String QUERY_ALL = "select d from %s d";
	/** 查询：存在 */
	protected static final String QUERY_EXISTS = "select count(d) from %s d where d.%s = :id"; 
	/** 查询：getBy */
	protected static final String QUERY_GET_BY = "select d from %s d where d.%s = ?1";
	/** 查询：findLike */
	protected static final String QUERY_FIND_LIKE = "select d from %s d where d.%s like :like";
	/** 查询：findLike记录数 */
	protected static final String QUERY_COUNT_LIKE = "select count(d) from %s d where d.%s like :like";
	/** 查询：所有记录数 */
	protected static final String QUERY_COUNT_ALL = "select count(d) from %s d";
    
	/** 实体类 */
	protected Class<T> mEntityClass;
	
	/**
	 * 取EntityManager
	 * @return
	 */
	protected abstract EntityManager getEntityManager();
	
	/**
	 * 构造
	 * @param entityClass
	 */
	@SuppressWarnings("unchecked")
	protected BaseDao(Class<T> entityClass) {

		mEntityClass = entityClass;
		if (null == entityClass) {
			
			_Log.warn("Dao \"{}\" no entity class.", getClass().getName());
			Type t = getClass().getGenericSuperclass();
			Type[] params = ((ParameterizedType) t).getActualTypeArguments();
			mEntityClass = (Class<T>) params[0];
		}
	}

	/**
	 * 根据id取实体
	 * @param id
	 * @return
	 */
	public T getById(ID id) {
		
		return getEntityManager().find(mEntityClass, id);
	}
	
	/**
	 * 保存
	 * @param entity
	 */
	public void save(T entity) {
		getEntityManager().persist(entity);
	}
	
	/**
	 * 批量保存
	 * @param entities
	 */
	@Transactional
	public void save(Iterable<? extends T> entities) {

		EntityManager em = getEntityManager();
        for (T entity : entities) {
        	
        	em.persist(entity);
        }  
	}
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	public T update(T entity) {

		return getEntityManager().merge(entity);
	}
	
	/**
	 * 批量更新
	 * @param entities
	 */
	public void update(Iterable<? extends T> entities) {

		EntityManager em = getEntityManager();
        for (T entity : entities) {
        	
        	em.merge(entity);
        }  
	}
	
	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(T entity) {

		EntityManager em = getEntityManager();
		//if (em.contains(entity))
		em.remove(em.merge(entity));
	}
	
	/**
	 * 批量删除
	 * @param entities
	 */
	@Transactional
	public void delete(Iterable<? extends T> entities) {  
  
		EntityManager em = getEntityManager();
        for (T entity : entities) {
        	
        	//if (em.contains(entity))
        	em.remove(em.merge(entity));
        }  
    } 
	
	/**
	 * 判断实例是否存在。
	 * @param id
	 * @return
	 */
	public boolean exists(ID id) {
		
		EntityManager em = getEntityManager();
		String str = String.format(QUERY_EXISTS, mEntityClass.getSimpleName(), getIdAttribute(em, mEntityClass));
		TypedQuery<Long> q = em.createQuery(str, Long.class);
		q.setParameter("id",id);
		return q.getSingleResult() > 0;
	}
	
	/**
	 * 取指定属性=值的一个数据
	 * @param id
	 * @param attribute
	 * @param value
	 * @return
	 */
	public T getBy(String attribute, Object value) {

		String strQuery = String.format(QUERY_GET_BY, mEntityClass.getSimpleName(), attribute);
		TypedQuery<T> query = getEntityManager().createQuery(strQuery, mEntityClass);
		query.setParameter(1, value);
		List<T> datas = query.getResultList();
		if (null != datas && datas.size() > 0)
			return datas.get(0);
		else
			return null;
	}

	/**
	 * 取指定条件实体
	 * 
	 * @param where
	 * @return
	 */
	public T getByWhere(QueryWhere where) {

		// JPQL
		StringBuilder sb = new StringBuilder(128);
		sb.append("select d from ").append(mEntityClass.getSimpleName());
		sb.append(" d where").append(where.toQL("d"));
		TypedQuery<T> query = getEntityManager().createQuery(sb.toString(), mEntityClass);
		where.toParameters(query);
		List<T> datas = query.getResultList();
		if (null != datas && datas.size() > 0)
			return datas.get(0);
		else
			return null;
	}
	
	/**
	 * 查询指定条件实体数
	 * @param where
	 * @return
	 */
	public int getCount(QueryWhere where) {

		// JPQL
		StringBuilder sb = new StringBuilder(128);
		sb.append("select count(d) from ").append(mEntityClass.getSimpleName());
		sb.append(" d where").append(where.toQL("d"));
		TypedQuery<Long> query = getEntityManager().createQuery(sb.toString(), Long.class);
		where.toParameters(query);
		return query.getSingleResult().intValue();
	}
	
	/**
	 * 查找所有
	 * @return
	 */
	public List<T> findAll() {

		String strQuery =  String.format(QUERY_ALL, mEntityClass.getSimpleName());
		return defaultSort(getEntityManager().createQuery(strQuery, mEntityClass).getResultList());
	}
	
	/**
	 * 查找所有指定
	 * @param ids
	 * @return
	 */
	public List<T> findAll(Iterable<ID> ids) {

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(mEntityClass);
		Root<T> root = query.from(mEntityClass);
		Path<?> path = root.get(getIdAttribute(em, mEntityClass));
		query.where(path.in(cb.parameter(List.class, "ids")));
		return defaultSort(em.createQuery(query).setParameter("ids", ids).getResultList());
    }
	
	/**
	 * 分页所有
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Pageable pageable) {
		
		EntityManager em = getEntityManager();
			
		// 先取记录数
		String strQuery = String.format(QUERY_COUNT_ALL, mEntityClass.getSimpleName());
		int nTotal = em.createQuery(strQuery, Long.class).getSingleResult().intValue();
		
		// 再查询
		strQuery = String.format(QUERY_ALL, mEntityClass.getSimpleName());
		if (null != pageable.getSort()) strQuery += pageable.getSort().toQL(); // 排序
		TypedQuery<T> query = em.createQuery(strQuery, mEntityClass);
		return pageQuery(query, nTotal, pageable);
	}

	/**
	 * 缺省排序
	 * 
	 * @param items
	 */
	public List<T> defaultSort(List<T> items) {

		SortAnnotation an = (SortAnnotation) mEntityClass.getAnnotation(SortAnnotation.class);
		if (null != an && null != an.value()) {

			// 有设置排序
			Sort sort = Sort.parese(an.value());
			if (null != sort) {

				// 排序
				Collections.sort(items, new SortComparator<T>(sort));
			}
		}
		return items;
	}
	
	/**
	 * 分页查询
	 * @param query
	 * @param pageable
	 * @return
	 */
	protected Page<T> pageQuery(TypedQuery<T> query, int total, Pageable pageable) {
		query.setMaxResults(pageable.getPageSize());
		query.setFirstResult(pageable.getOffset());
		return new Page<T>(query.getResultList(), pageable, total);
	}
	
	/**
	 * 取实体ID属性名（单ID）
	 * @param em
	 * @param clazz
	 * @return
	 */
	private String getIdAttribute(EntityManager em, Class<T> clazz) {
		
		ManagedType<T> mt = em.getMetamodel().managedType(clazz);
		if (mt instanceof IdentifiableType) {
			
			IdentifiableType<T> it = (IdentifiableType<T>) mt;
			if (it.hasSingleIdAttribute()) {
				
				SingularAttribute<? super T, ?> attr = it.getId(it.getIdType().getJavaType());
				if (null != attr) return attr.getName();
			}
		}

		_Log.error("Entity {} no id.", clazz.getName());
		return null;
	}
	
	/**
	 * 根据jpql取字符
	 * @param sql
	 * @return
	 */
	public String strSelect(String jpql) {
		
		EntityManager em = getEntityManager();
		TypedQuery<String> q = em.createQuery(jpql, String.class);
		String ret=q.getSingleResult();
		if(ret==null || ret.length()==0) ret="";
		return ret;
	}
	
	/**
	 * 根据jpql取字符
	 * @param jpql
	 * @param values 参数值
	 * @return
	 */
	public String strSelect(String jpql, Object... values) {
		
		EntityManager em = getEntityManager();
		TypedQuery<String> q = em.createQuery(jpql, String.class);
		if (null != values && values.length > 0) {

			for (int i = 0; i < values.length; i++)
				q.setParameter(1 + i, values[i]);
		}
		String ret = q.getSingleResult();
		if (ret == null || ret.length() == 0) ret = "";
		return ret;
	}
	
	/**
	 * 根据jpql取浮点
	 * @param sql
	 * @return
	 */
	public Double doubleSelect(String jpql) {
		
		EntityManager em = getEntityManager();
		TypedQuery<Double> q = em.createQuery(jpql, Double.class);
		Double val = q.getSingleResult();
		if (val != null)
			return val.doubleValue();
		else
			return 0.0;
	}
	
	/**
	 * 根据jpql取浮点
	 * @param sql
	 * @return
	 */
	public Double doubleSelect(String jpql, Object...values) {
		
		EntityManager em = getEntityManager();
		TypedQuery<Double> q = em.createQuery(jpql, Double.class);
		if (null != values && values.length > 0) {

			for (int i = 0; i < values.length; i++)
				q.setParameter(1 + i, values[i]);
		}
		Double val = q.getSingleResult();
		if (val != null)
			return val.doubleValue();
		else
			return 0.0;
	}
	
	/**
	 * 根据jpql取整型
	 * @param sql
	 * @return
	 */
	public int intSelect(String jpql) {
		
		EntityManager em = getEntityManager();
		TypedQuery<Long> q = em.createQuery(jpql, Long.class);
		Long val = q.getSingleResult();
		if (val != null)
			return val.intValue();
		else
			return 0;
	}
	
	/**
	 * 根据jpql取整型
	 * @param jpql
	 * @param values 参数值
	 * @return
	 */
	public int intSelect(String jpql, Object... values) {
		
		EntityManager em = getEntityManager();
		TypedQuery<Long> q = em.createQuery(jpql, Long.class);
		if (null != values && values.length > 0) {

			for (int i = 0; i < values.length; i++)
				q.setParameter(1 + i, values[i]);
		}
		Long val = q.getSingleResult();
		if (val != null)
			return val.intValue();
		else
			return 0;
	}
	
}
