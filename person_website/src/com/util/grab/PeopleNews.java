package com.util.grab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.util.DateTimeUtil;
import com.website.baseserver.entity.News;

/**
 * 社会分类
 * @author dream
 *
 */
public class PeopleNews {

	/**
	 * 人民网-社会-民生报道
	 */
	public static final String people_url="http://society.people.com.cn/GB/41158/index.html";
	
	/**
	 * 人民网-社会-社会万象
	 */
	public static final String social_event_url="http://society.people.com.cn/GB/1062/index.html";
	
	/**
	 * 人民网-社会-本网原创
	 */
	public static final String ori_url="http://society.people.com.cn/GB/86800/index.html";
	
	
	/**
	 * 获取新闻内容
	 * @return
	 * @throws Exception
	 */
	public static List<News> getHtmlContent(String url) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日hh:mm");
		List<News> list=new ArrayList<News>();
		//获取内容
		Document doc=Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0)").get();
		String [] arrtype=doc.title().split("--");
		Elements links=doc.getElementsByClass("list_14 clearfix");
		Elements es=links.select("li").select("a");
	    for (Element e:es) {
	         try {
	        	 News news=new News();
	        	 String second_url=e.attr("abs:href");
	 	         Document second_doc=Jsoup.connect(second_url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0)").get();
	 	         Elements head=second_doc.getElementsByClass("clearfix w1000_320 text_title");
	 	         String title=head.select("h1").text();//新闻标题
	 	         String date[]=head.select("div").select(".box01").select("div").select(".fl").text().split("来源：");//时间
	 	         Elements contents=second_doc.getElementById("rwb_zw").select("p");//内容
	 	         String writer=second_doc.getElementById("rwb_zw").getElementsByClass("edit clearfix").text();//责编
	 	        //去除新闻样式
	 	         StringBuilder content=new StringBuilder();
	 	         for (Element c:contents) {
	 	        	String con= c.text();
	 	        	if(con!=null && !"".equals(con)){
	 	        		 content.append(con);
	 	        		 content.append("@@@");
	 	        	}else{
	 	        		String img =c.select("img").attr("abs:src");
	 	        		 content.append(img);
	 	        	}
					
	 	         }
	 	         //保存新闻
	 	         news.setFtype(arrtype[1]);
	 	         news.setStype(arrtype[0]);
	 	         news.setScource(date[1]);
	 	         news.setTitle(title);
	 	         news.setWriter(writer);
	 	         news.setCreatedate(DateTimeUtil.getCurDateTime());
	 	         news.setUpdatedate(DateTimeUtil.getCurDateTime());
	 	         news.setNewsdate(DateTimeUtil.formatDateTime2Minute(sdf.parse(date[0])));
	 	         news.setContent(content.toString());
	 	         news.setStatus(1);
	 	         list.add(news);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				continue;
			}
	       
	    }
		return list;
	} 
	
	public static void main(String [] args) throws Exception{

	}
	
	
}
