package com.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import jp.sourceforge.qrcode.QRCodeDecoder;
import com.swetake.util.Qrcode;

/**
 * QRCode生成二维码
 * @author dream
 *
 */
public class QRCodeUtil {

	private static Logger _Log=Logger.getLogger(QRCodeUtil.class); 
	
	/**
	  * 生成包含字符串信息的二维码图片
	  * @param outpath 文件输出路径
	  * @param content 二维码携带信息
	  * @param size 二维码图片大小
	  */
	 public static void createQrCode(String content,int size,String outpath,String filename) throws IOException{  
	  File file=new File(outpath);
	  if(!file.exists()){
		file.mkdirs(); 
	   }
	   Qrcode qrcode = new Qrcode();  
       // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小 
       qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）  
       qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符 
       qrcode.setQrcodeVersion(size);//  设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大     
       //设置一下二维码的像素  
       // 图片尺寸     
       int imgSize = 67 + 12 * (size - 1);  
       BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
       //绘图  
       Graphics2D gs = bufferedImage.createGraphics();  
       gs.setBackground(Color.WHITE);  
       gs.setColor(Color.BLACK);  
       gs.clearRect(0, 0, imgSize, imgSize);//清除下画板内容  
       //设置下偏移量,如果不加偏移量，有时会导致出错。  
       int pixoff = 2;  
       byte[] d = content.getBytes("gb2312");  
       if(d.length > 0 && d.length <120){  
           boolean[][] s = qrcode.calQrcode(d);  
           for(int i=0;i<s.length;i++){  
               for(int j=0;j<s.length;j++){  
                   if(s[j][i]){  
                       gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);  
                   }  
               }  
           }  
       }  
       gs.dispose();  
       bufferedImage.flush();  
       ImageIO.write(bufferedImage, "png", new File(outpath+"/"+filename));  
	 } 
	 
	 /**  
	  * 解析二维码（QRCode）  
	  * @param imgPath 图片路径  
	  * @return  
	  */    
	 public static String decoderQRCode(String imgPath) {    
	 	// QRCode 二维码图片的文件  
	    File imageFile = new File(imgPath);  
	    BufferedImage bufImg = null;  
	    String content = null;  
	    try {  
	        bufImg = ImageIO.read(imageFile);  
	        QRCodeDecoder decoder = new QRCodeDecoder();  
	        content = new String(decoder.decode(new MyQRCodeImage(bufImg)), "utf-8");   
	    } catch (Exception e) {  
	    	_Log.info("Error: " + e.getMessage());  
	    } 
	    return content;  
	 }    
	  
	  public static void main(String []args){
		  try {
			QRCodeUtil.createQrCode("http://www.baidu.com",8,"E:/space","qrcode");
			System.out.println(QRCodeUtil.decoderQRCode("E:/space/qrcode.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	
    
	
}
