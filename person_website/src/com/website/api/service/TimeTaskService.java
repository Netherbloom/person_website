package com.website.api.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.util.CommonMethodUtil;
import com.util.Const;
import com.util.DateTimeUtil;
import com.util.MD5Util;
import com.util.QRCodeUtil;
import com.website.admin.entity.Ebooks;
import com.website.admin.service.EbookChapterService;
import com.website.admin.service.EbooksService;
import com.website.baseserver.entity.Config;
import com.website.baseserver.services.ConfigService;

/**
 *定时任务 
 * @author dream
 *
 */
@Service
public class TimeTaskService {
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	private EbooksService ebooksService;
	
	@Autowired
	private EbookChapterService chapterService;
	
	private static Logger _Log=Logger.getLogger(TimeTaskService.class);
	
	/**
	 * 每天1点生成签到图片
	 */
	@Scheduled(cron="0 0 1 * * ?")
	public void createQRCode(){
		try {
			String content=MD5Util.MD5Encode(DateTimeUtil.getCurDate()+CommonMethodUtil.getRandNum(6));
			String filename="singqrcode.png";
			Config config=new Config();
			config.setIsuse(1);
			config.setMemo(Const.UPLOAD_FLODER_ORDERQRCODE+filename);
			config.setSystemkey("sing_day_qrcode");
			config.setSystemvalue(content);
			QRCodeUtil.createQrCode(content,8,Const.UPLOAD_FLODER_ORDERQRCODE,filename);
			configService.save(config);
		} catch (IOException e) {
			System.out.println("createQRCode ERROR: "+e.getMessage());
		}
	}
	
	/**
	 * 每天一点更新书籍
	 */
	@Scheduled(cron="0 05 17 * * ?")
	public void syncEbook(){
		try {
			ebooksService.syncinitEbooks();
		} catch (Exception e) {
			_Log.info(e.getMessage());
		}
	}
	

}
