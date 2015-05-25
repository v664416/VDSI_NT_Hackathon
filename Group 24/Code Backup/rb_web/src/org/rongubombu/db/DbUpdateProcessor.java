package org.rongubombu.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class DbUpdateProcessor implements Callable<Boolean> {

	private List<String> books;

	public DbUpdateProcessor(List<String> books) {
		this.books = books;
	}

	public boolean updateBook(List<String> books) {
		Date date = new Date();
		long startTime =System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + " Started : "+date);
		Connection conn = null;
		Statement ps = null;
		try {
			conn = DBConnectionUtil.getConection();
			ps = conn.createStatement();
			for (String book : books) {

				ps.addBatch(book);

			}
			ps.executeBatch();
		} catch (SQLException e) {
			return false;
		} finally {
			DBConnectionUtil.close(ps, null);
			DBConnectionUtil.closeConection(conn);
			System.out.println(Thread.currentThread().getName() + " Started : "+date+" Totaltime : "+(System.currentTimeMillis()-startTime));
		}
		return true;

	}

	@Override
	public Boolean call() throws Exception {
		return updateBook(books);
	}
}
