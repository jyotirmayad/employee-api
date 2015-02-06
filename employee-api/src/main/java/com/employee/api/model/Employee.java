package com.employee.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.employee.api.util.JSonViews;
import com.employee.api.util.NullCollectionSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * @author 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "EMPLOYEE")
public class Employee extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1106280434818908095L;

	@JsonIgnore
	@Column(name = "EMP_NO", unique = true)
	private String emp_no = null;

	@NotNull
	@Size(min = 3)
	@Column(name = "ROLE")
	private String role = "";

	@NotNull
	@Size(min = 3)
	@Column(name = "FIRST_NAME")
	private String first_name = "";

	@NotNull
	@Size(min = 3)
	@Column(name = "LAST_NAME")
	private String last_name = "";

	@Column(name = "MIDDLE_NAME")
	private String middle_name = "";

	@NotNull
	@Column(name = "DOB")
	private Long dob;

	@NotNull
	@Column(name = "GENDER")
	private String gender = "";

	@NotNull
	@Column(name = "DOJ")
	private Long doj;

	@NotNull
	@Column(name = "STATUS")
	private String status = "";

	@NotNull
	@Size(min = 3)
	@Column(name = "EMAIL", unique = true)
	private String email = "";

	@NotNull
	@Size(min = 3)
	@Column(name = "PASSWORD")
	private String password = "";

	@JsonView(JSonViews.EntityView.class)
	@JsonSerialize(using = NullCollectionSerializer.class)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "EMPLOYEE_SKILL", joinColumns = { 
			@JoinColumn(name = "EMPLOYEE_ID", nullable = true, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "SKILL_ID", 
			nullable = true, updatable = false) })
	private List<Skill> skills = new ArrayList<Skill>();
	
	@JsonIgnore
    @JsonSerialize(using = NullCollectionSerializer.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Goal> goals = new ArrayList<Goal>();
	
	@JsonIgnore
    @JsonSerialize(using = NullCollectionSerializer.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Leave> leaves = new ArrayList<Leave>();
	
	@JsonIgnore
    @JsonSerialize(using = NullCollectionSerializer.class)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<EmployeeLeaveDate> employeeLeaveDates = new ArrayList<EmployeeLeaveDate>();

	/**
	 *
	 */
	public Employee() {}

	/**
	 *
	 */
	public Employee(Long id, String emp_no, String role) {
		this.id = id;
		this.emp_no = emp_no;
		this.role = role;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getDoj() {
		return doj;
	}

	public void setDoj(Long doj) {
		this.doj = doj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String passwordVal() {
		return password;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public List<EmployeeLeaveDate> getEmployeeLeaveDates() {
		return employeeLeaveDates;
	}

	public void setEmployeeLeaveDates(List<EmployeeLeaveDate> employeeLeaveDates) {
		this.employeeLeaveDates = employeeLeaveDates;
	}

}
