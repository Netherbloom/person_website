package com.website.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.DateTimeUtil;
import com.util.JsonUtil;
import com.website.baseserver.services.CarTestQuestionsService;
import com.website.baseserver.services.CityService;
import com.website.baseserver.services.NewsService;
import com.website.core.controller.BaseController;

@Controller
@RequestMapping("/website/base/**")
public class MobileBaseController extends BaseController{

	private static Logger _Log=Logger.getLogger(MobileBaseController.class);
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CarTestQuestionsService carTestQuestionsService;
	
	@Autowired
	private NewsService newsService;
	
	/**
	 * 保存天气城市code
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="savecity")
	public void savecity(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String, Object> data=new HashMap<String,Object>();
		try {
			cityService.batchsave();
			data.put("code", "200");
			data.put("msg", "操作成功");
		} catch (Exception e) {
			data.put("code", "201");
			data.put("msg", e.getMessage());
		}
		_Log.info("save city:"+DateTimeUtil.getCurDateTime());
		writeJson(response, JsonUtil.toJson(data));
	}
	
	/**
	 * 初始化驾照题目
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="savecarquestions")
	public void savecarquestions(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String, Object> data=new HashMap<String,Object>();
		String subject=request.getParameter("subject").toString();
		String type=request.getParameter("type").toString();
		try {
			carTestQuestionsService.save(subject, type);
			data.put("code", "200");
			data.put("msg", "操作成功");
		} catch (Exception e) {
			data.put("code", "201");
			data.put("msg", e.getMessage());
		}
		_Log.info("init carquestions:"+DateTimeUtil.getCurDateTime());
		writeJson(response, JsonUtil.toJson(data));
	}
	
	
	@RequestMapping(value="sysnNews")
	public void sysnNews(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String, Object> data=new HashMap<String,Object>();
		try {
			newsService.sysnNews();
			data.put("code", "200");
			data.put("msg", "操作成功");
		} catch (Exception e) {
			data.put("code", "201");
			data.put("msg", e.getMessage());
		}
		_Log.info("sysnNews time:"+DateTimeUtil.getCurDateTime());
		writeJson(response, JsonUtil.toJson(data));
	}
}
