package com.vplan.hackathon;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;


public class SearchDAO {
	public static JSONArray searchBooks(String bookName, String authorName, int startRow, int endRow){
	   PreparedStatement stmt = null;
	   Connection conn = null;
	   
	   JSONArray arr = new JSONArray();
	   try{
	      Class.forName("com.mysql.jdbc.Driver");

	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackathon","root", "root");

	      System.out.println("Creating statement...");
	      stmt = conn.prepareStatement("select * from (select * from book_details where book_name like ? and author_name like ? order by book_id) t limit ?, ?");
	      
	      System.out.println("select * from (select * from book_details where book_name like ? and author_name like ? ) t limit ?, ? order by book_id");
	      
	      stmt.setString(1, "%" + bookName.replaceAll(" ", "%") + "%");
	      stmt.setString(2, "%" + authorName.replaceAll(" ", "%") + "%");
	      stmt.setInt(3, startRow);
	      stmt.setInt(4, endRow);
	      
	      System.out.println("bookName: " + bookName + " Author Name: " + authorName);
	      System.out.println("start row: " + startRow + " end row: " + endRow);
	      
	      ResultSet rs = stmt.executeQuery();

	      JSONObject obj = null;
	      
	      while(rs.next()){
	         //Retrieve by column name
	    	 obj = new JSONObject();
	    	  
	    	 obj.put("bookName", rs.getString("book_name"));
	    	 obj.put("authorName", rs.getString("author_name"));
	    	 
	    	 arr.put(obj);
	      }
	      
	      System.out.println("retrieved");
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
		
	   return arr;
	}
}
