package com.employee.api.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
*
* @author 
*/
@Entity
@Table(name = "EMPLOYEE_LEAVE_DATE")
public class EmployeeLeaveDate extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8301760736838015348L;
	
	@NotNull
	@Column(name = "DATE")
	private Date date;
	
	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Employee employee = null;
	
	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_LEAVE_ID", referencedColumnName = "ID")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Leave employeeLeave = null;
	
	public EmployeeLeaveDate() {
		super();
	}

	public EmployeeLeaveDate(Date date, Employee employee, Leave employeeLeave) {
		super();
		this.date = date;
		this.employee = employee;
		this.employeeLeave = employeeLeave;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Leave getEmployeeLeave() {
		return employeeLeave;
	}

	public void setEmployeeLeave(Leave employeeLeave) {
		this.employeeLeave = employeeLeave;
	}

}
