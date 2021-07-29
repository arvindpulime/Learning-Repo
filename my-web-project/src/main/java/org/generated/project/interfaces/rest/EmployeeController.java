package org.generated.project.interfaces.rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.generated.project.application.ConsumerData;
import org.generated.project.application.LoginCredential;
import org.generated.project.application.UpdatePassword;
import org.generated.project.domain.model.Employee;
import org.generated.project.domain.model.EmployeeId;
import org.generated.project.domain.services.UserService;

import io.swagger.annotations.Api;
@Api("psa-employee-tracker")
@Path("psa")
public class EmployeeController {
	
	@Inject
	private UserService userServ;

//	@Path("saveemployee")
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response saveEmployee(Employee emp) {
//		return Response.status(201).entity(userServ.addEmployee(emp)).build();
//	}
	
	  @Path("getemployee/{id}") 
	  @GET
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response getEmployee(@PathParam("id") String id) { 
		//return getService.getEmployee(new EmployeeId(id));
		return Response.status(200).entity(userServ.getEmployee(new EmployeeId(id))).build();
		}
	 
	  
	  @Path("updateemployee")
	   @POST
	   @Consumes(MediaType.APPLICATION_JSON)
	  public Response updateEmployee(Employee emp) {
		  System.out.println("update controller called..");
		  //returnupdateService.updateEmployee(emp);
		  return Response.status(200).entity(userServ.updateEmployee(emp)).build();
	  } 
	  
	  @Path("key/{id}")
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response sendMail(@PathParam("id") String id) {
	    	
		  
	    	return Response.status(200).entity((userServ.getRandomKey(id))).build();
	    	
	    }
	  
	  @Path("updatepswd")
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response updatePassword(UpdatePassword updatepswd) {
		  
		return Response.status(200).entity(userServ.updatePassword(updatepswd)).build();
		  
	  }
	  
	  @Path("login")
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    public String login(LoginCredential logindata)
	    {
	    
	    	return userServ.login(logindata);
	    }
	  
	  @Path("saveemployee")
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response validate(Employee emp) {
		  return Response.status(200).entity(userServ.validate(emp)).build();
	  }
	  
	  @Path("consume/{topic}")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public String consume(@PathParam("topic") String topic) {
		  
		  return userServ.consumer(topic);
	  }
	  
	  @Path("getConsumedData/{topic}")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public ArrayList<ConsumerData> consumer2(@PathParam("topic") String topic) {
		  
		  return userServ.consumer2(topic);
		  
	  }
}
	

