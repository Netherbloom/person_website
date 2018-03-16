package com.website.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

/**
 * 查询条件
 * @author liangli
 */
public class QueryWhere {
	
	static final int OP_WHERE 		= 0x0001;
	static final int OP_EQ 			= 0x0002;
	static final int OP_LIKEAll 	= 0x0003;
	static final int OP_NOT_LIKEAll = 0x0004;
	static final int OP_IN 			= 0x0005;
	static final int OP_NOT_IN 		= 0x0006;
	static final int OP_LIKE 		= 0x0007;
	static final int OP_NOT_LIKE 	= 0x0008;
	static final int OP_CUSTOM 		= 0x0009;
	
	static final int OP_MASK 	= 0x0FFF;
	static final int OP_AND 	= 0x0000;
	static final int OP_OR 		= 0x1000;

	/** 属性 */
	private List<String> attributes;
	/** 值 */
	private List<Object> values;
	/** 运算符 */
	private List<Integer> operations;
	
	public QueryWhere() {
		
		this.attributes = new ArrayList<String>(8);
		this.values = new ArrayList<Object>(8);
		this.operations = new ArrayList<Integer>(8);
	}
	
	public QueryWhere(Map<String, Object> items) {
		
		this();
		this.and(items);
	}
	
	/**
	 * 是否有条件
	 * @return
	 */
	public boolean hasWhere() {

		return this.attributes.size() > 0;
	}
	
	/**
	 * and条件
	 * @param where
	 */
	public QueryWhere and(QueryWhere where) {

		if (null == where || !where.hasWhere()) return this;
		
		attributes.add("");
		values.add(where);
		operations.add(OP_WHERE);
		return this;
	}
	
	/**
	 * or条件
	 * @param where
	 */
	public QueryWhere or(QueryWhere where) {

		if (null == where || !where.hasWhere()) return this;
		
		attributes.add("");
		values.add(where);
		operations.add(OP_OR | OP_WHERE);
		return this;
	}
	
	/**
	 * and条件
	 * @param attribute
	 * @param value
	 */
	public QueryWhere and(String attribute, Object value) {

		if (null == attribute || attribute.length() < 1 || null == value) return this;
		
		attributes.add(attribute);
		values.add(value);
		operations.add(OP_EQ);
		return this;
	}
	
	/**
	 * or条件
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere or(String attribute, Object value) {

		if (null == attribute || attribute.length() < 1 || null == value) return this;
		
		attributes.add(attribute);
		values.add(value);
		operations.add(OP_OR | OP_EQ);
		return this;
	}
	
	/**
	 * and条件
	 * @param attribute
	 * @param value
	 */
	public QueryWhere and(String attribute, String op, Object value) {

		if (null == attribute || attribute.length() < 1 || null == value) return this;
		
		attributes.add(attribute + op);
		values.add(value);
		operations.add(OP_CUSTOM);
		return this;
	}
	
	/**
	 * or条件
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere or(String attribute, String op, Object value) {

		if (null == attribute || attribute.length() < 1 || null == value) return this;
		
		attributes.add(attribute + op);
		values.add(value);
		operations.add(OP_OR | OP_CUSTOM);
		return this;
	}
	
	/**
	 * and条件
	 * @param items
	 */
	public QueryWhere and(Map<String, Object> items) {

		if (null == items || items.size() < 1) return this;
		
		for (String key : items.keySet()) {

			if (null == items.get(key)) continue;
			attributes.add(key);
			values.add(items.get(key));
			operations.add(OP_EQ);
		}
		return this;
	}
	
	/**
	 * or条件
	 * @param items
	 */
	public QueryWhere or(Map<String, Object> items) {

		if (null == items || items.size() < 1) return this;
		
		for (String key : items.keySet()) {

			if (null == items.get(key)) continue;
			attributes.add(key);
			values.add(items.get(key));
			operations.add(OP_EQ | OP_OR);
		}
		return this;
	}
	
