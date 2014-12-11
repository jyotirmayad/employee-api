package com.employee.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.employee.api.util.NullCollectionSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
*
* @author 
*/
@Entity
@Table(name = "SKILL")
public class Skill extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230116930351577984L;
	
	
	@NotNull
	@Size(min = 1)
	@Column(name = "SKILL")
	private String skill = "";
	
	@JsonIgnore
    @JsonSerialize(using = NullCollectionSerializer.class)
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "skills")
	private List<Employee> employees = new ArrayList<Employee>();

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
