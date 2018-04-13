package com.website.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.admin.dao.MenuDao;
import com.website.admin.entity.Menu;
import com.website.admin.service.MenuService;

/**
 * 菜单服务
 * @author Administrator
 *
 */
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Map<String, Object>> getMenus(String parentid,int level) {
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		List<Menu> list=menuDao.getmenu(parentid, level);
		if(null!=list &&list.size()>0){
			for (Menu menu : list) {
				Map<String, Object> map=new HashMap<String, Object>();
				result.add(map);
				map.put("name", menu.getName());
				map.put("id", menu.getId());
				map.put("ico", menu.getIco());
				map.put("url", menu.getUrl());
				if(level>1){
					List<Menu> child=menuDao.getmenu(menu.getId(), level+1);
					map.put("list", child);
				}
			}
		}
		
		return result;
	}

	@Transactional
	@Override
	public void save(Menu menu) {
		if(StringUtils.isBlank(menu.getId())){
			menuDao.save(menu);
		}else{
			Menu oldMenu=menuDao.getById(menu.getId());
			menu.setId(oldMenu.getId());
			menuDao.update(menu);
		}
	
	}

	@Transactional
	@Override
	public void delete(Menu menu) {
		menuDao.delete(menu);
	}

	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}

	@Override
	public Menu findById(String id) {
		return menuDao.getById(id);
	}
	
}
