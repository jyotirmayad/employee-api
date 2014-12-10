package com.employee.api.controller;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.employee.api.model.Employee;

@Stateless
public class EmployeeController extends BaseController<Employee> {
	public EmployeeController() {
		this.type = Employee.class;
	}

	/**
	 *
	 */
	@Inject
	protected Logger log;

	/**
	 *
	 */
	@Inject
	protected EntityManager entityManager;

	/**
	 *
	 */
	@Inject
	protected Validator validator;
	
	/**
	 *
	 * @param String
	 * @param String
	 * @return
	 */
	public Employee findEmployeeByUsernamePassword(String userName, String password)
			throws ConstraintViolationException, NoResultException {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Employee> q = cb.createQuery(Employee.class);

		Root<Employee> entity = q.from(Employee.class);
		
		q.select(cb.construct(Employee.class,
				entity.get("id"),
				entity.get("emp_no"),
				entity.get("role")));

		q.select(entity).where(cb.and(
				cb.equal(entity.get("email"), userName),
				cb.equal(entity.get("password"), password)));

		Employee user = entityManager.createQuery(q).getSingleResult();

		return user;
	}
}
