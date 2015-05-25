package org.rongubombu.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

import org.rongubombu.elasticsearch.ELSearchConnectionUtil;

public class Processor implements Callable<Void>{

	private int startIndex;
	private int lastIndex;
	
	public Processor(int startIndex,int lastIndex) {
		this.startIndex=startIndex;
		this.lastIndex=lastIndex;
	}


	private void retreiveFromDbAndAddInElastic() {
		Connection conn=null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn=DBConnectionUtil.getConection();
			String sql="SELECT BOOK_NAME,AUTHOR_NAME,PUBLISHER,DESCRIPTION FROM BOOKS WHERE BOOK_ID BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1,startIndex);
			ps.setInt(2,lastIndex);
			rs= ps.executeQuery();
			while(rs.next())
			{
				Book book = new Book();
				book.setBookName(rs.getString("BOOK_NAME"));
				book.setAuthorName(rs.getString("AUTHOR_NAME"));
				book.setPublisherName(rs.getString("PUBLISHER"));
				book.setDescription(rs.getString("DESCRIPTION"));
			//	ELSearchConnectionUtil.addDocument(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(conn);
		}
		
	}
	@Override
	public Void call() throws Exception {
		retreiveFromDbAndAddInElastic();
		return null;
	}

}
