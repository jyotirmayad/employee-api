package com.employee.api.test;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import com.employee.api.controller.EmployeeController;
import com.employee.api.model.Employee;

public class EmployeeApiTest {

	@Inject
	EmployeeController employeeController;

	@Inject
	Logger log;
	
	private static String employeeApiUrl = "http://localhost:8080/employee-api/rest/employee/";
	
	//execute only once, in the starting 
	@BeforeClass
	public static void beforeClass() {
		System.out.println("in before class");
		
	}
	

	@Test
	public void TestEmployeeApi() {
		try {
			this.EmployeeRegisterTest();
			this.LoginTest();
			this.UpdateEmployeeTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void EmployeeRegisterTest() throws Exception {
		System.out.println("Executing EmployeeRegisterTest");
		Employee newEmployee = new Employee();
		newEmployee.setFirst_name("jyoti");
		newEmployee.setLast_name("Dehury");
		newEmployee.setMiddle_name("");
		newEmployee.setEmail("jyotirmayad@mindfiresolutions.com");
		newEmployee.setDob((long) 23232323);
		newEmployee.setDoj((long) 23232323);
		newEmployee.setGender("male");
		newEmployee.setRole("Developer");
		newEmployee.setStatus("active");
		newEmployee.setPassword("password");
		newEmployee.setEmp_no("32432");
		newEmployee.setId( (long) 0);

		try {
			Entity<Employee> entity = Entity.json(newEmployee);
			Client client = createClient();
			WebTarget target = client
			        .target(employeeApiUrl);
 
			Response response = target
			        .request(MediaType.APPLICATION_JSON)
			        .accept(MediaType.APPLICATION_JSON)
			        .post(entity);

			
			Employee employee = response.readEntity(Employee.class);
			
			assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
			assertEquals("jyotirmayad@mindfiresolutions.com", employee.getEmail());
			assertEquals("", employee.getPassword());
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("EmployeeRegisterTest ENDs....");
	}
	
	public void LoginTest() throws Exception {
		System.out.println("Executing LoginTest");
		String email = "jyotirmayad@mindfiresolutions.com";
		String password = "password";
		Client client = createClient();
		WebTarget target = client
                .target(employeeApiUrl + "login?username=" + email + "&password=" + password);
 
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();

        
        Employee employee = response.readEntity(Employee.class);
        
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals("jyotirmayad@mindfiresolutions.com", employee.getEmail());
        assertEquals("", employee.getPassword());
 
		System.out.println("LoginTest Ends.....");
	}
	
	public void UpdateEmployeeTest() throws Exception {
		
		System.out.println("Executing UpdateEmployeeTest");
		
		String email = "jyotirmayad@mindfiresolutions.com";
		String password = "password";
		Client client = createClient();
		WebTarget target = client
                .target(employeeApiUrl + "login?username=" + email + "&password=" + password);
 
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();

        
        Employee employee = response.readEntity(Employee.class);
        
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals("jyotirmayad@mindfiresolutions.com", employee.getEmail());
        assertEquals("", employee.getPassword());
        
        try {
        	employee.setFirst_name("Jyotirmaya");
        	employee.setLast_name("Dehury");
        	Entity<Employee> entity = Entity.json(employee);
			target = client
			        .target(employeeApiUrl + employee.getId());
 
			response = target
			        .request(MediaType.APPLICATION_JSON)
			        .accept(MediaType.APPLICATION_JSON)
			        .put(entity);

			
			Employee updatedEmployee = response.readEntity(Employee.class);
			
			assertEquals(Status.OK.getStatusCode(), response.getStatus());
			assertEquals("Jyotirmaya", updatedEmployee.getFirst_name());
			assertEquals("", employee.getPassword());

			System.out.println("UpdateEmployeeTest Ends....");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Client createClient() {
		return ClientBuilder
				.newBuilder()
				.register(JacksonJaxbJsonProvider.class)
				.build();

	}
}
