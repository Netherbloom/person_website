package com.website.user.controller;

/*import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.swetake.util.Qrcode;
import com.util.Gloab;
import com.util.HttpClientUtil;*/

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import com.util.CommArray.UserLogType;
import com.util.CommonMethodUtil;
import com.util.JsonUtil;
import com.util.MD5Util;
import com.website.core.controller.BaseController;
import com.website.core.vo.ResultVo;
import com.website.user.entity.UserLog;
import com.website.user.entity.Userinfo;
import com.website.user.services.UserInfoServices;
import com.website.user.services.UserLogServices;

@Controller
@RequestMapping("/center/**")
public class CenterController extends BaseController{

	@Autowired
	private UserInfoServices userInfoServices;
	
	@Autowired
	private UserLogServices userLogServices;
	
	@RequestMapping("index")
	public void index(HttpServletRequest request){
	}
	/**
	 * 登录
	 * @param session
	 * @param username
	 * @param password
	 */
	@RequestMapping("login")
	public void login(HttpSession session,String username,String password){
	}
	
	@RequestMapping("ajax_login")
	public void ajax_login(HttpSession session,HttpServletResponse response,HttpServletRequest request,String username,String password) throws Exception{
		Map<String, String> map=new HashMap<String, String>();
		if(StringUtils.isBlank(username)|| StringUtils.isBlank(password)){
			map.put("code", "201");
			map.put("msg", "账号或密码不能为空");
		}
		userInfoServices.checkHaveUser();
		password=MD5Util.MD5Encode(password);
		Userinfo user=userInfoServices.getUserByUserName(username);
		if(user!=null && user.getStatus()!=0 && user.getPassword().equals(password)){
			//添加登录日志
			UserLog log=new UserLog();
			String ip=CommonMethodUtil.getIp(request);
			log.setUserId(user.getId());
			log.setLoginIp(ip);
			log.setType(UserLogType._登录.getValue());
			log.setCreaTime(new Date());
			log.setBrowser(CommonMethodUtil.getBrowserInfo(request).get("browser"));
			log.setUrl(CommonMethodUtil.getBrowserInfo(request).get("url"));
			userLogServices.save(log);
			session.setAttribute("currentUser", user);
			map.put("code", "200");
			map.put("type",user.getType()+"");
		}else{
			if(user.getStatus()==0){
				map.put("code", "201");
				map.put("msg","该账号已被禁用");
			}else{
				map.put("code", "201");
				map.put("msg", "账号或密码错误");
			}
		}
		writeJson(response, JsonUtil.toJson(map));
	}
	
	/**
	 * 登出
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("logout")
	public void logout(HttpSession session,HttpServletResponse response) throws Exception{
		 //清除Session  
        session.invalidate();  
        ResultVo vo=new ResultVo();
        writeJson(response, JsonUtil.toJson(vo)); 
	}
	
	@RequestMapping("register")
	public void register(){
	}
	
}
