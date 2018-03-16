package com.website.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;





import com.util.JsonUtil;
import com.util.TreeNode;
import com.website.blog.entity.Blog;
import com.website.blog.services.BlogServices;
import com.website.core.controller.BaseController;
import com.website.core.vo.ResultVo;
import com.website.user.entity.Userinfo;

@Controller
@RequestMapping("/blog/**")
public class BlogController extends BaseController{

	@Autowired
	private BlogServices blogServices;
	
	@RequestMapping("list")
	public void list(Model model,HttpServletRequest request){

	}
	
	@RequestMapping("getMyBlog")
	public void getMyBlog(HttpSession session,HttpServletResponse response) throws Exception{
		Userinfo user=getusUserinfoBySession(session);
		 List<TreeNode> list=new ArrayList<TreeNode>();
		  TreeNode firstztree = new TreeNode();
		  firstztree.setId("0");
          firstztree.setName("全部");
          firstztree.setIsParent(true);
          firstztree.setOpen(true);
          list.add(firstztree);
		  if(user!=null){
			 List<Blog> blogs=blogServices.findBlogsList(user.getId());
			 if(blogs!=null &&blogs.size()>0){
				 for(Blog blog:blogs){
					 TreeNode ztree = new TreeNode();
		                ztree.setId(blog.getId());
		                ztree.setName(blog.getTitle());
		                if(StringUtils.isBlank(blog.getParentId())){
		                	ztree.setpId("0");
		                	ztree.setIsParent(true);
		                }else{
		                	ztree.setpId(blog.getParentId());
		                }
		                list.add(ztree);
				 } 
			 }
		}
		writeJson(response, JsonUtil.toJson(list));
	}
	
	@RequestMapping("add")
	public void add(Model model,HttpServletRequest request){
		String id=request.getParameter("id")+"";
		String type=request.getParameter("type")+"";
		Blog blog=null;
		if(type.equals("2")){
			blog=new Blog();
			blog.setParentId(id);
		}else{
			blog=blogServices.findBlogById(id);
		}
		model.addAttribute("blog", blog);
	}
	
	@RequestMapping("ajax_add")
	public void ajax_add(HttpServletResponse response,HttpSession session,Blog blog) throws Exception{
		ResultVo vo=new ResultVo();
		Userinfo user=getusUserinfoBySession(session);
		blog.setUserId(user.getId());
		blog.setContent(blog.getContentstr().getBytes());
		boolean flag=blogServices.saveOrUpdate(blog);
		if(!flag){
			vo=new ResultVo(false,"操作失败");
		}
		writeJson(response, JsonUtil.toJson(vo));
	}
	
	@RequestMapping("ajax_delete")
	public void ajax_delete(HttpServletResponse response,HttpServletRequest request) throws Exception{
		ResultVo vo=new ResultVo();
		String id=request.getParameter("id")+"";
		Blog blog=new Blog();
		blog.setId(id);
		boolean flag=blogServices.delete(blog);
		if(!flag){
			vo=new ResultVo(false,"操作失败");
		}
		writeJson(response, JsonUtil.toJson(vo));
	}
	
	/**
	 * 详细
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("getBlogDetails")
	public void getBlogDetails(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		Blog blog=blogServices.findBlogById(id);
		writeJson(response, JsonUtil.toJson(blog));
	}
	
	/**
	 * 热门
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("gethotblog")
	public void gethotblog(HttpServletResponse response) throws Exception{
		List<Blog> list=blogServices.findHotBlogsList();
		writeJson(response, JsonUtil.toJson(list));
	}
}
