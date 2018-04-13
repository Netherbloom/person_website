package com.website.user.services.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.CommonMethodUtil;
import com.util.Gloab;
import com.util.MD5Util;
import com.website.core.dao.QueryWhere;
import com.website.user.dao.UserinfoDao;
import com.website.user.entity.Userinfo;
import com.website.user.services.UserInfoServices;

@Service
public class UserInfoServicesImpl implements UserInfoServices{

	@Autowired
	private UserinfoDao userinfoDao;

	@Transactional
	@Override
	public String saveOrUpdate(Userinfo userinfo) {
		try {
			if(null!=userinfo && !StringUtils.isBlank(userinfo.getId())){
				Userinfo userinfo1=userinfoDao.getById(userinfo.getId());
				Userinfo newuser=(Userinfo)CommonMethodUtil.convertBean2Bean(userinfo1, userinfo);
				userinfoDao.update(newuser);
				return newuser.getUsername();
			}else{
				String account=this.createAccount();//有并发问题，账号重复
				userinfo.setCreaTime(new Date());
				if(userinfo.getPassword()==null || userinfo.getPassword().equals("")){
					userinfo.setPassword(account+"123456");
				}
				String password=MD5Util.MD5Encode(userinfo.getPassword());
				userinfo.setPassword(password);
				userinfo.setUsername(account);
				userinfoDao.save(userinfo);
				return account;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Userinfo> findUserinfos(int status){
		QueryWhere where=new QueryWhere();
		where.and("status",status);
		return userinfoDao.findByWhere(where);
	}

	@Override
	public Userinfo getUserinfoById(String id) {
		return userinfoDao.getById(id);
	}

	@Override
	public Userinfo getUserByUserName(String userName) {
		QueryWhere where=new QueryWhere();
		where.and("username",userName);
		Userinfo userinfo=userinfoDao.getByWhere(where);
		return userinfo;
	}

	@Transactional
	@Override
	public void checkHaveUser() {
		List<Userinfo>list= userinfoDao.findAll();
		if(list==null || list.size()==0){
			Userinfo userinfo=new Userinfo();
			userinfo.setCreaTime(new Date());
			userinfo.setNickname("admin");
			userinfo.setUsername("admin");
			userinfo.setType(1);
			userinfo.setStatus(1);
			userinfo.setPassword(MD5Util.MD5Encode("123456"));
			userinfoDao.save(userinfo);
		}
	}
	
	/**
	 * 生成账号
	 * @return
	 */
	public String createAccount(){
		int maxaccount=userinfoDao.getMaxAccount();
		if(maxaccount<Integer.parseInt( Gloab.init_account)){
			maxaccount=Integer.parseInt( Gloab.init_account);
		}
		++maxaccount;
		return maxaccount+"";
	}

	@Override
	public Userinfo getUserByOther(String openid,String type) {
		QueryWhere where=new QueryWhere();
		if(type.equals("wx")){
			where.and("wxopenid",openid);
		}else if(type.equals("qq")){
			where.and("qqopenid",openid);
		}
		Userinfo userinfo=userinfoDao.getByWhere(where);
		return userinfo;
	}
}
