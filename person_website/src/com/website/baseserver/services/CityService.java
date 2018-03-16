package com.website.baseserver.services;

import java.util.List;

import com.website.baseserver.entity.City;


public interface CityService {

	/**
	 * 新增城市
	 * @param city
	 */
	public void batchsave();
	
	/**
	 * 根据父级id获取城市
	 * @param parentid
	 * @return
	 */
	public List<City> getCitiesByParentId(String parentid,int status);
	
}
