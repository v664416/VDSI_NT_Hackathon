package com.vplan.hackathon;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;


public class SearchDAO {
	public static JSONArray searchBooks(String bookName, String authorName){
	   PreparedStatement stmt = null;
	   Connection conn = null;
	   
	   JSONArray arr = new JSONArray();
	   try{
	      Class.forName("com.mysql.jdbc.Driver");

	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackathon","root", "root");

	      System.out.println("Creating statement...");
	      stmt = conn.prepareStatement("select * from book_details where book_name like ? and author_name like ?");
	      
	      stmt.setString(1, "%" + bookName + "%");
	      stmt.setString(2, "%" + authorName + "%");
	      
	      System.out.println("bookName: " + bookName + " Author Name: " + authorName);
	      
	      ResultSet rs = stmt.executeQuery();

	      JSONObject obj = null;
	      
	      while(rs.next()){
	         //Retrieve by column name
	    	 obj = new JSONObject();
	    	  
	    	 obj.put("Book Name", rs.getString("book_name"));
	    	 obj.put("Author Name", rs.getString("author_name"));
	    	 
	    	 arr.put(obj);
	      }
	      
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
