package com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * Http请求处理
 * @author Administrator
 *
 */
public class HttpClientUtil {
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final int DEFAULT_CONNTIMEOUT = 15000;
	
	/**
	 * get方式请求
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static String httpGet(String url, Map<String, String> headers, Map<String, String> params, String encoding) throws Exception, IOException {
		//添加参数到url
		if (params != null && !params.isEmpty()) 
		{
			if (url != null && url.indexOf("?") == -1)
				url += "?a=1";
			if (encoding != null && !"".equals(encoding))
				url += encoderPara(params, encoding);
			else
				url += encoderPara(params, DEFAULT_ENCODING);
		}
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_CONNTIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpGet get = new HttpGet(url);
		
		if (headers != null && !headers.isEmpty())
		{
			for (Map.Entry<String, String> entry : headers.entrySet()) 
				get.addHeader(entry.getKey(), entry.getValue());
		}
		
		HttpResponse response = httpClient.execute(get);
		String code = response.getStatusLine().getStatusCode() + "";
		if ("200".equals(code)) {
			code = EntityUtils.toString(response.getEntity());
		}
		httpClient.getConnectionManager().shutdown();
		return code;
	}
	

	/**
	 * post方式请求
	 * @param url
	 * @param headers
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> headers, Map<String, String> params, String encoding) throws Exception {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_CONNTIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost post = new HttpPost(url);
		
		//添加头参数
		if (headers != null && !headers.isEmpty()) 
		{
			for (Map.Entry<String, String> entry : headers.entrySet()) 
				post.addHeader(entry.getKey(), entry.getValue());
		}
		
		//添加请求参数
		if (params != null && !params.isEmpty()) 
		{
			List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
			
			for (Map.Entry<String, String> entry : params.entrySet()) 
				postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			
			UrlEncodedFormEntity entity = null;
			if (encoding != null && !"".equals(encoding))
				entity = new UrlEncodedFormEntity(postData, encoding);
			else
				entity = new UrlEncodedFormEntity(postData, DEFAULT_ENCODING);
			post.setEntity(entity);
		}
		
		HttpResponse response = httpClient.execute(post);
		String code = response.getStatusLine().getStatusCode() + "";
		if ("200".equals(code)) {
			code = EntityUtils.toString(response.getEntity());
		}
		httpClient.getConnectionManager().shutdown();
		return code;
	}
	
	/**
	 * 处理get方式请求参数
	 * @param paraMap
	 * @return
	 */
	public static String encoderPara(Map<String, String> params, String encoding) {
		StringBuilder para = new StringBuilder();
		if (params != null && !params.isEmpty()) 
		{
			String value = null;
			for (String key : params.keySet()) 
			{
				value = params.get(key);
				if (value != null && !"".equals(value))
					try {
						para.append("&" + key + "=" + URLEncoder.encode(String.valueOf(value), encoding));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
			}
		}
		return para.toString();
	}
	
	/**
	 * 获取头信息
	 * @param url
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> httpHeader(String url, Map<String, String> headers) throws Exception {
		Map<String, String> map = null;
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpHead httpHead = new HttpHead(url);
		if (headers != null && !headers.isEmpty() )
		{
			String value = null;
			for (String key : headers.keySet()) 
			{
				value = headers.get(key);
				if (value != null && !"".equals(value))
				{
					httpHead.setHeader(key, value);
				}
			}
		}
		HttpResponse response = httpClient.execute(httpHead);
		if (200 ==  response.getStatusLine().getStatusCode() )
		{
			Header [] m_Headers = response.getAllHeaders();
			if (m_Headers != null && m_Headers.length > 0)
			{
				map = new HashMap<String, String>();
				for (Header h : m_Headers)
					map.put(h.getName(), h.getValue());
			}
		}
		return map;
	}
	
	public static void main(String[]args) {
		String url = "http://www.baidu.com/";
		String url1 = "http://www.360buy.com/product/346488.html";
		String url2 = "http://www.iteye.com/news/23891";
		Map<String, String> headers = new HashMap<String, String>();
//		Map<String, String> params = new HashMap<String, String>();
		
		
		String url3 = "http://xt.unimip.cn/mobilepj/tzj/currencyRSS.jsp?id=1138";
		
		
		headers.put("ETag", "676fde59e26a24d3dc586191d131f02d");
		
		
		try {
			//String code = HttpClientUtil.httpPost(url, null, null, null);
			//String code = HttpClientUtil.httpGet(url, headers, params, null);
			//System.out.println("--:" + code);
			
			
			System.out.println(httpHeader(url2, headers));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
