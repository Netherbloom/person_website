package com.website.admin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.util.grab.GrabBooks;
import com.website.admin.dao.EbookChapterDao;
import com.website.admin.dao.EbooksDao;
import com.website.admin.entity.EbookChapter;
import com.website.admin.entity.Ebooks;
import com.website.admin.service.EbookChapterService;

@Service
public class EbookChapterServiceImpl implements EbookChapterService{

	@Autowired
	private EbookChapterDao chapterDao;
	@Autowired
	private EbooksDao ebooksDao;
	
	@Override
	public int getMaxPri(String ebookid) {
		return chapterDao.getMaxPri(ebookid);
	}

	@Transactional
	@Override
	public void save(List<EbookChapter> list) {
		if(list!=null && list.size()>0){
			chapterDao.save(list);
		}
	}

	@Transactional
	@Override
	public void syncinitChapter(Ebooks ebook, int num) {
		GrabBooks.initIps();
		while (true) {
			try {
				ebook=GrabBooks.getChapter(ebook, num);
				break;
			} catch (Exception e) {
				GrabBooks.changeIP();
			}
			
		}
		ebooksDao.update(ebook);
		chapterDao.save(ebook.getChapters());
	}

	@Transactional
	@Override
	public void updateEbook(Ebooks ebooks) {
		ebooksDao.update(ebooks);
		chapterDao.save(ebooks.getChapters());
		
	}

}