	/**
	 * and条件
	 * @param items
	 */
	public QueryWhere and(String[] attributes, Object[] values) {

		if (null == attributes || attributes.length < 1) return this;
		if (null == values || attributes.length !=  attributes.length) return this;
		
		int nCount = attributes.length;
		for (int i=0; i<nCount; i++) {
			
			if (null == values[i]) continue;
			this.attributes.add(attributes[i]);
			this.values.add(values[i]);
			operations.add(OP_EQ);
		}
		return this;
	}
	
	/**
	 * or条件
	 * @param items
	 */
	public QueryWhere or(String[] attributes, Object[] values) {

		if (null == attributes || attributes.length < 1) return this;
		if (null == values || attributes.length !=  attributes.length) return this;
		
		int nCount = attributes.length;
		for (int i=0; i<nCount; i++) {
			
			if (null == values[i]) continue;
			this.attributes.add(attributes[i]);
			this.values.add(values[i]);
			operations.add(OP_EQ | OP_OR);
		}
		return this;
	}
	
	/**
	 * and条件
	 * @param items
	 */
	public QueryWhere and(Collection<String> attributes, Collection<Object> values) {

		if (null == attributes || attributes.size() < 1) return this;
		if (null == values || attributes.size() !=  attributes.size()) return this;
		
		int nCount = attributes.size();
		this.attributes.addAll(attributes);
		this.values.addAll(values);
		for (int i=0; i<nCount; i++) this.operations.add(OP_EQ);
		return this;
	}
	
	/**
	 * or条件
	 * @param items
	 */
	public QueryWhere or(Collection<String> attributes, Collection<Object> values) {

		if (null == attributes || attributes.size() < 1) return this;
		if (null == values || attributes.size() !=  attributes.size()) return this;
		
		int nCount = attributes.size();
		this.attributes.addAll(attributes);
		this.values.addAll(values);
		for (int i=0; i<nCount; i++) this.operations.add(OP_EQ | OP_OR);
		return this;
	}
	
	/**
	 * like all条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere likeAll(boolean and, String attribute, String value) {

		if (null == attribute || attribute.length() < 1 || null == value || value.length() < 1)
			return this;
		
		attributes.add(attribute);
		values.add(value);
		if (and)
			operations.add(OP_LIKEAll);
		else
			operations.add(OP_LIKEAll | OP_OR);
		return this;
	}
	
	/**
	 * like all条件
	 * @param items
	 */
	public QueryWhere likeAll(String attribute, String value) {
		
		return this.likeAll(true, attribute, value);
	}
	
