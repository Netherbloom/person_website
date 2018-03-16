package com.util;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;


/**
 * 公用方法
 * @author dream
 *
 */
public class CommonMethodUtil {
	
	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,  
         2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,  
         4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };  
	// 对应首字母区间表
    private final static String[] lc_FirstLetter = { "A", "B", "C", "D", "E",  
         "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",  
         "T", "W", "X", "Y", "Z" };  
	
	 /** 
	  * 获取每个汉字的大写首字母
	  * @param str 给定汉字串 
	  * @return 声母串 
	  */  
	 public static String getAllFirstLetter(String str) {  
	     if (str == null || str.trim().length() == 0) {  
	         return "";  
	     }  
	
	     String _str = "";  
	     for (int i = 0; i < str.length(); i++) {  
	         _str = _str + getFirstLetter(str.substring(i, i + 1));  
	     }  
	
	     return _str;  
	 }  
		
	 /** 
	  * 取得给定汉字的第一个首字母
	  * @param chinese 给定的汉字 
	  * @return 给定汉字的声母 
	  */  
	 public static String getFirstLetter(String chinese) {  
	     if (chinese == null || chinese.trim().length() == 0) {  
	         return "";  
	     }  
	     chinese =conversionStr(chinese, "GB2312", "ISO8859-1");  
	
	     if (chinese.length() > 1) // 判断是不是汉字  
	     {  
	         int li_SectorCode = (int) chinese.charAt(0); // 汉字区码  
	         int li_PositionCode = (int) chinese.charAt(1); // 汉字位码  
	         li_SectorCode = li_SectorCode - 160;  
	         li_PositionCode = li_PositionCode - 160;  
	         int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码  
	         if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {  
	             for (int i = 0; i < 23; i++) {  
	                 if (li_SecPosCode >= li_SecPosValue[i]  
	                         && li_SecPosCode < li_SecPosValue[i + 1]) {  
	                     chinese = lc_FirstLetter[i];  
	                     break;  
	                 }  
	             }  
	         } else // 非汉字字符,如图形符号或ASCII码  
	         {  
	             chinese = conversionStr(chinese, "ISO8859-1", "GB2312");  
	             chinese = chinese.substring(0, 1);  
	         }  
	     }  
	
	     return chinese;  
	 }  

	 /** 
	  * 字符串编码转换 
	  * @param str 要转换编码的字符串 
	  * @param charsetName 原来的编码 
	  * @param toCharsetName 转换后的编码 
	  * @return 经过编码转换后的字符串 
	  */  
	 private static String conversionStr(String str, String charsetName,String toCharsetName) {  
	     try {  
	         str = new String(str.getBytes(charsetName), toCharsetName);  
	     } catch (UnsupportedEncodingException ex) {  
	         System.out.println("字符串编码转换异常：" + ex.getMessage());  
	     }  
	     return str;  
	 }  
	 
	 /**
	 * 根据属性名获取属性值 
	 * @param fieldName
	 * @param o
	 * @return
	 */
	 public static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	         System.out.println("exception:"+e.getMessage());    
	           return null;    
	       }    
	   }   	     
	 
	 /**
	  * 为对象属性值为null的属性赋值
	  * @param from
	  * @param to
	  * @return
	  */
	 public static Object convertBean2Bean(Object from, Object to) {   
	        try {   
	            BeanInfo beanInfo = Introspector.getBeanInfo(to.getClass());   
	            PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();   
	  
	            for (PropertyDescriptor p : ps) {   
	               Method getMethod = p.getReadMethod();   
	               Method setMethod = p.getWriteMethod();   
	               if (getMethod != null && setMethod != null) {  
	            	   if(getFieldValueByName(p.getName(),to)==null){//此属性没有值则赋值
	            		   try {   
	 	                      Object result = getMethod.invoke(from);   
	 	                      setMethod.invoke(to, result);   
	 	                   } catch (Exception e) {   
	 	                      // 如果from没有此属性的get方法，跳过   
	 	                      continue;   
	 	                   }   
	            	   }
	               }   
	            }   
	        } catch (Exception e) {   
	           e.printStackTrace();   
	        }   
	  
	        return to;   
	    } 
	 
	 /**
	  * 
	  * 输出对象字符串
	  * @param o
	  * @return
	  */
	  public static String toString(Object o){
		    Field[] fields=o.getClass().getDeclaredFields();  
		    StringBuilder sb=new StringBuilder();
		    sb.append("[");
		    for(int i=0;i<fields.length;i++){  
		    	sb.append(fields[i].getName());
		    	sb.append(":");
		    	sb.append(CommonMethodUtil.getFieldValueByName(fields[i].getName(), o));
		    	sb.append("   ");
		    } 
		    sb.append("]");
		    return sb.toString();
	   }
	
	  	/**
		 * 获取ip
		 * @param request
		 * @return
		 */
		public static String getIp(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		} 
		
		/**
		 * 获取请求信息
		 * @param request
		 * @return
		 */
		public static Map<String, String> getBrowserInfo(HttpServletRequest request){
			Map<String, String> map=new HashMap<String, String>();
			map.put("other", request.getRemoteHost());
			map.put("browser", request.getHeader("User-Agent").toLowerCase());
			map.put("url", request.getRequestURL().toString());//获取请求url
			return map;
		}
	 
		/**
		 * 根据ip获取地址
		 * @param ip
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Map<String, Object> getAddressByIp(String ip){
			Map<String,Object> resultmap=new HashMap<String,Object>();
			try {
				URL url = new URL( "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);   
			    URLConnection conn = url.openConnection();   
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));   
			    String line = null;   
			    StringBuffer result = new StringBuffer();   
			    while((line = reader.readLine()) != null)  
			    {   
			      result.append(line);   
			    }   
			    reader.close();   
			    Map<String,Object> map=(Map<String, Object>) JsonUtil.fromJson(result.toString(), Map.class);
			    String str=map.get("data").toString();
				str = str.replace("{", "{\"");
				str = str.replace("=", "\"=\"");
				str = str.replace(", ", "\",\"");
				str = str.replace("}", "\"}");
				str=str.replace("=",":");
				resultmap=(Map<String, Object>) JsonUtil.fromJson(str.toString(), Map.class);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			  return resultmap;
		}
	/**
	 * 获取指定长度随机数	
	 * @param size
	 * @return
	 */
	public static String getRandNum(int size){
		Random random = new Random();  
		String result="";  
		for (int i=0;i<size;i++){  
		    result+=random.nextInt(10);  
		} 
		return result;
	}
		
	 public static void main(String []args){
		 System.out.println(CommonMethodUtil.getRandNum(6));
	 }
	
}
