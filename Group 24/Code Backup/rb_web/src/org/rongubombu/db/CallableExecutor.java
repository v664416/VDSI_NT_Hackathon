package org.rongubombu.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CallableExecutor {

	private static ThreadPoolExecutor threadPoolExecutor = null;
	private List<Future<?>> futures = new ArrayList<Future<?>>();

	public void process(Callable<?> processor) {
		if (null == threadPoolExecutor) {
			threadPoolExecutor = new ThreadPoolExecutor(100, 100, 0L,
					TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		}
		if (null != processor) {

			futures.add(threadPoolExecutor.submit(processor));

		}

	}

	public void check() {
		
		for (Future<?> future : futures) {
			if (null != future) {
				try {
					if(!future.isDone()){
						future.get();
					}
				} catch (Exception e) {
					future.cancel(false);
				}
			}
		
		
	}
		shutdown();
		futures = new ArrayList<Future<?>>();
	}

	public void shutdown() {
		if (null != threadPoolExecutor) {
			threadPoolExecutor.shutdown();
		}
	}

}