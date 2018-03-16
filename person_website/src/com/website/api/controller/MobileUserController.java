package com.website.api.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.util.JsonUtil;
import com.website.baseserver.entity.Config;
import com.website.baseserver.services.ConfigService;
import com.website.core.controller.BaseController;
import com.website.user.entity.Userinfo;
import com.website.user.services.UserInfoServices;
import com.website.user.services.UserLogServices;

@Controller
@RequestMapping("/website/user/**")
public class MobileUserController extends BaseController{

	@Autowired
	private ConfigService configService;
	@Autowired
	private UserInfoServices userInfoServices;
	@Autowired
	private UserLogServices userLogServices;
	
	private static Logger _Log=Logger.getLogger(MobileUserController.class);
	
	/**
	 * 注册
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="register")
	public void register(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String nickname=request.getParameter("nickname");
		String password=request.getParameter("password");
		Userinfo userinfo=new Userinfo();
		userinfo.setNickname(nickname);
		userinfo.setPassword(password);
		userinfo.setRegister_source("website");
		userinfo.setType(3);
		userinfo.setStatus(1);
		String account=userInfoServices.saveOrUpdate(userinfo);
		Userinfo userinfo2=userInfoServices.getUserByUserName(account);
		Map<String, Object> data=new HashMap<String,Object>();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("account",userinfo2.getUsername());
		data.put("code", "200");
		data.put("data", map);
		writeJson(response, JsonUtil.toJson(data));
	}
	
	
	
	/**
	 * 微信注册
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="wxregister")
	public void wxregister(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String openid=request.getParameter("openid");
		String nickname=request.getParameter("nickname");
		String photo=request.getParameter("photo");
		Userinfo userinfo=new Userinfo();
		userinfo.setWxopenid(openid);
		userinfo.setNickname(nickname);
		userinfo.setPhoto(photo);
		userinfo.setRegister_source("wx");
		userinfo.setType(3);
		userinfo.setStatus(1);
		userInfoServices.saveOrUpdate(userinfo);
		Userinfo userinfo2=userInfoServices.getUserByOther(openid, "wx");
		Map<String, Object> data=new HashMap<String,Object>();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("account",userinfo2.getUsername());
		data.put("code", "200");
		data.put("data", map);
		writeJson(response, JsonUtil.toJson(data));
	}
	
	/**
	 * 扫码签到
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="signqrcode")
	public void signQRcode(HttpServletResponse response,HttpServletRequest request) throws Exception{
		String sign=request.getParameter("sign");
	//	String username=request.getParameter("account");
		Map<String, Object> data=new HashMap<String,Object>();
		Config config= configService.getConfigByKey("sing_day_qrcode");
		if(config==null){
			data.put("code", "201");
			data.put("msg", "签到图片未生成");
		}else if(sign.equals(config.getSystemvalue())){
			
		}else{
			data.put("code", "201");
			data.put("msg", "签到信息有误，请重新检查");
		}
	
		writeJson(response, JsonUtil.toJson(data));
	}
}
