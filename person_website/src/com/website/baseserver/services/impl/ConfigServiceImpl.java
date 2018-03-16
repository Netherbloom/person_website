package com.website.baseserver.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.website.baseserver.dao.ConfigDao;
import com.website.baseserver.entity.Config;
import com.website.baseserver.services.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService{

	@Autowired
	ConfigDao configDao;
	
	@Transactional
	@Override
	public void save(Config config) {
		if(config!=null){
			Config old=configDao.getConfigByKey(config.getSystemkey());
			if(old!=null){
				old.setSystemvalue(config.getSystemvalue());
				old.setUpdatedate(DateTimeUtil.getCurDateTime());
				old.setMemo(config.getMemo()==null?old.getMemo():config.getMemo());
				old.setIsuse(config.getIsuse());
				configDao.update(old);
			}else{//新增
				config.setCareatedate(DateTimeUtil.getCurDateTime());
				config.setUpdatedate(DateTimeUtil.getCurDateTime());
				configDao.save(config);
			}
		}
	}

	@Override
	public Config getConfigByKey(String key) {
		return configDao.getConfigByKey(key);
	}

}
