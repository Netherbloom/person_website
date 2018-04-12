package com.website.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.website.admin.entity.Ebooks;
import com.website.admin.service.EbooksService;
import com.website.core.controller.BaseController;
import com.website.core.page.Page;
import com.website.core.page.PageRequest;
import com.website.core.page.Pageable;

@Controller
@RequestMapping("/admin/ebook/**")
public class EbookController extends BaseController{

	@Autowired
	private EbooksService ebooksService;
	
	/**
	 * 首页
	 * @param model
	 * @param request
	 */
	@RequestMapping("index")
	public void index(Model model,HttpServletRequest request){
	
	}
	
	
	/**
	 * 初始化书籍列表
	 * @param rows
	 * @param page
	 * @param title
	 * @return
	 */
	@RequestMapping("getinitebook")
	@ResponseBody
	public Map<String, Object> getinitebook(Integer rows,Integer page,String keywords){
		Pageable pageable =new PageRequest((page == null) ? 0 :page , rows);
		Page<Ebooks> pageresult=ebooksService.findAdminEbookPage(pageable, keywords);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total", pageresult.getRowCount());
		map.put("rows", pageresult.getResults());
		map.put("page", pageresult.getPageno());
		return map;
	}
	
	/**
	 * 删除书籍
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteEbook")
	@ResponseBody
	public Map<String, Object> deleteEbook(String id){
		Map<String, Object> map=new HashMap<String, Object>();
		Ebooks ebooks=ebooksService.getEbooksById(id);
		if(ebooks!=null){
			ebooksService.delete(ebooks);
			map.put("code", "200");
			map.put("msg", "操作成功");
		}else{
			map.put("code", "201");
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	
	/**
	 * 同步书籍
	 * @param id
	 * @return
	 */
	@RequestMapping("reloadEbook")
	@ResponseBody
	public Map<String, Object> reloadEbook(String id){
		Map<String, Object> map=new HashMap<String, Object>();
		Ebooks ebooks=ebooksService.getEbooksById(id);
		if(ebooks!=null){
			map.put("code", "200");
			map.put("msg", "操作成功");
		}else{
			map.put("code", "201");
			map.put("msg", "操作失败");
		}
		return map;
	}
}
