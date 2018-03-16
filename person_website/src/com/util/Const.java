package com.util;


/**
 * 全局参数
 * 
 */
public final class Const {

	/** 全局变量中的变量名称 */
	public static final String SYSTEM_NAME="person_website";
	/**全局变量中的变量名称*/
	public static final String CONTEXT_PATH = "path";
	public static final String CONTEXT_PLATFORMPATH = "platform";
	public static final String CONTEXT_UPLOADPATH = "CONTEXT_UPLOADPATH";
	public static final String CONTEXT_COMMARRAY = "commArray";
	public static final int MAXRANK = 1000;
	public static final String FILEPATHTYPE = "file";

	/** 默认显示条数 */
	public static final int DEFINE_PAGE = 10;
	public static final int MAX_CLIENT = 5;

	/** 静态文件 */
	public static final String[] RESOURCES = new String[] { "jpg", "png",
			"gif", "css", "js", "swf", "ttf", "woff" };

	public static final String UTF8 = "utf-8";
	
	/**文件上传相关配置**/
	public final static String UPLOAD_URL_ROOT = Gloab.WebPath + "/upload/";
	public final static String UPLOAD_URL_TMP = UPLOAD_URL_ROOT + "tmp/";
	
	public final static String UPLOAD_FLODER_ROOT = Gloab.FilePath;
	public final static String UPLOAD_FLODER_TMP = UPLOAD_FLODER_ROOT + "tmp/";
	
	/**二维码*/
	public final static String UPLOAD_URL_ORDERQRCODE = UPLOAD_URL_ROOT + "orderqrcode/";
	public final static String UPLOAD_FLODER_ORDERQRCODE  = UPLOAD_FLODER_ROOT + "orderqrcode/";
}
