package com.website.user.services;

import java.util.List;

import com.website.user.entity.Userinfo;

public interface UserInfoServices {

	/**
	 * 新增或修改一条数据
	 * @param userinfo
	 * @return
	 */
	public String saveOrUpdate(Userinfo userinfo);
	
	/**
	 * 根据状态获取用户
	 * @return
	 */
	public List<Userinfo> findUserinfos(int status);
	
	/**
	 * 根据主键获取记录
	 * @param id
	 * @return
	 */
	public Userinfo getUserinfoById(String id);
	
	/**
	 * 判断该用户名是否存在
	 * @param userName
	 * @return
	 */
	public Userinfo getUserByUserName(String userName);
	
	/**
	 * 检查是否有用户，没有则插入
	 */
	public void checkHaveUser();
	
	/**
	 * 根据openid查询
	 * @param openid
	 * @return
	 */
	public Userinfo getUserByOther(String openid,String type);
}
