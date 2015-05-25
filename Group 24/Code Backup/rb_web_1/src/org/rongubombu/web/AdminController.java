package org.rongubombu.web;

import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rongubombu.db.CatalogProcessor;
import org.rongubombu.db.DbUpdater;
import org.rongubombu.db.EsTimer;
import org.rongubombu.db.EsUpdater;

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
}
