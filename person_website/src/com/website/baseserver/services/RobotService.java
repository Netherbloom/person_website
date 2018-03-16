package com.website.baseserver.services;

import java.util.Map;


/**
 * 机器问答
 * @author Administrator
 *
 */
public interface RobotService {

	/**
	 * 获取回复
	 * @param question
	 * @return
	 */
	public Map<String,Object> getRobotReply(String question);
	
}
