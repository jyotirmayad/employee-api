package com.employee.api.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.employee.api.controller.EmployeeController;
import com.employee.api.model.Employee;

/**
 *
 *
 * @author 
 */
@RequestScoped
@Path("/employee")
public class EmployeeEndPoint extends BaseEndpoint<Employee> {

	@Inject
	private EmployeeController employeeController;

	/**
	 *
	 * @param startPosition
	 * @param maxResult
	 * @return
	 * @throws IOException
	 */
	@Path("/login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@DefaultValue("") @QueryParam("username") String userName,
			@DefaultValue("") @QueryParam("password") String password)
					throws IOException {
		
		// Create a hash map for response data
    	HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
		try {
			return createOkResponse(
					employeeController.findEmployeeByUsernamePassword(userName, password)).build();
		} catch (ConstraintViolationException e) {
			responseData.put("message", "Invalid Username or Password");
    		return Response.status(Response.Status.UNAUTHORIZED).entity(responseData).type(MediaType.APPLICATION_JSON).build();
		} catch (NoResultException e) {
			responseData.put("message", "Data not found");
			return Response.status(Response.Status.UNAUTHORIZED).entity(responseData).type(MediaType.APPLICATION_JSON).build();
		}
	}



}
