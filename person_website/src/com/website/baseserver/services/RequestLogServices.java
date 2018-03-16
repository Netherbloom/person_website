package com.website.baseserver.services;

import com.website.baseserver.entity.RequestLog;

public interface RequestLogServices {

	public void save (RequestLog log);
	
	/**
	 * 查询次数
	 * @param ip
	 * @param type
	 * @return
	 */
	public int getcount(String ip,String type);
	
}
