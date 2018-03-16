package com.website.admin.service;

import java.util.List;
import java.util.Map;

import com.website.admin.entity.Menu;


/**
 * 菜单服务
 * @author Administrator
 *
 */
public interface MenuService {

	/**
	 * 获取菜单
	 * @param parentsid
	 * @return
	 */
	public List<Map<String, Object>> getMenus(String parentsid,int level);
	
	public void save(Menu menu);
	
	public void delete(Menu menu);
	
	public List<Menu> findAll();
	
	public Menu findById(String id);
}
