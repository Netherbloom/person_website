package com.website.blog.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import com.util.JsonUtil;
import com.website.blog.entity.Blog;
import com.website.blog.services.BlogServices;
import com.website.core.controller.BaseController;
import com.website.core.vo.ResultVo;
import com.website.user.entity.Userinfo;

@Controller
@RequestMapping("/blog/detail/**")
public class BlogDetailController extends BaseController{

	@Autowired
	private BlogServices blogServices;
	
	@RequestMapping("index")
	public void index(Model model,HttpServletRequest request){

	}
	
	
	
	@RequestMapping("add")
	public void add(Model model,HttpServletRequest request){
		String id=request.getParameter("id")+"";
		model.addAttribute("blogId", id);
	}
	
	@RequestMapping("ajax_add")
	public void ajax_add(HttpServletResponse response,HttpSession session,Blog blog) throws Exception{
		ResultVo vo=new ResultVo();
		Userinfo user=getusUserinfoBySession(session);
		blog.setUserId(user.getId());
		boolean flag=blogServices.saveOrUpdate(blog);
		if(!flag){
			vo=new ResultVo(false,"操作失败");
		}
		writeJson(response, JsonUtil.toJson(vo));
	}
	
	@RequestMapping("ajax_delete")
	public void ajax_delete(HttpServletResponse response,HttpServletRequest request) throws Exception{
		ResultVo vo=new ResultVo();
		String id=request.getParameter("id")+"";
		Blog blog=new Blog();
		blog.setId(id);
		boolean flag=blogServices.delete(blog);
		if(!flag){
			vo=new ResultVo(false,"操作失败");
		}
		writeJson(response, JsonUtil.toJson(vo));
	}
	
	@RequestMapping("addimg")
	public void materialAdd(HttpServletRequest  request,String elements,HttpServletResponse response) throws Exception{
		elements= HtmlUtils.htmlUnescape(elements);
		writeJson(response, JsonUtil.toJson(elements));
	}
}
