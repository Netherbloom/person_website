package com.website.core.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;

import com.website.core.page.Page;
import com.website.core.page.Pageable;

/**
 * 实体附加几个字段查询
 * @author liangli
 *
 * @param <T>
 */
public class QueryAdded<T> {
	
	/**
	 * Mapper 定义
	 * @param <T>
	 */
	public static interface Mapper<T> {
		
		public T map(EntityAdded<T> data);
	}
	
	/**
	 * 默认Mapper
	 * @param <T>
	 */
	public static class DefaultMapper<T> implements Mapper<T> {
		
		String[] mProps = null;
		public DefaultMapper(String... names) {
			
			mProps = names;
		}

		public T map(EntityAdded<T> data) {
			
			T entity = data.getData();
			PropertyDescriptor pd;
			Method m;
			for (int i=0; i<mProps.length; i++) {
				
				pd = BeanUtils.getPropertyDescriptor(entity.getClass(), mProps[i]);
				m = pd.getWriteMethod();
				try {
					m.invoke(entity, data.getAddValue(i));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return entity;
		}
	}
	
	private Query mQuery;
	private Pageable mPageable;

	public QueryAdded() {}

	public QueryAdded(EntityManager em, String jpql) {
		
		mQuery = this.createQuery(em, jpql);
	}

	public QueryAdded(EntityManager em, String jpql, Pageable pageable) {
		
		mPageable = pageable;
		mQuery = this.createQuery(em, jpql, null);
	}

	public QueryAdded(EntityManager em, String jpql, QueryWhere where, Pageable pageable) {

		mPageable = pageable;
		mQuery = this.createQuery(em, jpql, where);
	}
	
	/**
	 * 创建查询
	 * @param em
	 * @param jpql
	 * @return
	 */
	public Query createQuery(EntityManager em, String jpql) {
		
		return this.createQuery(em, jpql, null);
	}
	
	/**
	 * 创建查询
	 * @param em
	 * @param jpql QL
	 * @param where 条件
	 * @return
	 */
	public Query createQuery(EntityManager em, String jpql, QueryWhere where) {
		
		// 注入ql
		String str = jpql.toLowerCase();
		int nIdx1 = str.indexOf("select ");
		int nIdx2 = str.indexOf(" from ");
		if (nIdx1 >=0 && nIdx2 > nIdx1 && str.indexOf("select new ") < 0) {
			
			StringBuilder sb = new StringBuilder(jpql.length() + 64);
			sb.append(jpql.substring(0, nIdx1 + 7));
			sb.append("new ").append(EntityAdded.class.getName()).append('(');
			sb.append(jpql.substring(nIdx1 + 7, nIdx2)).append(')');
			sb.append(jpql.substring(nIdx2));
			if (null != where) {
				
				// 有条件
				where.toQL(sb, null, 1);
				// 排序
				if (null != mPageable && null != mPageable.getSort())
					sb.append(mPageable.getSort().toQL("d"));
				Query q = em.createQuery(sb.toString());
				where.toParameters(q);
				return q;
			}
			else
				jpql = sb.toString();				
		}
		else if (null != where) {

			// 有条件
			jpql += where.toQL(null, 1);
			// 排序
			if (null != mPageable && null != mPageable.getSort())
				jpql += mPageable.getSort().toQL("d");
			Query q = em.createQuery(jpql);
			where.toParameters(q);
			return q;
		}

		// 排序
		if (null != mPageable && null != mPageable.getSort())
			jpql += mPageable.getSort().toQL("d");
		
		return em.createQuery(jpql);
	}

	/**
	 * 取结果
	 * @param query
	 * @param mapper
	 * @return
	 */
	public List<T> getResultList(Query query, Mapper<T> mapper) {

		return this.getDatas(query.getResultList(), mapper);
	}
	
	/**
	 * 取结果
	 * @param query
	 * @param mapper
	 * @return
	 */
	public List<T> getResultList(Query query, String... adds) {

		return this.getDatas(query.getResultList(), new DefaultMapper<T>(adds));
	}
	
	/**
	 * 取结果
	 * @param query
	 * @param mapper
	 * @return
	 */
	public List<T> getResultList(String... adds) {

		return this.getDatas(mQuery.getResultList(), new DefaultMapper<T>(adds));
	}
	
	/**
	 * 取分页结果
	 * @param nTotal 总记录数
	 * @param adds 附加字段
	 * @return
	 */
	public Page<T> getPage(int total, String... adds) {

		mQuery.setMaxResults(mPageable.getPageSize());
		mQuery.setFirstResult(mPageable.getOffset());
		return new Page<T>(getResultList(adds), mPageable, total);
	}
	
	/**
	 * 取分页结果
	 * @param query 查询
	 * @param pageable 分页
	 * @param total 总记录数
	 * @param adds 附加字段
	 * @return
	 */
	public Page<T> getPage(Query query, Pageable pageable, int total, String... adds) {

		query.setMaxResults(pageable.getPageSize());
		query.setFirstResult(pageable.getOffset());
		return new Page<T>(getResultList(query, adds), pageable, total);
	}
	
	/**
	 * 转换数据
	 * @param items
	 * @param mapper
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<T> getDatas(List<?> items, Mapper<T> mapper) {
		
		ArrayList<T> datas = new ArrayList<T>(items.size());
		for (Object item : items) {
			
			if (item instanceof EntityAdded)
				datas.add(mapper.map((EntityAdded<T>)item));
			else
				datas.add(mapper.map(new EntityAdded<T>((Object[])item)));
		}
		return datas;
	}
}
