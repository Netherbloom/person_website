package com.website.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.DateTimeUtil;
import com.website.baseserver.entity.News;
import com.website.baseserver.services.NewsService;
import com.website.core.controller.BaseController;
import com.website.core.page.Page;
import com.website.core.page.PageRequest;
import com.website.core.page.Pageable;

@Controller
@RequestMapping("/admin/news/**")
public class NewsController extends BaseController{

	@Autowired
	private NewsService newsService;
	
	@RequestMapping("index")
	public void index(Model model,HttpServletRequest request){
	
	}
	
	/**
	 * 初始化新闻数据
	 * @param rows
	 * @param page
	 * @param title
	 * @return
	 */
	@RequestMapping("getNewsPage")
	@ResponseBody
	public Map<String, Object> getNewsPage(Integer rows,Integer page,String title){
		Pageable pageable =new PageRequest((page == null) ? 0 :page , rows);
		Page<News> pageresult=newsService.findPage(pageable,title);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total", pageresult.getRowCount());
		map.put("rows", pageresult.getResults());
		map.put("page", pageresult.getPageno());
		return map;
	}
	
	@RequestMapping("edit")
	public void edit(String id,Model model,HttpServletRequest request){
		News news=newsService.getNewsById(id);
		model.addAttribute("news", news);
	}
	
	@RequestMapping("ajax_edit")
	@ResponseBody
	public Map<String, Object> ajax_edit(News news){
		Map<String, Object> map=new HashMap<String, Object>();
		if(news!=null){
			news.setUpdatedate(DateTimeUtil.getCurDateTime());
			newsService.update(news);
			map.put("code", "200");
			map.put("msg", "操作成功");
		}else{
			map.put("code", "201");
			map.put("msg", "参数为空，操作失败");
		}
		return map;
	}
	
	/**
	 * 删除新闻
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteNews")
	@ResponseBody
	public Map<String, Object> deleteNews(String id){
		Map<String, Object> map=new HashMap<String, Object>();
		News news=newsService.getNewsById(id);
		if(news!=null){
			newsService.delete(news);
			map.put("code", "200");
			map.put("msg", "操作成功");
		}else{
			map.put("code", "201");
			map.put("msg", "操作失败");
		}
		return map;
	}
}
