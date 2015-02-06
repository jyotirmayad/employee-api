package com.employee.api.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * WFH model Class
 * 
 * @author Jyoti
 * @version
 */
@Entity
@Table(name = "WFH")
public class WFH extends Resource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2446791802919134440L;

	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Employee employee = null;
	
	@Column(name = "STATUS")
	private int status = 1;
	
	@Column(name = "CREATED")
	private Date created = new java.sql.Date(System.currentTimeMillis());

}
