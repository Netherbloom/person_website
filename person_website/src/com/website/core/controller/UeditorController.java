package com.website.core.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.ueditor.ActionEnter;

/**
 * ueditor的公用Controller
 * @author Lionel
 * @version uetec_v0.1 2015年8月27日
 */
@RequestMapping("/ueditor")
@Controller
public class UeditorController  {
	
	/**
	 * ueditor请求后台入口
	 * @param request
	 * @param response
	 * @param model
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/ueditorUpload")
	public void ueditorUpload(HttpServletRequest request,
			HttpServletResponse response, Model model) throws JSONException {
		try {
			PrintWriter out = response.getWriter();
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type", "text/html");
			ServletContext application = request.getServletContext();
			String rootPath = application.getRealPath("/");
			out.write(new ActionEnter(request, rootPath).exec());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
