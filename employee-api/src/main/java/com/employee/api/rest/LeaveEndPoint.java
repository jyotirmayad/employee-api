package com.employee.api.rest;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;

import com.employee.api.controller.EmployeeController;
import com.employee.api.controller.LeaveController;
import com.employee.api.model.Employee;
import com.employee.api.model.EmployeeLeaveDate;
import com.employee.api.model.Leave;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Class to define the Leave related APIs
 * 
 * @author Jyoti
 * @version 1.0
 */
@RequestScoped
@Path("employee/{employee:[0-9]*}/leave")
public class LeaveEndPoint  extends BaseEndpoint<Leave> {

	@EJB
	private LeaveController leaveController;

	@EJB
	private EmployeeController employeeController;

	public LeaveEndPoint() {
		this.type = LeaveEndPoint.class;
	}

	/**
	 * Create Leave API call
	 * 
	 * @param Leave entity
	 * @return JSON response
	 * @throws IllegalArgumentException
	 * @throws UriBuilderException
	 * @throws JsonProcessingException
	 */
	@POST
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response create(
			@Min(value = 1, message = "") @PathParam("employee") long id,
			@NotNull(message = ENTITY_VALIDATION) Leave entity)
					throws IllegalArgumentException, UriBuilderException, JsonProcessingException {

		//CHECK FOR FROM DATE <= TO DATE
		if(entity.getFromDate().after(entity.getToDate())) {
			// Create a hash map for response data
			HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
			responseData.put("message", "From Date should come before To Date.");
			return createResponse(412).entity(responseData).type(MediaType.APPLICATION_JSON).build();

		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		//CHECK FOR OVERLAP WITH OTHER LEAVE REQUESTS
		Long checkOverlap = employeeController.checkForOverlapDateInLeaveRequest(id, 
				dateFormat.format(entity.getFromDate()),
				dateFormat.format(entity.getToDate()));
		if( checkOverlap > 0 ) {
			// Create a hash map for response data
			HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
			responseData.put("message", "The dates overlaped with previous leave requests.");
			return createResponse(412).entity(responseData).type(MediaType.APPLICATION_JSON).build();

		}
		Employee employee = employeeController.find(id);
		entity.setEmployee(employee);

		Date startDate = entity.getFromDate();
		Date endDate = entity.getToDate();

		while(!startDate.after(endDate)) 
		{
			java.sql.Date sqlDate = startDate;
			Calendar cal = Calendar.getInstance();
			cal.setTime(sqlDate);
			java.sql.Date sqlTommorow = new java.sql.Date(cal.getTimeInMillis());

			EmployeeLeaveDate empLeaveDate = new EmployeeLeaveDate();
			empLeaveDate.setDate(sqlTommorow);
			empLeaveDate.setEmployee(employee);

			entity.getEmployeeLeaveDates().add(new EmployeeLeaveDate(sqlTommorow, employee, entity));

			cal.add(Calendar.DAY_OF_YEAR, 1);
			startDate = new java.sql.Date(cal.getTimeInMillis());

		}

		return createCreatedResponse(leaveController.create(entity)).build();
	}

	/**
	 * To fetch All Leave requests for an year
	 * 
	 * @param employee id
	 * @param year
	 * @return leaves
	 * @throws JsonProcessingException
	 * @throws NoResultException
	 */
	@GET
	@Path("/{year: [0-9][0-9][0-9][0-9]}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLeaveRequests(
			@Min(value = 1, message = "") @PathParam("employee") long id,
			@PathParam("year") int year)
					throws JsonProcessingException, NoResultException {
		List<Leave> leaves = leaveController.getLeaveRequestsByEmployee(id, year);
		return createOkResponse(leaves).build();
	}


	/**
	 * Calculate the total leave count of an employee in a given year.
	 * 
	 * @param employee id
	 * @param year
	 * @return json 
	 * @throws JsonProcessingException
	 * @throws NoResultException
	 */
	@GET
	@Path("/count/{year: [0-9][0-9][0-9][0-9]}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLeaveCount(
			@Min(value = 1, message = "") @PathParam("employee") long id,
			@PathParam("year") int year)
					throws JsonProcessingException, NoResultException {
		List<EmployeeLeaveDate> leaves = leaveController.getLeaveDaysByEmployee(id, year);
		HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
		responseData.put("count", leaves.size());
		return createResponse(200).entity(responseData).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * To fetch the leave count month-wise for an employee in an year.
	 * 
	 * @param employee id
	 * @param year
	 * @return json
	 * @throws JsonProcessingException
	 * @throws NoResultException
	 */
	@GET
	@Path("/count/monthly/{year: [0-9][0-9][0-9][0-9]}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMonthlyLeaveCount(
			@Min(value = 1, message = "") @PathParam("employee") long id,
			@PathParam("year") int year)
					throws JsonProcessingException, NoResultException {
		List<EmployeeLeaveDate> leaves1 = leaveController.getLeaveDaysByEmployeeMonth(id, 1, year);
		List<EmployeeLeaveDate> leaves2 = leaveController.getLeaveDaysByEmployeeMonth(id, 2, year);
		List<EmployeeLeaveDate> leaves3 = leaveController.getLeaveDaysByEmployeeMonth(id, 3, year);
		List<EmployeeLeaveDate> leaves4 = leaveController.getLeaveDaysByEmployeeMonth(id, 4, year);
		List<EmployeeLeaveDate> leaves5 = leaveController.getLeaveDaysByEmployeeMonth(id, 5, year);
		List<EmployeeLeaveDate> leaves6 = leaveController.getLeaveDaysByEmployeeMonth(id, 6, year);
		List<EmployeeLeaveDate> leaves7 = leaveController.getLeaveDaysByEmployeeMonth(id, 7, year);
		List<EmployeeLeaveDate> leaves8 = leaveController.getLeaveDaysByEmployeeMonth(id, 8, year);
		List<EmployeeLeaveDate> leaves9 = leaveController.getLeaveDaysByEmployeeMonth(id, 9, year);
		List<EmployeeLeaveDate> leaves10 = leaveController.getLeaveDaysByEmployeeMonth(id, 10, year);
		List<EmployeeLeaveDate> leaves11 = leaveController.getLeaveDaysByEmployeeMonth(id, 11, year);
		List<EmployeeLeaveDate> leaves12 = leaveController.getLeaveDaysByEmployeeMonth(id, 12, year);

		HashMap<String, Serializable> responseData = new HashMap<String, Serializable>();
		responseData.put("jan", leaves1.size());
		responseData.put("feb", leaves2.size());
		responseData.put("mar", leaves3.size());
		responseData.put("apr", leaves4.size());
		responseData.put("may", leaves5.size());
		responseData.put("jun", leaves6.size());
		responseData.put("jul", leaves7.size());
		responseData.put("aug", leaves8.size());
		responseData.put("sep", leaves9.size());
		responseData.put("oct", leaves10.size());
		responseData.put("nov", leaves11.size());
		responseData.put("dec", leaves12.size());

		return createResponse(200).entity(responseData).type(MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * To Cancel a leave request
	 * @param leave id
	 * @return json
	 * @throws JsonProcessingException
	 */
	@PUT
	@Path("/{id:[0-9]*}/cancel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelLeave(
			@Min(value = 1, message = ID_VALIDATION) @PathParam("id") long id)
					throws JsonProcessingException {
		
		Leave entity = leaveController.findById(id);
		entity.setStatus(0);
		entity.setEmployeeLeaveDates(new ArrayList<EmployeeLeaveDate>());
		
		return createOkResponse(leaveController.update(entity)).build();
	}

}
