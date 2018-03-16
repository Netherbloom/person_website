package com.website.user.services;

import com.website.user.entity.UserLog;


public interface UserLogServices {

	/**
	 * 添加日志
	 * @param userLog
	 * @return
	 */
	public boolean save(UserLog userLog); 
}
