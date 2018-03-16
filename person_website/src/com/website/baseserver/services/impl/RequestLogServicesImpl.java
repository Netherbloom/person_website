package com.website.baseserver.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.website.baseserver.dao.RequestLogDao;
import com.website.baseserver.entity.RequestLog;
import com.website.baseserver.services.RequestLogServices;

@Service
public class RequestLogServicesImpl implements RequestLogServices{

	@Autowired
	private RequestLogDao requestLogDao;

	@Transactional
	@Override
	public void save(RequestLog log) {
		if(log!=null){
			log.setCreatetime(DateTimeUtil.getCurDateTime());
			requestLogDao.save(log);
		}
	}

	@Override
	public int getcount(String ip, String type) {
		return requestLogDao.getrequestcount(ip, type);
	}
	
}
