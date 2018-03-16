package com.util;

import java.util.Properties;


public class Gloab {


	public static String ServerInstance; // 服务实例编号
	public static String WebPath;//项目地址
	public static String weather_appcode;//天气接口code
	public static String jz_appcode;//驾照试题接口code
	public static String FilePath;//文件地址
	public static String init_account;//初始账号
	
	/**
	 * 项目名称
	 */
	public static final String PROJECT_NAME="person_website";
	
	/**
	 * 项目文件目录
	 */
	public static final String PROJECT_FILE_NAME ="website-file-system";
	
	/**
	 * 读取配置
	 * @throws IOException 
	 */
	public static boolean load() {
		
		String strFileName = "/conf/resources.properties";
		
		Properties ps = new Properties();
		try {
			// 加载
			ps.load(Gloab.class.getResourceAsStream(strFileName));
		}
		catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		
		ServerInstance = ps.getProperty("server_instance");
		if (null == ServerInstance) ServerInstance = "signle"; // 默认单例

		// path
		WebPath = ps.getProperty("web_path");
		FilePath = ps.getProperty("file_path");
		init_account=ps.getProperty("init_account");
		weather_appcode=ps.getProperty("weather_appcode");
		jz_appcode=ps.getProperty("jz_appcode");
		return true;
	}

}
