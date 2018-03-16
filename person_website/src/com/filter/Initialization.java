package com.filter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.util.CommArray;
import com.util.Const;
import com.util.Gloab;


/**
 * 
 * 类名称：Initialization <br />
 * 类描述： 初始化系统参数 <br />
 * 
 * @version
 */
@Service
public class Initialization implements ServletContextAware {
	
	ServletContext context;

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@PostConstruct
	public void init() {
		initContentPath();
		// 公共数组
		if (null != context)context.setAttribute(Const.CONTEXT_COMMARRAY, CommArray.getInstance());
	}

	/**
	 * 初始化项目路径
	 */
	public void initContentPath() {
		
		// 加载全局变量
		if (null == Gloab.WebPath) Gloab.load();
		if (null == context) {
			// 单元测试时
			System.out.println("Initialization not context.");
			return;
		}
		
		
		// 网站根路径
		String contextPath = context.getContextPath();
		context.setAttribute(Const.CONTEXT_PATH, contextPath);

		// 上传路径
		String uploadPath = contextPath + "/uploads/";
		context.setAttribute(Const.CONTEXT_UPLOADPATH, uploadPath);

	
	}
}
