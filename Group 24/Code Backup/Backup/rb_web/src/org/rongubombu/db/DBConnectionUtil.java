package org.rongubombu.db;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.rongubombu.util.LogUtil;

public class DBConnectionUtil {
	Context initContext;
	Context envContext;
	DataSource ds;
	private static DBConnectionUtil instance = new DBConnectionUtil();
	private DBConnectionUtil(){
		try {
			initContext = new InitialContext();
			envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/rongubombu");
		} catch (NamingException e) {
			LogUtil.logInfo("DBConnectionUtil", "DBConnectionUtil", e.getMessage());
			LogUtil.logTrace(e);
		}
	}
	public static DataSource getDataSource(){
		return instance.ds;
	}
	public static Connection getConection() throws SQLException{
		return instance.ds.getConnection();
	}
	public static void closeConection(Connection con) throws SQLException{
		con.close();
	}
}
