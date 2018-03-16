package com.website.core.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.CommArray;
import com.util.JsonUtil;
import com.util.ResponseUtil;
import com.website.user.entity.Userinfo;



public class BaseController {

	/**
	 * 获取Context对象
	 * @param request
	 * @param attrName
	 * @return
	 */
	protected Object getContextAttr(HttpServletRequest request, String attrName) {
		//request.getServletContext() servlet3.0添加的方法，如果直接获取会报错
		return request.getSession().getServletContext().getAttribute(attrName);
	}
	
	/**
	 * 获取项目路径
	 * @param request
	 * @return
	 */
	protected String getContextPath(HttpServletRequest request) {
		return (String) getContextAttr(request, "path");
	}
	
	/**
	 * 获取session中登录用户
	 * @param request
	 * @return
	 */
	protected Userinfo getusUserinfoBySession(HttpSession session) {
		return  (Userinfo)session.getAttribute("currentUser");
	}
	
	/**
	 * 获取请求参数中的字符串,非null
	 * @param name
	 * @param request
	 * @return
	 */
	protected String getString(String name, HttpServletRequest request) {
		 String method = request.getMethod();  
		 name = request.getParameter(name);
	     if("GET".equalsIgnoreCase(method)){  
	        try {
				name = new String(name.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {

			}  
	     }		
		return name;
	}
	
	/**
	 * 输出json结果
	 * @param response
	 * @param context
	 * @throws Exception 
	 */
	protected void writeJson(HttpServletResponse response, String context) throws Exception {
		ResponseUtil.outJson(context, response);
	}
	
	/**
	 * 输出xml
	 * @param response
	 * @param context
	 * @throws Exception 
	 */
	protected void writeXml(HttpServletResponse response, String context) throws Exception {
		ResponseUtil.outXml(context, response);
	}
	
	/**
	 * 输出html
	 * @param response
	 * @param context
	 * @throws IOException
	 */
	protected void writeHtml(HttpServletResponse response, String context) throws IOException {
		ResponseUtil.outHtml(context, response);
	}	
	/**
	 * 输出json文件
	 * 
	 * @param response
	 * @param xml
	 * @throws IOException
	 */
	public void outPutToJson(HttpServletResponse response, boolean result, String code)
			throws IOException {
		Map<String, String> maps = new LinkedHashMap<String, String>();
		if(result){//成功默认200
			code  = CommArray.ReturnStatus._200.toString();
		}
		// 验证失败
		int index = getErrorIdx(code);
		String xpserror = CommArray.ReturnStatus.values()[index].toString();
		String xpsmessage =CommArray.ReturnStatus.values()[index].msg;
		maps.put("code", xpserror);
		maps.put("message", xpsmessage);
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(JsonUtil.toJson(maps));
		response.getWriter().flush();
	}
	
	/**
	 * 获取对应错误代码位置
	 * 
	 * @return
	 */
	public int getErrorIdx(String code) {
		int ret = 0;
		for (int i = 0; i < CommArray.ReturnStatus.values().length; i++) {
			if (CommArray.ReturnStatus.values()[i].equals(CommArray.ReturnStatus.valueOf("_" + code))) {
				ret = i;
				break;
			}
		}
		return ret;
	}
}
