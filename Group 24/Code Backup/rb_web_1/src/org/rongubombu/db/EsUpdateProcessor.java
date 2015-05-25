package org.rongubombu.db;

import java.util.Date;
import java.util.concurrent.Callable;

import org.rongubombu.elasticsearch.ELSearchConnectionUtil;

public class EsUpdateProcessor implements Callable<Void>{
	
	private String book;
	private String bookId;

	public EsUpdateProcessor(String book, String bookId) {
		super();
		this.book = book;
		this.bookId = bookId;
	}

	@Override
	public Void call() throws Exception {
		Date date = new Date();
		long startTime =System.currentTimeMillis();
		try {
			System.out.println(Thread.currentThread().getName() + " Started : "+date);
		//	ELSearchConnectionUtil.addDocument(book, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			System.out.println(Thread.currentThread().getName() + " Started : "+date+" Totaltime : "+(System.currentTimeMillis()-startTime));
		}
		return null;
	}

}
