package com.website.admin.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.website.baseserver.entity.News;
import com.website.core.controller.BaseController;
import com.website.core.page.Page;
import com.website.core.page.PageRequest;
import com.website.core.page.Pageable;
import com.website.user.services.UserInfoServices;

@Controller
@RequestMapping("/admin/user/**")
public class UserController extends BaseController{

	@Autowired
	private UserInfoServices userInfoServices;
	
	@RequestMapping("list")
	public void list(Model model,HttpServletRequest request){
	
	}
	
/*	@RequestMapping("getUserList")
	@ResponseBody
	public Map<String, Object> getUserList(Integer rows,Integer page,String title){
		Pageable pageable =new PageRequest((page == null) ? 0 :page , rows);
		Page<News> pageresult=newsService.findPage(pageable,title);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total", pageresult.getRowCount());
		map.put("rows", pageresult.getResults());
		map.put("page", pageresult.getPageno());
		return map;
	}*/
}
