package com.website.admin.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;

import com.util.bootstarp.AbstractTask;
import com.util.bootstarp.QueueBootstrap;
import com.util.bootstarp.WheelQueue;
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
	
	@Override
	public Ebooks getEbooksById(String id) {
		return ebooksDao.getById(id);
	}

	@Override
	public Ebooks getEbooksByName(String name) {
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
		final List<Ebooks> newEbooks=GrabBooks.getEbooks();
			if(newEbooks!=null && newEbooks.size()>0){
				QueueBootstrap queueBootstrap = new QueueBootstrap();
				final WheelQueue wheelQueue = queueBootstrap.start();
				Thread thread= new Thread(new Runnable(){
					final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
					int num=1;
					@Override
					public void run() {
						for (final Ebooks ebooks:newEbooks) {
						
							wheelQueue.add(new AbstractTask(UUID.randomUUID().toString().replace("-", "")) {
								@Override
								public void run() {
									DefaultTransactionDefinition def = new DefaultTransactionDefinition();
									def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
									PlatformTransactionManager txManager = ContextLoader.getCurrentWebApplicationContext().getBean(PlatformTransactionManager.class);
									TransactionStatus status = txManager.getTransaction(def);
									try {
										Ebooks oldEbooks=ebooksDao.getBy("name", ebooks.getName());
										if(oldEbooks==null){
											ebooksDao.save(ebooks);
											oldEbooks=ebooksDao.getBy("name", ebooks.getName());
										}
										int num=chapterDao.getMaxPri(oldEbooks.getId());
										chapterService.syncinitChapter(oldEbooks, num);
										txManager.commit(status); // 提交事务
									} catch (Exception e) {
										System.out.println("异常信息：" + e.toString());
										 txManager.rollback(status); // 回滚事务
									}
								
								}

								
							}, threadLocalRandom.nextInt(0,8000));
							System.out.println(num);
							 num++;
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
					}
					
				});
				thread.start();
			}
	}

	@Override
	public List<Ebooks> findAllUpdate() {
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
	
	
}
