package com.website.user.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.user.dao.UserLogDao;
import com.website.user.entity.UserLog;
import com.website.user.services.UserLogServices;

@Service
public class UserLogServicesImpl implements UserLogServices{

	@Autowired
	private UserLogDao userLogDao;
	
	@Transactional
	@Override
	public boolean save(UserLog userLog) {
		try{
			userLogDao.save(userLog);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
