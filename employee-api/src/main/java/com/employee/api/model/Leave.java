package com.employee.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.employee.api.util.NullCollectionSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
* Employee Leave model Class
* 
* @author Jyoti
* @version
*/
@Entity
@Table(name = "EMPLOYEE_LEAVE")
public class Leave  extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5205358403951136317L;
	
	@NotNull
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@NotNull
	@Column(name = "TO_DATE")
	private Date toDate;
	
	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "TYPE")
	private int type = 1;
	
	@Column(name = "STATUS")
	private int status = 1;
	
	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Employee employee = null;
	
	@JsonIgnore
    @JsonSerialize(using = NullCollectionSerializer.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeLeave", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<EmployeeLeaveDate> employeeLeaveDates = new ArrayList<EmployeeLeaveDate>();
	
	@Column(name = "CREATED")
	private Date created = new java.sql.Date(System.currentTimeMillis());


	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<EmployeeLeaveDate> getEmployeeLeaveDates() {
		return employeeLeaveDates;
	}

	public void setEmployeeLeaveDates(List<EmployeeLeaveDate> employeeLeaveDates) {
		this.employeeLeaveDates = employeeLeaveDates;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
