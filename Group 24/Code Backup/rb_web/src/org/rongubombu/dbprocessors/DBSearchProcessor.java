package org.rongubombu.dbprocessors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.rongubombu.db.DBConnectionUtil;
import org.rongubombu.interfaces.SearchProcessor;

public class DBSearchProcessor implements SearchProcessor{

	/*public String getQueryResult(String query) throws Exception{
		Connection conn = DBConnectionUtil.getConection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from publisher");
		boolean flag = rs.next();
		StringBuilder sb = new StringBuilder();
		while(flag){
			sb.append("\n pub_id=").append(rs.getInt("pub_id"));
			sb.append(", pub_name=").append(rs.getString("pub_name"));
			flag = rs.next();
		}
		DBConnectionUtil.closeConection(conn);
		return sb.toString();
	}*/

	@Override
	public List<Map<String, Object>> getQueryResult(String s) throws Exception {
		return null;
	}

}
