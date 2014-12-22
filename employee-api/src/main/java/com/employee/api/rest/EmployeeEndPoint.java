package com.employee.api.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;

import com.employee.api.controller.EmployeeController;
import com.employee.api.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(
			@DefaultValue("0") @QueryParam("start") Integer startPosition,
			@DefaultValue("10") @QueryParam("max") Integer maxResult)
					throws IOException {
		List<Employee> employees = new ArrayList<Employee>();
		employees = employeeController.list(startPosition, maxResult);
		return createOkResponse(employees).build();
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("/{id:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(
			@Min(value = 1, message = ID_VALIDATION) @PathParam(value = "id") long id)
					throws JsonProcessingException, NoResultException {
		// Create a hash map for response data
		HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
		
		Employee employee = employeeController.find(id);
		
		responseData.put("status", employee.getStatus());
		responseData.put("email", employee.getEmail());
		responseData.put("id", employee.getId());
		responseData.put("emp_no", employee.getEmp_no());
		responseData.put("role", employee.getRole());
		responseData.put("first_name", employee.getFirst_name());
		responseData.put("last_name", employee.getLast_name());
		responseData.put("middle_name", employee.getMiddle_name());
		responseData.put("dob", employee.getDob());
		responseData.put("gender", employee.getGender());
//		responseData.put("skills", (Serializable) employee.getSkills());
//		responseData.put("goals", (Serializable) employee.getGoals());
		
		return createOkResponse(responseData).build();
	}

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
			Employee employee = employeeController.findEmployeeByUsernamePassword(userName, password);
			
			responseData.put("status", employee.getStatus());
			responseData.put("email", employee.getEmail());
			responseData.put("id", employee.getId());
			responseData.put("emp_no", employee.getEmp_no());
			responseData.put("role", employee.getRole());
			responseData.put("first_name", employee.getFirst_name());
			responseData.put("last_name", employee.getLast_name());
			responseData.put("middle_name", employee.getMiddle_name());
			responseData.put("dob", employee.getDob());
			responseData.put("gender", employee.getGender());
			responseData.put("skills", (Serializable) employee.getSkills());
			responseData.put("goals", (Serializable) employee.getGoals());
			
			return createOkResponse(responseData).build();

		} catch (ConstraintViolationException e) {
			responseData.put("message", "Invalid Username or Password");
			return Response.status(Response.Status.UNAUTHORIZED).entity(responseData).type(MediaType.APPLICATION_JSON).build();
		} catch (NoResultException e) {
			responseData.put("message", "Data not found");
			return Response.status(Response.Status.UNAUTHORIZED).entity(responseData).type(MediaType.APPLICATION_JSON).build();
		}
	}

	/**
	 *
	 * @param entity
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UriBuilderException
	 * @throws JsonProcessingException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(
			@NotNull(message = ENTITY_VALIDATION) Employee entity)
					throws IllegalArgumentException, UriBuilderException, JsonProcessingException {
		// Create a hash map for response data
		HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
		try {
			return createCreatedResponse(employeeController.create(entity)).build();
		} catch (ConstraintViolationException e) {
			responseData.put("message", "Invalid Data");
			return Response.status(Response.Status.CONFLICT).entity(responseData).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			responseData.put("message", "Error in the process");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseData).type(MediaType.APPLICATION_JSON).build();
		}
	}

	/**
	 *
	 * @param id
	 * @param entity
	 * @return
	 * @throws JsonProcessingException
	 */
	@PUT
	@Path("/{id:[0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(
			@Min(value = 1, message = ID_VALIDATION) @PathParam("id") long id,
			@NotNull(message = ENTITY_VALIDATION) Employee entity)
					throws JsonProcessingException {

		return createOkResponse(
				employeeController.update(entity)).build();
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 */
	@DELETE
	@Path("/{id:[0-9]*}")
	public Response delete(
			@Min(value = 1, message = ID_VALIDATION) @PathParam("id") long id)
					throws JsonProcessingException {

		employeeController.remove(id);
		return createNoContentResponse().build();
	}

}
