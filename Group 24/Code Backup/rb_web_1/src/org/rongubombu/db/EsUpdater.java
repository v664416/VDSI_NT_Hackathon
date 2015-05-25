package org.rongubombu.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class EsUpdater{
	private static final int incr_var = 1000;


	public void process(){
		
		readDBAndUpdateElasticSearch();
	}
	
	private void readDBAndUpdateElasticSearch() {
		int count = getCount();
		int startIndex = 0;
		int lastIndex = incr_var;
		if(count<incr_var)
		{
			lastIndex = count;
		}
		int nbrItr = (count / incr_var);
		if((count%incr_var)>0)
		{
			++nbrItr;
		}
		try
		{
			CallableExecutor ce = new CallableExecutor();
			for (int i = 0; i<nbrItr; i++)
			{
				ce.process(new Processor(startIndex, lastIndex));
				startIndex = lastIndex + 1;
				lastIndex = lastIndex + incr_var;
			}
			ce.check();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private int getCount() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = DBConnectionUtil.getConection();
			String sql = "SELECT MAX(BOOK_ID) AS COUNT FROM BOOKS";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(conn);
		}
		return 0;
	}

}
