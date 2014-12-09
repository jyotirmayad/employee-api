package com.employee.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
*
* @author 
*/
@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Resource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1106280434818908095L;
	
	@NotNull
	@Size(min = 3)
	@Column(name = "EMP_NO", unique = true)
	private String emp_no = "";

}
