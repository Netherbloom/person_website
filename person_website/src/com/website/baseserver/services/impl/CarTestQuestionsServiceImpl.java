package com.website.baseserver.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.util.Gloab;
import com.util.HttpUtils;
import com.util.JsonUtil;
import com.website.baseserver.dao.CarTestQuestionsDao;
import com.website.baseserver.entity.CarTestQuestions;
import com.website.baseserver.services.CarTestQuestionsService;

@Service
public class CarTestQuestionsServiceImpl implements CarTestQuestionsService{

	private static Logger _Log=Logger.getLogger(CarTestQuestionsServiceImpl.class);
	
	@Autowired
	private CarTestQuestionsDao questionsDao;
	
	@Transactional
	@Override
	public void save(String subject,String type) throws Exception {
			List<Map<String, Object>> list=getQuestions(subject,type);
			if(list!=null && list.size()>0){
				for (Map<String, Object> map:list) {
					CarTestQuestions car=new CarTestQuestions();
					car.setAnswer(map.get("answer").toString());
					car.setApply_type(map.get("apply_type").toString());
					car.setChapter(map.get("chapter").toString());
					car.setCreatetime(DateTimeUtil.getCurDateTime());
					car.setAexplain(map.get("explain").toString());
					car.setOption1(map.get("option1").toString());
					car.setOption2(map.get("option2").toString());
					car.setOption3(map.get("option3").toString());
					car.setOption4(map.get("option4").toString());
					car.setPic(map.get("pic").toString());
					car.setQuestion(map.get("question").toString());
					car.setSubject(map.get("subject").toString());
					car.setType(map.get("type").toString());
					questionsDao.save(car);
				}
			}
			
	}
	
	/**
	 * 获取该类型所有试题
	 * @param subject
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getQuestions(String subject,String type) {
		int pagenum=1,pagesize=10; 
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		String host = "http://jisujiakao.market.alicloudapi.com";
		    String path = "/driverexam/query";
		    String method = "GET";
		    String appcode = Gloab.jz_appcode;
		    Map<String, String> headers = new HashMap<String, String>();
		    headers.put("Authorization", "APPCODE " + appcode);
		    Map<String, String> querys = new HashMap<String, String>();
		    querys.put("pagesize", pagesize+"");
		    querys.put("sort", "normal");//排序方式 正常排序normal 随机排序rand 默认normal
		    querys.put("subject", subject);//科目类别 1为科目一 4为科目四 默认1
		    querys.put("type", type);//题目类型 分为A1,A3,B1,A2,B2,C1,C2,C3,D,E,F 默认C1
		     while (true) {
		    	 querys.put("pagenum", pagenum+"");
				try {
					HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
					 //获取response的body
				   	 String resultStr=EntityUtils.toString(response.getEntity());
					 Map<String,Object> result1=(Map<String, Object>) JsonUtil.fromJson(resultStr, Map.class);
					 String str=result1.get("result").toString();
				     str = str.replace("{", "{\"");
				     str = str.replace("=", "\"=\"");
				     str = str.replace(", ", "\",\"");
				     str = str.replace("}", "\"}");
				     str = str.replace("}\"", "}");
				     str = str.replace("\"{", "{");
				     str=str.replace("=",":");
				     str = str.replace("]\"", "]");
				     str = str.replace("\"[", "[");
					 Map<String,Object> resul2=(Map<String, Object>) JsonUtil.fromJson(str, Map.class);
					 String liststr=resul2.get("list").toString();
					 liststr = liststr.replace("{", "{\"");
				     liststr = liststr.replace("=", "\"=\"");
				     liststr = liststr.replace(", ", "\",\"");
				     liststr = liststr.replace("}", "\"}");
				     liststr = liststr.replace("}\"", "}");
				     liststr = liststr.replace("\"{", "{");
				     liststr=liststr.replace("=",":");
					 List<Map<String,Object>> listmap=(List<Map<String, Object>>) JsonUtil.fromJson(liststr, List.class);
					 for (Map<String,Object> map:listmap) {
						 map.put("apply_type", map.get("type"));
						 map.put("type", type);
						 map.put("subject", subject);
						 list.add(map);
					}
					 if(listmap.size()<pagesize){
							break; 
					}
				} catch (Exception e) {
					_Log.info("error pagenum :"+pagenum);
					_Log.info("error msg:"+e.getMessage());
				}
		    	 pagenum++;
		    	 try {
					Thread.sleep(1000*2);//等待3秒钟，接口有次数限制
				} catch (InterruptedException e) {
					_Log.info("thread error:"+e.getMessage());
				}
			}
		   return list;
	}

}
