package com.util.bootstraptest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.bootstarp.AbstractTask;
import com.util.bootstarp.QueueBootstrap;
import com.util.bootstarp.QueueScanTimer;
import com.util.bootstarp.WheelQueue;


/**
 * @ClassName QueueBootstrapTest
 * @Description test class QueueBootstrap.
 * @date 2017-03-22
 * @author hongjian.liu
 * @version 1.0.0
 *
 */
@SuppressWarnings("all")
public class QueueBootstrapTest {
	private static final Logger LOG = LoggerFactory.getLogger(QueueScanTimer.class);
	
	/**
	 * 单线程测试
	 */
	@Test
	public void testStart() {
		QueueBootstrap queueBootstrap = new QueueBootstrap();
		WheelQueue wheelQueue = queueBootstrap.start();
		wheelQueue.add(new AbstractTask("9527") {
			
			@Override
			public void run() {
				LOG.info("add task. id=" + this.getId());
			}
		}, 5);
	
		wheelQueue.add(new AbstractTask("9528") {

			@Override
			public void run() {
				LOG.info("running task. id=" + this.getId());
			}

		}, 8);

		wheelQueue.add(new AbstractTask("9529") {

			@Override
			public void run() {
				LOG.info("running task. id=" + this.getId());
			}

		}, 9);

		while (true) {

		}
	}

	/***
	 * 多线程加入
	 */
	@Test
	@SuppressWarnings("all")
	public void testThreadStart() {
		int threadCount = 3;
		final int sleep = 200;
		final int secondsRandom = 8000;
		QueueBootstrap queueBootstrap = new QueueBootstrap();
		final WheelQueue wheelQueue = queueBootstrap.start();
		Thread thread;
		
			thread = new Thread(new Runnable() {
				final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
				@Override
				public void run() {
					while (true) {
						wheelQueue.add(new AbstractTask(generateId()) {

							@Override
							public void run() {
								LOG.debug("business processes. id=" + this.getId());
							}

						}, threadLocalRandom.nextInt(0, secondsRandom));
						
						try {
							Thread.sleep(sleep);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			});

			thread.start();


		while (true) {
		}
	}

	private static AtomicInteger counter = new AtomicInteger(0);

	private static String generateId() {
		return String.format("C%05d", counter.incrementAndGet());
	}

	public static void main(String []args){
		
		int threadCount = 3;
		final int sleep = 200;
		final int secondsRandom = 8000;
	
		Thread thread;
		
		for (int i = 1; i <= threadCount; i++) {
			thread = new Thread(new Runnable() {
				final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
				@Override
				public void run() {
					System.out.println(generateId());

				}
			});

			thread.start();

		}
	}
	
}