	/**
	 * and like all条件
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere andLikeAll(String attribute, String value) {

		return this.likeAll(true, attribute, value);
	}
	
	/**
	 * or like all条件
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere orLikeAll(String attribute, String value) {

		return likeAll(false, attribute, value);
	}
	
	/**
	 * not like all条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere notLikeAll(boolean and, String attribute, String value) {

		if (null == attribute || attribute.length() < 1 || null == value || value.length() < 1)
			return this;
		
		attributes.add(attribute);
		values.add(value);
		if (and)
			operations.add(OP_NOT_LIKEAll);
		else
			operations.add(OP_NOT_LIKEAll | OP_OR);
		return this;
	}
	public QueryWhere notLikeAll(String attribute, String value) {
		return this.notLikeAll(true, attribute, value);
	}
	public QueryWhere andNotLikeAll(String attribute, String value) {
		return this.notLikeAll(true, attribute, value);
	}
	public QueryWhere orNotLikeAll(String attribute, String value) {
		return this.notLikeAll(false, attribute, value);
	}
	
	/**
	 * like 条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere like(boolean and, String attribute, String value) {

		if (null == attribute || attribute.length() < 1 || null == value || value.length() < 1) return this;
		
		attributes.add(attribute);
		values.add(value);
		if (and)
			operations.add(OP_LIKE);
		else
			operations.add(OP_LIKE | OP_OR);
		return this;
	}
	
	/**
	 * not like 条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere notLike(boolean and, String attribute, String value) {

		if (null == attribute || attribute.length() < 1 || null == value || value.length() < 1) return this;
		
		attributes.add(attribute);
		values.add(value);
		if (and)
			operations.add(OP_NOT_LIKE);
		else
			operations.add(OP_NOT_LIKE | OP_OR);
		return this;
	}
	
	/**
	 * in 条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere in(boolean and, String attribute, Collection<? extends Object> values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.size() < 1) return this;
		
		attributes.add(attribute);
		this.values.add(values);
		if (and)
			operations.add(OP_IN);
		else
			operations.add(OP_IN | OP_OR);
		return this;
	}
	public QueryWhere andIn(String attribute, Collection<? extends Object> values) {
		return this.in(true, attribute, values);
	}
	public QueryWhere orIn(String attribute, Collection<? extends Object> values) {
		return this.in(false, attribute, values);
	}
	
	/**
	 * in 条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere in(boolean and, String attribute, Object... values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.length < 1) return this;
		
		ArrayList<Object> items = new ArrayList<Object>(values.length);
		for (Object item : values) {

			if (item.getClass().isArray()) {

				// 数组
				for (int v : (int[]) item)
					items.add(v);
			}
			else
				items.add(item);
		}
		this.in(and, attribute, items);
		return this;
	}
	public QueryWhere andIn(String attribute, Object... values) {
		return this.in(true, attribute, values);
	}
	public QueryWhere orIn(String attribute, Object... values) {
		return this.in(false, attribute, values);
	}
	public QueryWhere andIn(String attribute, int... values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.length < 1) return this;
		
		ArrayList<Integer> items = new ArrayList<Integer>(values.length);
		for (int item : values) items.add(item);
		this.in(true, attribute, items);
		return this;
	}
	public QueryWhere orIn(String attribute, int... values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.length < 1) return this;
		
		ArrayList<Integer> items = new ArrayList<Integer>(values.length);
		for (int item : values) items.add(item);
		this.in(false, attribute, items);
		return this;
	}
	
	/**
	 * not in 条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere notIn(boolean and, String attribute, Collection<? extends Object> values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.size() < 1) return this;
		
		attributes.add(attribute);
		this.values.add(values);
		if (and)
			operations.add(OP_NOT_IN);
		else
			operations.add(OP_NOT_IN | OP_OR);
		return this;
	}
	public QueryWhere andNotIn(String attribute, Collection<? extends Object> values) {
		return this.notIn(true, attribute, values);
	}
	public QueryWhere orNotIn(String attribute, Collection<? extends Object> values) {
		return this.notIn(false, attribute, values);
	}
	
	/**
	 * not in 条件
	 * @param and
	 * @param attribute
	 * @param value
	 * @return
	 */
	public QueryWhere notIn(boolean and, String attribute, Object... values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.length < 1) return this;
		
