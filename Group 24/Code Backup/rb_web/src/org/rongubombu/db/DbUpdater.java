package org.rongubombu.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.rongubombu.db.Book;
import org.rongubombu.db.CatalogProcessor;
import org.rongubombu.db.DBConnectionUtil;
import org.rongubombu.db.DbUpdateProcessor;



public class DbUpdater{

	private static final int incr_var = 5000;

//	public void process() throws ServletException, IOException {
//		long startTime = System.currentTimeMillis();
//	//	List<Book> books = CatalogProcessor.getInstance().processCatalog();
//		System.out.println("Reading catalog started: "+startTime);
//		System.out.println("Reading catalog total: "+(System.currentTimeMillis()-startTime));
//		if (null != books && !books.isEmpty()) {
//		int count=books.size();
//		int startIndex = 0;
//		int lastIndex = incr_var;
//		if(count<incr_var)
//		{
//			lastIndex = count;
//		}
//		int nbrItr = (count / incr_var);
//		if((count%incr_var)>0)
//		{
//			++nbrItr;
//		}
//		try
//		{
//			long strter = System.currentTimeMillis();
//			CallableExecutor ce = new CallableExecutor();
//			for (int i = 0; i<nbrItr; i++)
//			{
//				System.out.println("Count : "+count +"Start index : "+startIndex+" last index : "+lastIndex);
//			//	ce.process(new DbUpdateProcessor(books.subList(startIndex, lastIndex)));
//				startIndex = lastIndex + 1;
//				if(lastIndex + incr_var>count)
//				{
//					lastIndex=count-1;
//				}
//				else
//				{
//				lastIndex = lastIndex + incr_var;
//				
//				}
//				System.out.println("After updateCount : "+count +"Start index : "+startIndex+" last index : "+lastIndex);
//			}
//			long startTime1 = System.currentTimeMillis();
//			//ce.check();
//			System.out.println("Processing total: "+(System.currentTimeMillis()-strter));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally{
//			System.out.println("In finally");
//		}
//		
//			
//			
//		
//		deleteBooksTrigger();
//		}
//	}
	
	private void deleteBooksTrigger(){
		PreparedStatement ps = null;
		Connection conn=null;
		try {
			conn = DBConnectionUtil.getConection();
			ps = conn.prepareStatement("TRUNCATE BOOKS_TRIGGER");
			ps.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			DBConnectionUtil.close(ps, null);
			DBConnectionUtil.closeConection(conn);
		}
		

	}

}