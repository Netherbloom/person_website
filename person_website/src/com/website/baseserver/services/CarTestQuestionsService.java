package com.website.baseserver.services;

/**
 * 驾照试题服务
 * @author Administrator
 *
 */
public interface CarTestQuestionsService {

	/**
	 * 新增
	 */
	public void save(String subject,String type)throws Exception;
}
