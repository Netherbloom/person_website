package com.website.baseserver.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.util.DateTimeUtil;
import com.util.grab.PeopleNews;
import com.website.baseserver.dao.NewsDao;
import com.website.baseserver.entity.News;
import com.website.baseserver.services.NewsService;
import com.website.core.dao.QueryWhere;
import com.website.core.page.Page;
import com.website.core.page.Pageable;

@Service
public class NewsServiceImpl implements NewsService{

	@Autowired
	private NewsDao newsDao;

	private static final Logger _log=LoggerFactory.getLogger(NewsServiceImpl.class);
	
	
	@Transactional
	@Override
	public void save(News news) {
		if(news!=null){
			news.setCreatedate(DateTimeUtil.getCurDateTime());
			news.setUpdatedate(DateTimeUtil.getCurDateTime());
			newsDao.save(news);
		}
	}

	@Transactional
	@Override
	public void update(News news) {
		if(news!=null){
			news.setUpdatedate(DateTimeUtil.getCurDateTime());
			newsDao.update(news);
		}
	}

	@Transactional
	@Override
	public void delete(News news) {
		if(news!=null){
			newsDao.delete(news);
		}
	}

	@Transactional
	@Override
	public void sysnNews() throws Exception{
		 List<News> result=new ArrayList<News>();
		 result.addAll(PeopleNews.getHtmlContent(PeopleNews.people_url));
		 result.addAll(PeopleNews.getHtmlContent(PeopleNews.social_event_url));
		 result.addAll(PeopleNews.getHtmlContent(PeopleNews.ori_url));
		 if(result!=null && result.size()>0){
			int num=1;
		 	for (News news:result) {
		 		if(newsDao.checkNews(news.getTitle())){
		 			continue;
		 		}
		 		newsDao.save(news);
		 		num++;
		 	}
		 	_log.info("newsnum:"+num);
		 }
	}

	@Override
	public Page<News> findPage(Pageable pageable,String title) {
		QueryWhere where=new QueryWhere();
		if(title!=null && !title.equals("")){
			where.andLikeAll("title", title);
		}
		return newsDao.findByWhere(where, pageable);
	}

	@Override
	public News getNewsById(String id) {
		return newsDao.getById(id);
	}

	
}
