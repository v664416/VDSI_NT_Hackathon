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
import org.json.JSONObject;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookName = request.getParameter("bookName") == null? "" : request.getParameter("bookName");
		String authorName = request.getParameter("authorName") == null? "" : request.getParameter("authorName");
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		String isParallelSearch = request.getParameter("isParallelSearch");
		
		CacheDetails key = new CacheDetails();
		key.setBookName(bookName);
		key.setAuthorName(authorName);
		key.setStartRow(startRow);
		
		CacheManager cm = CacheManager.newInstance();
		
		//2. Get a cache called "cache1", declared in ehcache.xml
		Cache cache = cm.getCache("cache1");
		
		JSONArray resultArray = null;
		String jsonString = null;
		JSONArray partialArray = new JSONArray();
		
		int flag = 0;
		
		try{
			if(!cache.isKeyInCache(key)){
				flag = 1;
				System.out.println("ENTERING DB");
				resultArray = SearchDAO.searchBooks(bookName, authorName, startRow, 8000);
				
				int totalLeftOut = 0;
				int totalThousands = 0;
				
				if(resultArray.length() < 8000){
					totalLeftOut = (resultArray.length()%1000);
					totalThousands = resultArray.length()/1000;
				}
				
				System.out.println("TOTAL LEFT " + totalLeftOut);
				System.out.println("TOTAL Thousands " + totalThousands);
				System.out.println("TOTAL RECORDS " + resultArray.length());
				for(int i = 0; i < resultArray.length(); i++){
					partialArray.put(resultArray.getJSONObject(i));
					
					if((resultArray.length() < 8000 && ((i+1)/1000) == totalThousands && ((i+1)%1000) == totalLeftOut) || (i+1)%1000 == 0){
						CacheDetails cacheKey = new CacheDetails();
						cacheKey.setBookName(bookName);
						cacheKey.setAuthorName(authorName);
						cacheKey.setStartRow(startRow);
						
						System.out.println("ASSIGNED START ROW : " + startRow);
						cache.put(new Element(cacheKey, partialArray.toString()));
						 
						startRow += 1000;
						
						partialArray = new JSONArray();
					}
				}
			}
			
			if("true".equals(isParallelSearch)){
				System.out.println("Parallel search...");
				int totalTimes = Integer.parseInt(request.getParameter("totalTimes"));
				
				JSONObject returnobj = new JSONObject();
				returnobj.put("NEXT_ROW", resultArray.length() > 0 ? (key.getStartRow()+8000) : 0);
				returnobj.put("totalTimes", totalTimes+1);
				
				response.setContentType("text/json");
				
				PrintWriter out = response.getWriter();
				
				out.print(returnobj.toString());
			}else{
				Element ele = cache.get(key);
				
				System.out.println(" cache.get(key) " + cache.get(key));
				
				if(ele != null){
					jsonString = (String)ele.getObjectValue();
				}
				
				System.out.println("*******************************");
				System.out.println(jsonString);
				JSONObject finalObject = new JSONObject();
				finalObject.put("NEXT_ROW", (key.getStartRow() + 8000));
				finalObject.put("RESULTS", new JSONArray(jsonString));
				finalObject.put("SEARCH_FLAG", flag);
				
				response.setContentType("text/json");
				
				PrintWriter out = response.getWriter();
				
				out.print(finalObject.toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
