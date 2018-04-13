package com.website.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.CommArray;
import com.util.CommonMethodUtil;
import com.util.JsonUtil;
import com.website.admin.entity.Ebooks;
import com.website.admin.service.EbookChapterService;
import com.website.admin.service.EbooksService;
import com.website.baseserver.entity.City;
import com.website.baseserver.entity.RequestLog;
import com.website.baseserver.services.CityService;
import com.website.baseserver.services.RequestLogServices;
import com.website.baseserver.services.RobotService;
import com.website.baseserver.services.WeatherServices;
import com.website.core.controller.BaseController;

@Controller
@RequestMapping("/website/app/**")
public class MobileAppController extends BaseController{

	@Autowired
	private WeatherServices weatherServices;
	@Autowired
	private RequestLogServices requestLogServices;
	@Autowired
	private CityService cityService;
	@Autowired
	private RobotService robotService;
	@Autowired
	private EbooksService ebooksService;
	@Autowired
	private EbookChapterService chapterService;
	
	private static Logger _Log=Logger.getLogger(MobileAppController.class);
	
	/**
	 * 天气查询
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="serchweather")
	public void serchweather(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String citycode=request.getParameter("citycode");
		String name="";
		String ip=CommonMethodUtil.getIp(request);
		if(StringUtils.isBlank(citycode)){
			name=CommonMethodUtil.getAddressByIp(ip).get("city").toString();
		}
		Map<String, Object> data=new HashMap<String,Object>();
		try {
			data.put("code", "200");
			Map<String,Object> result=weatherServices.getNewWeather(ip, citycode, name);
			if(result==null){
				data.put("code", "201");
				data.put("msg","未查询到结果");
				writeJson(response, JsonUtil.toJson(data));
				return;
			}
			data.put("result", result);
			RequestLog log=new RequestLog();
			log.setIp(ip);
			log.setType(CommArray.requestType._天气.getValue());
			requestLogServices.save(log);
		} catch (Exception e) {
			_Log.info("serchweather error:"+e.getMessage());
			data.put("code", "201");
			data.put("msg", e.getMessage());
		}
		writeJson(response, JsonUtil.toJson(data));
	}
	
	/**
	 * 获取城市
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="getcity")
	public void getcity(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String parendId=request.getParameter("cityid");
		Map<String, Object> data=new HashMap<String,Object>();
		try {
			data.put("code", "200");
			List<City> list=cityService.getCitiesByParentId(parendId, 1);
			List<Map<String, String>> maplist=new ArrayList<Map<String, String>>();
			if(list!=null && list.size()>0){
				for (City city:list) {
					Map<String, String> map=new HashMap<String,String>();
					map.put("cityid", city.getCityid());
					map.put("citycode",city.getCitycode());
					map.put("cityname",city.getName());
					maplist.add(map);
				}
				data.put("result", maplist);
			}
		} catch (Exception e) {
			_Log.info("error:"+e.getMessage());
			data.put("code", "201");
			data.put("msg", e.getMessage());
		}
		writeJson(response, JsonUtil.toJson(data));
	}
	
	/**
	 * 机器人问答
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="getrobotreply")
	public void getRobotReply(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String question=request.getParameter("question");
		final String ip=CommonMethodUtil.getIp(request);
		Map<String, Object> data=new HashMap<String,Object>();
		if(StringUtils.isBlank(question)){
			data.put("code", "201");
			data.put("msg", "问题不能为空");
		}else{
			try {
				data.put("code", "200");
				data.put("result", robotService.getRobotReply(question));
				Thread t=new Thread(new Runnable() {
					@Override
					public void run() {
						RequestLog log=new RequestLog();
						log.setIp(ip);
						log.setType(CommArray.requestType._机器问答.getValue());
						requestLogServices.save(log);	
					}
				});
				t.start();
				t=null;
			} catch (Exception e) {
				_Log.info("error:"+e.getMessage());
				data.put("code", "201");
				data.put("msg", e.getMessage());
			}
		}
		writeJson(response, JsonUtil.toJson(data));
	}
	
}
