package com.website.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Map方式查询
 * @author liangli
 *
 * @param <T>
 */
public class MapQuery<T> {
	
	/** logger */
	private static final Logger _Log = LoggerFactory.getLogger(BaseDao.class);
	
	/** 数据类 */
	protected Class<T> mDataClass;

	/**
	 * 构造函数
	 * @param dataClass
	 */
	public MapQuery(Class<T> dataClass) {
		
		mDataClass = dataClass;
	}
	

	/**
	 * 取结果
	 * @param query
	 * @param mapper
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getResultList(Query query) throws DaoException {

		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> items = query.getResultList();
		if (null == items || items.size() < 1) return null;
	
		T data = null;
		ArrayList<T> datas = new ArrayList<T>(items.size());
		for (Map<String, Object> item : items) {
			
			try {
				
				data = mDataClass.newInstance();
			}
			catch (Exception e) {
				throw new DaoException(e);
			}

			try {
				BeanUtils.populate(data, item);
			} catch (Exception e) {
				_Log.warn("MapQuery.getResultList error: ", e);
			}
			datas.add(data);
		}
		return datas;
	}
}
