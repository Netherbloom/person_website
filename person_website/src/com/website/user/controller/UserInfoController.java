package com.website.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.website.core.controller.BaseController;
import com.website.user.entity.Userinfo;
import com.website.user.services.UserInfoServices;

@Controller
@RequestMapping("/userinfo/")
public class UserInfoController extends BaseController{

	@Autowired
	private UserInfoServices userInfoServices;
	
	@RequestMapping("list")
	public void list(Model model,HttpServletRequest request){
		model.addAttribute("userList", userInfoServices.findUserinfos(1));
	}
	
	@RequestMapping("edit")
	public void edit(HttpServletRequest request){
		String id=request.getParameter("id");
		Userinfo userinfo=new Userinfo();
		if(!StringUtils.isBlank(id)){
			 userinfo=userInfoServices.getUserinfoById(id);
		}
		request.setAttribute("item", userinfo);
	}
	
	@RequestMapping("ajax_edit")
	@ResponseBody
	public boolean ajax_edit(Userinfo userinfo){
		String account= userInfoServices.saveOrUpdate(userinfo);
		if(account!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping("checkusername")
	@ResponseBody
	public boolean checkusername(String username){
		Userinfo userinfo=userInfoServices.getUserByUserName(username);
		boolean flag= false;
		if(userinfo!=null && !StringUtils.isBlank(userinfo.getId())){
			flag=true;
		}
		return flag;
	}
	
}
