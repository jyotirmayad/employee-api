package com.employee.api.model;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
*
* @author 
*/
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "GOAL")
public class Goal extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8095606040764944542L;
	
	@NotNull
	@Column(name = "GOAL")
	private String goal = "";
	
	@Column(name = "DATE")
	private Date date;
	
	@Column(name = "STATUS")
	private int status = 0;
	
	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Employee employee = null;

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
