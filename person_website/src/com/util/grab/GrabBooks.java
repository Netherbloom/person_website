package com.util.grab;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.util.DateTimeUtil;
import com.website.admin.entity.EbookChapter;
import com.website.admin.entity.Ebooks;

/**
 * 同步书籍
 * @author Administrator
 *
 */
public class GrabBooks {

	public static List<ProxyInfo> listip=new ArrayList<ProxyInfo>();	
	
	public static final String books_url="https://www.37zw.net/xiaoshuodaquan/";//37
	
	/**
	 * 获取书名及地址
	 * @return
	 * @throws IOException
	 */
	public static List<Ebooks> getEbooks() throws IOException{
		List<Ebooks> list=new ArrayList<Ebooks>();
		//获取内容
		Document doc=Jsoup.connect(books_url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0)").get();
		Element content = doc.getElementById("main");
		Elements links = content.getElementsByTag("li");
		 for (Element e:links) {
			 String []name=e.text().split("/");
			 String second_url=e.select("a").attr("abs:href");//书籍详情地址
		     Ebooks ebooks=new Ebooks();
		     ebooks.setName(name[0]);//书名
		     ebooks.setWriter(name[1]);
		     ebooks.setCopyurl(second_url);//来源网址
		     ebooks.setCreatetime(DateTimeUtil.getCurDateTime());
		     list.add(ebooks);
		}
		 return list;
	}
	
	/**
	 * 获取章节
	 * @param url
	 * @param ebook
	 * @param num
	 * @return
	 * @throws Exception
	 */
		public static Ebooks getChapter(Ebooks ebook,int num) throws Exception{
			 Document document=Jsoup.connect(ebook.getCopyurl()).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0)").get();
			 Elements chapters=document.getElementById("list").getElementsByTag("dd").select("a");
			 String lasttime=document.getElementById("info").getElementsByTag("p").get(2).text().split("：")[1];
			 String type=document.getElementsByClass("con_top").text().split(">")[1].trim();
			 ebook.setType(type);//小说类型
			 ebook.setUpdatetime(lasttime);//最后更新时间
			 ebook.setIntro(document.getElementById("intro").getElementsByTag("p").text());//简介
		     String cover= document.getElementById("fmimg").select("img").attr("abs:src");//封面
		     ebook.setCover(cover);
			 List<EbookChapter> list=new ArrayList<EbookChapter>();
			 int i=1;
			 for (Element e:chapters) {
				 String second_url=e.attr("abs:href");//内容详情地址
				 String chaptername=e.text();
				 if(i>num){
					 EbookChapter chapter=new EbookChapter();
					 chapter.setChapter(chaptername);
					 chapter.setBookname(ebook.getName());
					 chapter.setEbookid(ebook.getId());
					 chapter.setPri(i);
					 chapter.setCopyurl(second_url);
					 try {
						 setChapterContent(chapter);
					} catch (Exception e2) {
						changeIP();
						setChapterContent(chapter);
					}
					 list.add(chapter);
				 }
				 i++;
			 }
			 ebook.setChapters(list);
			return ebook;
		}
		
		/**
		 * 获取章节内容
		 * @param chapter
		 * @return
		 * @throws IOException 
		 */
		public static void setChapterContent(EbookChapter chapter) throws IOException{
			 Document contentDocument=Jsoup.connect(chapter.getCopyurl()).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0)").get();
			 String content=contentDocument.getElementById("content").text();
			 chapter.setContent(content);
		}
	
		/**
		 * 初始化ip代理池
		 */
		@SuppressWarnings({ "unchecked", "deprecation" })
		public static void initIps(){
			if(listip==null || listip.size()==0){
				ProxyCralwerUnusedVPN vpn=new ProxyCralwerUnusedVPN();
				String text= vpn.startCrawler(5);
			    Map<String, Object> map=new HashMap<String,Object>();
			    map=JSONObject.parseObject(text);
			    String textString=JSONObject.toJSON(map.get("proxy")).toString();
			    List<Map<String, Object>> ips = new ArrayList<Map<String, Object>>();   
			    JSONArray jsonArray = JSONArray.fromObject(textString);//把String转换为json   
			    ips = JSONArray.toList(jsonArray,Map.class);
			    if(ips!=null && ips.size()>0){
			    	for (Map<String, Object> map2:ips) {
			    		ProxyInfo info=new ProxyInfo(map2.get("ip").toString(),map2.get("port").toString(),map2.get("type").toString());
			    		listip.add(info);
			    	}
			    }
			}
			//设置代理ip
			System.getProperties().setProperty("proxySet", "true");
			System.getProperties().setProperty("http.proxyHost", listip.get(0).getIp());
			System.getProperties().setProperty("http.proxyPort", listip.get(0).getPort());
		}
		
		/**
		 * 更换代理地址
		 */
		public static void changeIP() {
			ProxyInfo old=listip.get(0);
			listip.remove(0);
			listip.add(old);
			System.getProperties().setProperty("proxySet", "true");
			System.getProperties().setProperty("http.proxyHost", listip.get(0).getIp());
			System.getProperties().setProperty("http.proxyPort", listip.get(0).getPort());
			
			
		}
}
