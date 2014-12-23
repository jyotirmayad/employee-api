package com.employee.api.rest;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;

import com.employee.api.controller.EmployeeController;
import com.employee.api.controller.GoalController;
import com.employee.api.model.Employee;
import com.employee.api.model.Goal;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 *
 * @author 
 */
@RequestScoped
@Path("employee/{employee:[0-9]*}/goal")
public class GoalEndPoint extends BaseEndpoint<Goal> {
	@EJB
	private GoalController goalController;
	
	@EJB
	private EmployeeController employeeController;

	public GoalEndPoint() {
		this.type = GoalEndPoint.class;
	}

	/**
	 *
	 * @param id
	 * @param startPosition
	 * @param maxResult
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(
			@Min(value = 1, message = "") @PathParam("employee") long id,
			@DefaultValue("0") @QueryParam("start") Integer startPosition,
			@DefaultValue("10") @QueryParam("max") Integer maxResult)
					throws IOException {

		Employee entity = employeeController.findEmployeeGoals(id);

		return createOkResponse(
				entity.getGoals()).build();
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
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response create(
			@Min(value = 1, message = "") @PathParam("employee") long id,
			@NotNull(message = ENTITY_VALIDATION) Goal entity)
					throws IllegalArgumentException, UriBuilderException, JsonProcessingException {

		Employee employee = employeeController.find(id);
		entity.setEmployee(employee);
		return createCreatedResponse(goalController.create(entity)).build();
	}
}
