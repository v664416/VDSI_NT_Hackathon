package org.rongubombu.web;

import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.rongubombu.db.CatalogProcessor;
import org.rongubombu.db.DbUpdater;
import org.rongubombu.db.EsTimer;
import org.rongubombu.db.EsUpdater;
import org.rongubombu.elasticsearch.ELSearchConnectionUtil;

import com.sun.jersey.api.view.Viewable;

@Path("/admin")
public class AdminController {
	@javax.ws.rs.core.Context
	HttpServletRequest request;
	private static Timer timer=null;


	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable search() throws Exception{
		Viewable re = new Viewable("/common/admin.jsp");
		return re;
	}
	@GET
	@Path("/dbupdate")
	@Produces(MediaType.TEXT_HTML)
	public Viewable dbUpdate() throws Exception{
		CatalogProcessor.getInstance().processCatalog();
		request.setAttribute("Response","Processed");
		Viewable re = new Viewable("/common/admin.jsp");
		return re;
	}
	
	@GET
	@Path("/startTimer")
	@Produces(MediaType.TEXT_HTML)
	public Viewable startTimerScheduler() throws Exception{
		if(null==timer){
			timer=new Timer();
		}
		timer.schedule(new EsTimer(), 0L, 60*60*1000);
		request.setAttribute("Response","Processed");
		Viewable re = new Viewable("/common/admin.jsp");
		return re;
	}
	
	@GET
	@Path("/stopTimer")
	@Produces(MediaType.TEXT_HTML)
	public Viewable stopTimerScheduler() throws Exception{
		if(null!=timer){
			timer.cancel();
			timer.purge();
		}
		request.setAttribute("Response","Processed");
		Viewable re = new Viewable("/common/admin.jsp");
		return re;
	}
	
	
	@GET
	@Path("/esUpdate")
	@Produces(MediaType.TEXT_HTML)
	public Viewable esUpdate() throws Exception{
		new EsUpdater().process();
		request.setAttribute("Response","Processed");
		Viewable re = new Viewable("/common/admin.jsp");
		return re;
	}
	
	
	@GET
	@Path("/getAddPage")
	@Produces(MediaType.TEXT_HTML)
	public Viewable getAdminPage() throws Exception{
		Viewable re = new Viewable("/common/addbook.jsp");
		return re;
	}
	
	@GET
	@Path("/addBook")
	public String addBook(@QueryParam("authoname") String authoname, 
			@QueryParam("category") String category, @QueryParam("bookName") String bookName,
			 @QueryParam("publisher") String publisher) throws Exception{
			boolean flag = ELSearchConnectionUtil.addDocument(authoname, category, bookName, publisher);
			String message = "success";
			if(!flag){
				message = "Data insertion failed, please try again";
			}
			return message;
	}
	
	@GET
	@Path("/deleteBook")
	public String deleteBook(@QueryParam("type") String type, @QueryParam("docid") String docId) throws Exception{
			boolean flag = ELSearchConnectionUtil.deleteDocument(type, docId);
			String message = "success";
			if(!flag){
				message = "Data deletion failed, please try again";
			}
			return message;
	}
	
	@GET
	@Path("/updateBook")
	public String updateBook(@QueryParam("type") String type, @QueryParam("docid") String docId, @QueryParam("authoname") String authoname, 
			@QueryParam("category") String category, @QueryParam("bookName") String bookName,
			 @QueryParam("publisher") String publisher) throws Exception{
			boolean flag = ELSearchConnectionUtil.updateDocument(type, docId,authoname, category, bookName, publisher);
			String message = "success";
			if(!flag){
				message = "Data insertion failed, please try again";
			}
			return message;
	}
	
	@GET
	@Path("/getAdminSearchPage")
	public Viewable getAdminSearchPage() throws Exception{
		Viewable re = new Viewable("/common/adminsearch.jsp");
		return re;
	}
	
	@GET
	@Path("/getUpdateBookPage")
	public Viewable getUpdateBookPage(@QueryParam("type") String type, @QueryParam("docid") String docId, @QueryParam("authoname") String authoname, 
			@QueryParam("category") String category, @QueryParam("bookName") String bookName,
			 @QueryParam("publisher") String publisher) throws Exception{
		Viewable re = new Viewable("/common/updatebook.jsp");
		return re;
	}
}
