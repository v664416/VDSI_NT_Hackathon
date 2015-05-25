package org.rongubombu.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;





import org.rongubombu.factory.RBFactory;
import org.rongubombu.interfaces.SearchProcessor;

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
	@Produces(MediaType.TEXT_HTML)
	public Viewable query() throws Exception{
		long start = System.currentTimeMillis();
		String query = request.getParameter("q");
		SearchProcessor processor = RBFactory.getSearchProcessor();
		List<Map<String,Object>> result = processor.getQueryResult(query);
		request.setAttribute("result", result);
		request.setAttribute("ResponseTime", (System.currentTimeMillis() -start) );
		Viewable re = new Viewable("/common/searchresp.jsp");
		return re;
	}
}