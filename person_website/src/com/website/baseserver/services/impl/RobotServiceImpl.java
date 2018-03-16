package com.website.baseserver.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.util.Gloab;
import com.util.HttpUtils;
import com.util.JsonUtil;
import com.util.LuceneSerch;
import com.website.baseserver.dao.RobotDao;
import com.website.baseserver.entity.Robot;
import com.website.baseserver.services.RobotService;
import com.website.blog.entity.Blog;

@Service
public class RobotServiceImpl implements RobotService{

	@Autowired
	private RobotDao robotDao;
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Map<String,Object> getRobotReply(String question) {
		 String host = "http://jisuznwd.market.alicloudapi.com";
		 String path = "/iqa/query";
		 String appcode = Gloab.weather_appcode;
		 Map<String, String> headers = new HashMap<String, String>();
		 headers.put("Authorization", "APPCODE " + appcode);
		 Map<String, String> querys = new HashMap<String, String>();
		 querys.put("question", question);
		 try {
			HttpResponse response = HttpUtils.doGet(host, path, "GET", headers, querys);
			//获取response的body
	    	String resultStr=EntityUtils.toString(response.getEntity());
	    	Map<String,Object> result=(Map<String, Object>) JsonUtil.fromJson(resultStr, Map.class);
	    	String str=result.get("result").toString();
		     str = str.replace("{", "{\"");
		     str = str.replace("=", "\"=\"");
		     str = str.replace(", ", "\",\"");
		     str = str.replace("}", "\"}");
		     str = str.replace("}\"", "}");
		     str = str.replace("\"{", "{");
		     str=str.replace("=",":");
		     Map<String,Object> resul2=(Map<String, Object>) JsonUtil.fromJson(str, Map.class);
		     if(resul2!=null){
		    	 Robot robot=new Robot();
		    	 robot.setCreatedate(DateTimeUtil.getCurDateTime());
		    	 robot.setQuestion(question);
		    	 robot.setRelquestion(resul2.get("relquestion").toString()+"");
		    	 robot.setContent(resul2.get("content").toString());
		    	 robot.setType(resul2.get("type").toString());
		    	 robotDao.save(robot);
		    	 return resul2;
		     }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String [] args){
		LuceneSerch logic = new LuceneSerch();    
        String queryStr = "100002";//要查询的关键字
        String[] fieldarr = {"contentstr"};
        try {    
            Long startTime = System.currentTimeMillis();    
            List<Blog> result = logic.getResult(queryStr, fieldarr, Blog.class);
            int i = 0;    
            for (Blog bean : result) {    
                if (i == 100)    
                    break;    
                /**  
                 * 打印完整的结果  
                 * 
                 */    
                System.out.println("bean.content " + bean.getContentstr());
                i++;    
            }    
            System.out.println("searchBean.result.size : " + result.size());    
            Long endTime = System.currentTimeMillis();    
            System.out.println("查询所花费的时间为：" + (endTime - startTime) / 1000);    
        } catch (Exception e) {    
            e.printStackTrace();    
            System.out.println(e.getMessage());    
        }    
	}
}
