package com.website.baseserver.services;

import java.util.Map;


/**
 * 天气服务
 * @author Administrator
 *
 */
public interface WeatherServices {

	/**
	 * 获取最新天气
	 * @return
	 */
	public Map<String, Object> findNewWeather(String citycode,String ip);
	
	public  Map<String,Object> getNewWeather(String ip,String citycode,String name);
}
