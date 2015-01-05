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
	
	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Employee employee = null;

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

}
