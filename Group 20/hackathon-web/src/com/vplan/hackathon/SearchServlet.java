package com.vplan.hackathon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.json.JSONArray;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookName = request.getParameter("bookName") == null? "" : request.getParameter("bookName");
		String authorName = request.getParameter("authorName") == null? "" : request.getParameter("authorName");
		
		String key = bookName + "::" + authorName;
		
		CacheManager cm = CacheManager.newInstance();
		
		//2. Get a cache called "cache1", declared in ehcache.xml
		Cache cache = cm.getCache("cache1");
		
		JSONArray resultArray = null;
		String jsonString = null;
		
		try{
			if(!cache.isKeyInCache(key)){
				resultArray = SearchDAO.searchBooks(bookName, authorName);
				cache.put(new Element(key, resultArray.toString()));
				System.out.println("INSIDE IF BLOCK");
				
				request.setAttribute("BOOK_GRID", resultArray.toString());
			}else{
				Element ele = cache.get(key);
				
				System.out.println(" cache.get(key) " + cache.get(key));
				
				if(ele != null){
					jsonString = (String)ele.getObjectValue();
				}
				System.out.println("INSIDE ELSE");
				
				request.setAttribute("BOOK_GRID", jsonString);
			}
			
			System.out.println("AFTER SETTING DATA ");
			
			request.setAttribute("bookName", bookName);
			request.setAttribute("authorName", authorName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("EXITING");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request,response);
	}

}
