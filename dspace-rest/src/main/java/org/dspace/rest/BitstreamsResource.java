package org.dspace.rest;

import org.dspace.utils.DSpace;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Constants;
import org.dspace.kernel.DSpaceKernelManager;
import org.dspace.rest.common.BitstreamEntity;
import org.dspace.usage.UsageEvent;

@Path("/bitstreams")
public class BitstreamsResource {
	
	private static final boolean writeStatistics;
	
	static{
		writeStatistics=ConfigurationManager.getBooleanProperty("rest","stats",false);
	}
	
	 /** log4j category */
    private static final Logger log = Logger.getLogger(BitstreamsResource.class);


    @GET
    @Path("/{bitstream_id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public BitstreamEntity getBitstream(@PathParam("bitstream_id") Integer bitstream_id, @QueryParam("expand") String expand) {
        return new BitstreamEntity(bitstream_id, expand);
    }
    
    @GET
    @Path("/{bitstream_id}/download")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getFile(@PathParam("bitstream_id")  Integer bitstream_id, 
    		@QueryParam("userIP") String user_ip, @QueryParam("userAgent") String user_agent, @QueryParam("xforwarderfor") String xforwarderfor,
    		@Context HttpHeaders headers, @Context HttpServletRequest request) {
    	BitstreamEntity be = new BitstreamEntity(bitstream_id);
    	
    	log.debug("write statistics : " + writeStatistics);
    	if(writeStatistics){
    		writeStats(bitstream_id, user_ip, user_agent, xforwarderfor, headers, request);
    	}
    	
    	log.debug("caller: " + user_ip + " " + user_agent + " " + xforwarderfor);
    	return Response.ok(be.getFile(), be.getMimeType()).build();
    }
    
        
	private void writeStats(Integer bitstream_id, String user_ip, String user_agent,
			String xforwarderfor, HttpHeaders headers,
			HttpServletRequest request) {
		
    	try{
    		org.dspace.core.Context context = new org.dspace.core.Context();
    		DSpaceObject bitstream = DSpaceObject.find(context, Constants.BITSTREAM, bitstream_id);
    		
    		if(user_ip==null || user_ip.length()==0){
    			new DSpace().getEventService().fireEvent(
	                     new UsageEvent(
	                                     UsageEvent.Action.VIEW,
	                                     request,
	                                     context,
	                                     bitstream));
    		} else{
	    		new DSpace().getEventService().fireEvent(
	                     new UsageEvent(
	                                     UsageEvent.Action.VIEW,
	                                     user_ip,
	                                     user_agent,
	                                     xforwarderfor,
	                                     context,
	                                     bitstream));
    		}
    		log.debug("fired event");
    		
		} catch(SQLException ex){
			log.error("SQL exception can't write usageEvent \n" + ex);
		}
    		
	}
}
