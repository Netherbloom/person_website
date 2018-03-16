package com.website.core.dao;

/**
 * 实体附加几个值
 * @author liangli
 *
 */
public class EntityAdded<T> {

	private T data;
	private Object[] addValues;
	
	@SuppressWarnings("unchecked")
	public EntityAdded(Object[] values) {

		this.setData((T) values[0]);
		addValues = new Object[values.length - 1];
		for (int i = 1; i < values.length; i++) addValues[i - 1] = values[i];
	}
	
	public EntityAdded(T t, Object value) {

		this.setData(t);
		addValues = new Object[]{value};
	}
	
	public EntityAdded(T t, Object value1, Object value2) {

		this.setData(t);
		addValues = new Object[]{value1, value2};
	}
	
	public EntityAdded(T t, Object value1, Object value2, Object value3) {

		this.setData(t);
		addValues = new Object[]{value1, value2, value3};
	}
	
	public EntityAdded(T t, Object value1, Object value2, Object value3, Object value4) {

		this.setData(t);
		addValues = new Object[]{value1, value2, value3, value4};
	}
	
	public EntityAdded(T t, Object value1, Object value2, Object value3, Object value4, Object value5) {

		this.setData(t);
		addValues = new Object[] { value1, value2, value3, value4, value5 };
	}

	public EntityAdded(T t, Object value1, Object value2, Object value3, Object value4, Object value5, Object value6) {

		this.setData(t);
		addValues = new Object[] { value1, value2, value3, value4, value5, value6 };
	}

	public EntityAdded(T t, Object value1, Object value2, Object value3, Object value4, Object value5, Object value6, Object value7) {

		this.setData(t);
		addValues = new Object[] { value1, value2, value3, value4, value5, value6, value7 };
	}

	public EntityAdded(T t, Object value1, Object value2, Object value3, Object value4, Object value5, Object value6, Object value7, Object value8) {

		this.setData(t);
		addValues = new Object[] { value1, value2, value3, value4, value5, value6, value7, value8 };
	}

	public T getData() {
		return data;
	}

	public Object[] getAddValues() {
		return addValues;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setAddValues(Object[] addValues) {
		this.addValues = addValues;
	}
	
	public Object getAddValue(int idx) {
		
		return this.addValues[idx];
	}
}
