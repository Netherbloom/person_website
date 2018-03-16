package com.website.baseserver.services;

import com.website.baseserver.entity.Config;

public interface ConfigService {

	/**
	 * 新增
	 * @param config
	 */
	public void save(Config config);
	
	/**
	 * 查询
	 * @param key
	 * @return
	 */
	public Config getConfigByKey(String key);
}
