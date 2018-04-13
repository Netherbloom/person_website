package com.util;
  
import com.gargoylesoftware.htmlunit.BrowserVersion;  
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;  
import com.gargoylesoftware.htmlunit.WebClient;  
import com.gargoylesoftware.htmlunit.html.HtmlForm;  
import com.gargoylesoftware.htmlunit.html.HtmlPage;  
import com.gargoylesoftware.htmlunit.html.HtmlTextInput; 

public class Test {
	
	
	public static void main(String[] args) {
	    String sUrl="http://122.13.0.69:8088/tpservices/namecheck?mode=1";//网址  
        //webclient设置  
        WebClient webClient = new WebClient(BrowserVersion.CHROME); //创建一个webclient    
        webClient.getOptions().setJavaScriptEnabled(true); // 启动JS            
        webClient.getOptions().setUseInsecureSSL(true);//忽略ssl认证              
        webClient.getOptions().setCssEnabled(false);//禁用Css，可避免自动二次请求CSS进行渲染              
        webClient.getOptions().setThrowExceptionOnScriptError(false);//运行错误时，不抛出异常     
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);  
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 设置Ajax异步        
        //登录  
        try {  
            HtmlPage page = (HtmlPage) webClient.getPage(sUrl);  
            HtmlForm form=page.getForms().get(0);//page.getFormByName("");  
            HtmlTextInput  txtUName = (HtmlTextInput )form.getInputByName("name"); //用户名text框  
            txtUName.setValueAttribute("张三");  
            HtmlTextInput txtPwd = (HtmlTextInput)form.getInputByName("mode");//密码框  
            txtPwd.setValueAttribute("1");  
            //submit没有name，只有class和value属性，通过value属性定位元素  
           // HtmlSubmitInput submit=(HtmlSubmitInput) form.getInputByValue("查询");  
           // page = (HtmlPage) submit.click();//登录进入  
            page = (HtmlPage) page.getHtmlElementById("submit").click();
            webClient.waitForBackgroundJavaScript(10000);//等待1秒  
              
            System.out.println(page.asText());  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}
}
