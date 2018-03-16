package com.website.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.website.admin.entity.Menu;
import com.website.admin.service.MenuService;
import com.website.blog.entity.Blog;
import com.website.core.controller.BaseController;
import com.website.core.vo.ResultVo;

@Controller
@RequestMapping("/admin/**")
public class AdminController extends BaseController{

	@Autowired
	private MenuService menuService;
	
	@RequestMapping("index")
	public void index(Model model,HttpServletRequest request){
	
	}
	
	/**
	 * 获取菜单
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("getmenu")
	public void  getmenu(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String parentid=request.getParameter("parentid");
		String level=request.getParameter("level");
		List<Map<String, Object>> list=menuService.getMenus(parentid, Integer.parseInt(level));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", 200);
		map.put("data", list);
		writeJson(response, JsonUtil.toJson(map));
	}
	
	@RequestMapping("menu")
	public void menu(Model model,HttpServletRequest request){
		
	}
	
	@RequestMapping("getmenulist")
	public void getmenulist(HttpSession session,HttpServletResponse response) throws Exception{
		 List<TreeNode> list=new ArrayList<TreeNode>();
		  TreeNode firstztree = new TreeNode();
		  firstztree.setId("0");
          firstztree.setName("全部");
          firstztree.setIsParent(true);
          firstztree.setOpen(true);
	      list.add(firstztree);
	      List<Menu> menus=menuService.findAll();
	      if(null!=menus && menus.size()>0){
	    	  for(Menu menu:menus){
	    		  TreeNode ztree = new TreeNode();
		            ztree.setId(menu.getId());
		            ztree.setName(menu.getName());
		            if(StringUtils.isBlank(menu.getParentId())){
		            	ztree.setpId("0");
		            	ztree.setIsParent(true);
		            }else{
		            	ztree.setpId(menu.getParentId());
		            }
		            list.add(ztree);
	    	  }
	      }
		writeJson(response, JsonUtil.toJson(list));
	}
	
	@RequestMapping("menuedit")
	public void menuedit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		Menu  menu=menuService.findById(id);
		if(Integer.parseInt(type)==0){//新增
			int level=menu==null?1:menu.getLevel();
			menu=new Menu();
			if(!id.equals("0")){
				level+=1;
			}
			menu.setParentId(id);
			menu.setLevel(level);
		}
		if(StringUtils.isBlank(menu.getName())){
			menu.setName(" ");
		}
		if(StringUtils.isBlank(menu.getCode())){
			menu.setCode(" ");
		}
		if(StringUtils.isBlank(menu.getIco())){
			menu.setIco(" ");
		}
		if(StringUtils.isBlank(menu.getUrl())){
			menu.setUrl(" ");
		}
		
		writeJson(response, JsonUtil.toJson(menu));
	}
	
	@RequestMapping("ajax_addmenu")
	public void ajax_add(Menu menu,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultVo vo=new ResultVo();
		try {
			if(menu.getLevel()==1 && StringUtils.isBlank(menu.getId())){
				menu.setParentId(null);
			}
			if(menu.getName()!=null){
				menu.setName(menu.getName().trim());
			}
			if(menu.getCode()!=null){
				menu.setCode(menu.getCode().trim());	
			}
			if(menu.getUrl()!=null){
				menu.setUrl(menu.getUrl().trim());
			}
		/*	if(request instanceof MultipartHttpServletRequest){
				MultipartFile file =  ((MultipartHttpServletRequest) request).getFile("ico");
				if(file!=null){
				String originalFileName=file.getOriginalFilename(); //文件名 带后缀
				//后缀名
				String suffix=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
				String filename=DateTimeUtil.getCurrentTime()+suffix;
				String filepath=Const.UPLOAD_FLODER_TMP+filename;
				file.transferTo(new File(filepath));
				FileInputStream fi=null;
				FileChannel in=null;
				fi=new FileInputStream(new File(filepath));
				in=fi.getChannel();
				fi.close();
				in.close();
				activity.setAttchpath(filename);
				activity.setAttchname(originalFileName);
			}
			}*/
			menuService.save(menu);
			vo=new ResultVo(true,"操作成功");
		} catch (Exception e) {
			vo=new ResultVo(false,"操作失败");
		}
		writeJson(response, JsonUtil.toJson(vo));
	}
	
	@RequestMapping("ajax_delmenu")
	public void ajax_delmenu(String id,HttpServletResponse response,HttpSession session,Blog blog) throws Exception{
		ResultVo vo=new ResultVo();
		try {
			Menu menu=menuService.findById(id);
			menuService.delete(menu);
			vo=new ResultVo(true,"操作成功");
		} catch (Exception e) {
			vo=new ResultVo(false,"操作失败");
		}
		writeJson(response, JsonUtil.toJson(vo));
	}
}
