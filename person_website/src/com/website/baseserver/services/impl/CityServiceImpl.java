package com.website.baseserver.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.Gloab;
import com.util.HttpUtils;
import com.util.JsonUtil;
import com.website.baseserver.dao.CityDao;
import com.website.baseserver.entity.City;
import com.website.baseserver.services.CityService;
import com.website.core.dao.QueryWhere;

@Service
public class CityServiceImpl implements CityService{

	@Autowired
	private CityDao cityDao;
	
	/**
	 * 调用api接口获取城市列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<City> getCityByApi(){
		List<City>list=new ArrayList<City>();
		String host = "http://jisutqybmf.market.alicloudapi.com";
		String path = "/weather/city";
		String appcode = Gloab.weather_appcode;
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		try {
			HttpResponse response = HttpUtils.doGet(host, path, "GET", headers, querys);
			//获取response的body
			String resultStr=EntityUtils.toString(response.getEntity());
			Map<String,Object> result=(Map<String, Object>) JsonUtil.fromJson(resultStr, Map.class);
			String liststr=result.get("result").toString();
			liststr=liststr.substring(1, liststr.length()-1);
			String [] listarr=liststr.split("},");
			for(String s:listarr){
			s=s.replace("=",":" );
			s = s.replace("{", "{\'");
			s = s.replace(":", "\':\'");
			s = s.replace(", ", "\',\'");
			s = s.replace("}", "\'}");
			if(!s.substring(s.length()-1,s.length()).equals("}")){
				s+="'}";
			}
			Map<String,Object> map=(Map<String, Object>) JsonUtil.fromJson(s, Map.class);
			if(map!=null){
				City city=new City();
				city.setCityid(map.get("cityid").toString());
				city.setParentid(map.get("parentid").toString());
				city.setCitycode(map.get("citycode").toString());
				city.setName(map.get("city").toString());
				city.setStatus(1);
				list.add(city);
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Transactional
	@Override
	public void batchsave() {
		List<City> list=getCityByApi();
		if(list!=null && list.size()>0){
			for(City city:list){
				cityDao.save(city);
			}
		}
	}

	@Override
	public List<City> getCitiesByParentId(String parentid,int status) {
		QueryWhere where=new QueryWhere();
		where.and("parentid",parentid);
		where.and("status",status);
		return cityDao.findByWhere(where);
	}
	
}
