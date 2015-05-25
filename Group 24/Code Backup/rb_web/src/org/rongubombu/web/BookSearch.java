package org.rongubombu.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.rongubombu.elasticsearch.ELSearchConnectionUtil;

import com.sun.jersey.api.view.Viewable;

@Path("/search")
public class BookSearch {
	@javax.ws.rs.core.Context
	HttpServletRequest request;


	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable search() throws Exception{
		Viewable re = new Viewable("/common/search.jsp");
		return re;
	}
	@GET
	@Path("/query")
	public String query(@QueryParam("q") String query, @QueryParam("page") int page) throws Exception{
		long start = System.currentTimeMillis();
		SearchResponse response = ELSearchConnectionUtil.getResult(query,page, 100);
		/*List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (SearchHit hit : response.getHits()) {
			result.add(hit.getSource());
		}*/
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", result);
		map.put("ResponseTime", (System.currentTimeMillis() -start));
		map.put("Count", result.size());
		String json = new Gson().toJson(map);*/
		StringBuilder sb = new StringBuilder("");
		sb.append("{");
		sb.append("\"result\":").append(response.toString());
		sb.append(", \"sresptime\":").append((System.currentTimeMillis() -start));
		sb.append(", \"resultcount\":").append(response.getHits().getHits().length);
		sb.append("}");
		/*System.out.println(sb.toString());*/
		return sb.toString();
	}
	
	
	@GET
	@Path("/query2")
	public String query2(@QueryParam("q") String query) throws Exception{
		SearchResponse response = ELSearchConnectionUtil.getResult(query,1, 10);
		StringBuilder sb = new StringBuilder("");
		for (SearchHit hit : response.getHits()) {
			sb.append("<option>");
			sb.append(hit.getSource().get("book_name"));
			sb.append(", "+hit.getSource().get("auth_name"));
			sb.append(", "+hit.getSource().get("category"));
			sb.append(", "+hit.getSource().get("publisher"));
			sb.append("</option>");
		}
		return sb.toString();
	}
}