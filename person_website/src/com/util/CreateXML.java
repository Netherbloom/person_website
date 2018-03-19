package com.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.helpers.AttributesImpl;

public class CreateXML {

	public static List<String> getURL() throws IOException{
		List<String> list=new ArrayList<String>();
		list.add("https://www.gzebook.cn/index.html");
		list.add("https://www.gzebook.cn/booklist/booklist.html");
		 Document document=Jsoup.connect("https://www.gzebook.cn/booklist/booklist.html").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0)").get();
		 Elements chapters=document.select("a");
		 for (Element e:chapters) {
			 String second_url=e.attr("abs:href");//内容详情地址
			 list.add(second_url);
		}
		return  list;
	}
	
	/**
	 * 创建xml文件
	 * @param file
	 * @throws Exception
	 */
	 public static void SAXcreate(File file)throws Exception {
	        //初始化要生成文件的数据
		 	List<String> list=getURL();
	      
	        SAXTransformerFactory stf=(SAXTransformerFactory) SAXTransformerFactory.newInstance();
	        TransformerHandler handler=stf.newTransformerHandler();
	        Transformer tf=handler.getTransformer();
	        tf.setOutputProperty(OutputKeys.INDENT, "yes");//标签自动换行
	        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//编码格式
	        StreamResult result=new StreamResult(file);//创建Result对象
	        handler.setResult(result);//关联

	        handler.startDocument();
	        handler.startElement("http://www.sitemaps.org/schemas/sitemap/0.9", "", "urlset", null);//根节点标签
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        String time=sdf.format(new Date());
	        for (String str:list) {
	            AttributesImpl atts=new AttributesImpl();//创建熟悉
	            handler.startElement("", "", "url", atts);//元素标签开始

	            handler.startElement("", "", "loc", null);//元素标签开始
	            handler.characters(str.toCharArray(),0,str.length());//元素标签内容
	            handler.endElement("", "", "loc");//元素标签结束
	            
	            handler.startElement("", "", "lastmod", null);//元素标签开始
	            handler.characters(time.toCharArray(),0,time.length());//元素标签内容
	            handler.endElement("", "", "lastmod");//元素标签结束

	            handler.startElement("", "", "changefreq", null);//元素标签开始
	            handler.characters("Weekly".toCharArray(),0,6);//元素标签内容
	            handler.endElement("", "", "changefreq");//元素标签结束
	            
	            handler.endElement("", "", "url");//元素标签结束
	        }

	        handler.endElement("", "", "urlset");//结束根节点标签
	        handler.endDocument();
	    }
	
	 public static void main(String[]args){
		 File file=new File("D:/sitemap.xml");
		 try {
			SAXcreate(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
}
