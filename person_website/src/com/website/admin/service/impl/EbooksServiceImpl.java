package com.website.admin.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.util.grab.GrabBooks;
import com.website.admin.dao.EbookChapterDao;
import com.website.admin.dao.EbooksDao;
import com.website.admin.entity.EbookChapter;
import com.website.admin.entity.Ebooks;
import com.website.admin.service.EbookChapterService;
import com.website.admin.service.EbooksService;
import com.website.core.page.Page;
import com.website.core.page.PageRequest;
import com.website.core.page.Pageable;

@Service
public class EbooksServiceImpl implements EbooksService{

	@Autowired
	private EbooksDao ebooksDao;
	@Autowired
	private EbookChapterDao chapterDao;
	@Autowired
	private EbookChapterService chapterService;

	/**
	 * 线程队列（非阻塞队列）
	 */
	private static ConcurrentLinkedQueue<Ebooks> concurrentLinkedQueue =new ConcurrentLinkedQueue<Ebooks>();
	
	/**
	 * 线程池
	 */
	private	static ExecutorService executorService=Executors.newCachedThreadPool();
	
	@Override
	public Ebooks getEbooksById(String id) {
		return ebooksDao.getById(id);
	}

	@Override
	public Ebooks getEbooksByName(String name) {
		System.out.println(name);
		return ebooksDao.getBy("name", name);
	}

	@Transactional
	@Override
	public void save(Ebooks ebooks) {
		if(ebooks!=null){
			ebooksDao.save(ebooks);
		}
	}

	@Transactional
	@Override
	public void update(Ebooks ebooks) {
		ebooksDao.update(ebooks);
	}

	@Transactional
	@Override
	synchronized public void syncinitEbooks() throws Exception {
		List<Ebooks> newEbooks=GrabBooks.getEbooks();
		List<Ebooks> list=ebooksDao.findAll();
			if(newEbooks!=null && newEbooks.size()>0){
				if(list!=null && list.size()>0){
					for (Ebooks ebooks:newEbooks) {
						if(!list.contains(ebooks)){
							ebooksDao.save(ebooks);
						}
					}
				}else{
					ebooksDao.save(newEbooks);
				}
			}
	}

	@Override
	public List<Ebooks> findAll() {
		return ebooksDao.findAllUpdate();
	}

	@Override
	public Page<Ebooks> findAdminEbookPage(Pageable pageable, String keywords) {
		return ebooksDao.findAdminEbookPage(pageable, keywords);
	}

	@Transactional
	@Override
	public void delete(Ebooks ebooks) {
		ebooksDao.delete(ebooks);
		Page<EbookChapter> page=chapterDao.findPageByEbookid(new PageRequest(0,Integer.MAX_VALUE), ebooks.getId());
		if(page!=null && page.getResults()!=null && page.getResults().size()>0){
			chapterDao.delete(page.getResults());
		}
	}

	@Transactional
	@Override
	public void syncChapter(Ebooks ebook) {
		executorService.execute(new Producer(ebook));
		
	}
	
	/**
	 * 每分钟执行一次，取队列中数据
	 */
	@Scheduled(cron="0 0/1 * * * ?")
	@Transactional
	@Override
	public void taskSaveChapter() {
		executorService.execute(new Consumer());
	}  
	
	/**
	 * 将获取章节放入线程队列
	 * @author Administrator
	 *
	 */
	 class Producer implements Runnable {  
        private Ebooks ebooks;  
        
        public Producer(Ebooks ebooks) {  
            this.ebooks = ebooks;  
        }  
  
    	@Override
        public void run() { 
    		int num=chapterService.getMaxPri(ebooks.getId());
    		System.out.println(num);
			try {
				ebooks=GrabBooks.getChapter(ebooks,num );
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
				
			//将得到的数据放入队列中
			if(ebooks.getChapters()!=null && ebooks.getChapters().size()>0){
				concurrentLinkedQueue.add(ebooks);  
			}
        }  
    }  
	
	/**
	 * 取出队列内容
	 * @author Administrator
	 *
	 */
	 class Consumer  implements Runnable {  
    	
    	boolean isRunning = true;
  
    	@Override
        public void run() {  
			while (isRunning) {
				Ebooks ebooks=concurrentLinkedQueue.poll();
				if(ebooks!=null && ebooks.getChapters()!=null && ebooks.getChapters().size()>0){
					chapterService.updateEbook(ebooks);
				}else{
					isRunning=false;
				}
			}
        }  
    }
	
	
}
