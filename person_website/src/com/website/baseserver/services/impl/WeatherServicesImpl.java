package com.website.baseserver.services.impl;

import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import com.util.Gloab;
import com.util.HttpUtils;
import com.util.JsonUtil;
import com.website.baseserver.services.WeatherServices;


@Service
public class WeatherServicesImpl implements WeatherServices{

	@Transactional
	@Override
	public Map<String, Object> findNewWeather(String citycode,String ip) {
		if(StringUtils.isBlank(citycode)){//城市code为空
			
		}else{
			
		}
		//获取前一个小时的天气
		//没有则新增一条
		return null;
	}

	/**
	 * 获取最新天气
	 * @param ip
	 * @param citycode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Map<String,Object> getNewWeather(String ip,String citycode,String name){
		    String host = "http://jisutqybmf.market.alicloudapi.com";
		    String path = "/weather/query";
		    String appcode = Gloab.weather_appcode;
		    Map<String, String> headers = new HashMap<String, String>();
		    headers.put("Authorization", "APPCODE " + appcode);
		    Map<String, String> querys = new HashMap<String, String>();
			if(StringUtils.isBlank(citycode)){
				querys.put("city", name);
			}else{
				querys.put("citycode", citycode);
			}
		    querys.put("ip", ip);
		    try {
		    	HttpResponse response = HttpUtils.doGet(host, path, "GET", headers, querys);
		    	//获取response的body
		    	String resultStr=EntityUtils.toString(response.getEntity());
		    	Map<String,Object> result=(Map<String, Object>) JsonUtil.fromJson(resultStr, Map.class);
		    	String str=result.get("result").toString();
		    	str = str.replace("{", "{\"");
				str = str.replace("=", "\"=\"");
				str = str.replace(", ", "\",\"");
				str = str.replace("}", "\"}");
				str = str.replace("}\"", "}");
				str = str.replace("\"{", "{");
				str=str.replace("=",":");
				str = str.replace("]\"", "]");
				str = str.replace("\"[", "[");
				Map<String,Object> result2=(Map<String, Object>) JsonUtil.fromJson(str, Map.class);
		    	if(result2!=null){
		    		return result2;
		    	}
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    return null;
	}
	
	public static void main(String []args){
		
	}
	
}