		ArrayList<Object> items = new ArrayList<Object>(values.length);
		for (Object item : values) {
			
			if (item.getClass().isArray()) {

				// 数组
				for (int v : (int[]) item)
					items.add(v);
			}
			else
				items.add(item);
		}
		this.notIn(and, attribute, items);
		return this;
	}
	public QueryWhere andNotIn(String attribute, Object... values) {
		return this.notIn(true, attribute, values);
	}
	public QueryWhere orNotIn(String attribute, Object... values) {
		return this.notIn(false, attribute, values);
	}
	public QueryWhere andNotIn(String attribute, Number... values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.length < 1) return this;
		
		ArrayList<Number> items = new ArrayList<Number>(values.length);
		for (Number item : values) items.add(item);
		this.notIn(true, attribute, items);
		return this;
	}
	public QueryWhere orNotIn(String attribute, Number... values) {

		if (null == attribute || attribute.length() < 1 || null == values || values.length < 1) return this;
		
		ArrayList<Number> items = new ArrayList<Number>(values.length);
		for (Number item : values) items.add(item);
		this.notIn(false, attribute, items);
		return this;
	}
	
	/**
	 * 输出QL
	 * @param alias 表别名
	 * @return
	 */
	public String toQL(String alias) {
		
		return this.toQL(alias, 1);
	}
	
	/**
	 * 输出QL
	 * @param alias 表别名
	 * @param idx
	 * @return
	 */
	public String toQL(String alias, int idx) {
		
		StringBuilder sb = new StringBuilder(128);
		toQL(sb, alias, idx);
		return sb.toString();
	}
	
	/**
	 * 输出QL
	 * @param alias 表别名
	 * @param idx
	 * @return
	 */
	public int toQL(StringBuilder ql, String alias, int idx) {
		
		int nCount = this.attributes.size();
		if (nCount < 1) return 0;
		
		int nOp;
		int nIdx = idx;
		for (int i = 0; i < nCount; i++) {
			
			nOp = this.operations.get(i);
			if (i > 0) {
				
				if (OP_OR == (OP_OR&nOp))
					ql.append(" or ");
				else
					ql.append(" and ");
			}
			else
				ql.append(' ');
			nOp = (nOp & OP_MASK);
			if (null != alias && OP_WHERE != nOp) ql.append(alias).append('.');
			if (OP_EQ == nOp) {
				
				ql.append(attributes.get(i)).append("=?" + (nIdx));
				nIdx++;
			}
			else if (OP_LIKEAll == nOp || OP_LIKE == nOp) {
				
				ql.append(attributes.get(i)).append(" like ?" + nIdx);
				nIdx++;
			}
			else if (OP_NOT_LIKEAll == nOp || OP_NOT_LIKE == nOp) {
				
				ql.append(attributes.get(i)).append(" not like ?" + nIdx);
				nIdx++;
			}
			else if (OP_IN == nOp || OP_NOT_IN == nOp) {
				
				if (OP_IN == nOp)
					ql.append(attributes.get(i)).append(" in(?" + nIdx);
				else
					ql.append(attributes.get(i)).append(" not in(?" + nIdx);
				ql.append(')');
				nIdx++;
			}
			else if (OP_WHERE == nOp) {
				
				QueryWhere w = (QueryWhere) this.values.get(i);
				ql.append('(');
				nIdx += w.toQL(ql, alias, nIdx);
				ql.append(')');
			}
			else if (OP_CUSTOM == nOp) {
				
				ql.append(attributes.get(i)).append("?" + nIdx);
				nIdx++;
			}
		}
		return nIdx - idx;
	}
	
	/**
	 * 输出参数
	 * @param query
	 * @return
	 */
	public int toParameters(Query query) {
		
		return this.toParameters(1, query);
	}
	
	/**
	 * 输出参数
	 * @param idx
	 * @param query
	 * @return
	 */
	public int toParameters(int idx, Query query) {
		
		int nCount = this.values.size();
		if (nCount < 1) return 0;
		
		int nOp;
		int nIdx = idx;
		for (int i=0; i<nCount; i++) {
			
			nOp = this.operations.get(i);
			nOp = (nOp & OP_MASK);
			if (OP_EQ == nOp) {
				
				query.setParameter(nIdx, this.values.get(i));
				nIdx ++;
			}
			else if (OP_LIKEAll == nOp) {
			
				query.setParameter(nIdx, String.format("%%%s%%", this.values.get(i)));
				nIdx ++;
			}
			else if (OP_WHERE == nOp) {
				
				QueryWhere w = (QueryWhere) this.values.get(i);
				nIdx += w.toParameters(nIdx, query);
			}
			else {
				
				query.setParameter(nIdx, this.values.get(i));
				nIdx ++;
			}
		}
		return nIdx - idx;
	}
}
