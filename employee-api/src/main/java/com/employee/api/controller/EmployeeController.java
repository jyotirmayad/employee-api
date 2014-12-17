package com.employee.api.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	 * @param id
	 * @return
	 */
	@Override
	public Employee find(
			@Min(value = 1, message = ID_NOT_NULL_VALIDATION) long id)
					throws ConstraintViolationException, NoResultException {

		Employee user = super.find(id);

		// Lazy load of skills
		user.getSkills().size();
		user.getGoals().size();
		return user;
	}

	
	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Employee create(
			@NotNull(message = ENTITY_VALIDATION) Employee entity)
					throws ConstraintViolationException {

		return super.create(entity);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Employee update(
			@NotNull(message = ENTITY_VALIDATION) Employee entity)
					throws ConstraintViolationException, IllegalArgumentException {

		TypedQuery<String> query = entityManager.createQuery(
				"SELECT e.password FROM Employee AS e WHERE e.id=" + entity.getId(), String.class);
		String password = query.getSingleResult();
		entity.setPassword(password);
		return super.update(entity);
	}
	
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
		user.getSkills().size();

		return user;
	}
	
	/**
	 *
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<Employee> list(
			@Min(value = 0, message = START_PAGE_VALIDATION) int startPage,
			@Min(value = 1, message = PAGE_SIZE_VALIDATION) int pageSize)
					throws ConstraintViolationException, NoResultException {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> q = cb.createQuery(type);
		Root<Employee> entity = q.from(type);

		q.select(cb.construct(type,
				entity.get("id"),
				entity.get("emp_no"),
				entity.get("role")));

		q.orderBy(cb.desc(entity.get("lastModified")));

		return entityManager.createQuery(q)
				.setFirstResult(startPage * pageSize)
				.setMaxResults(pageSize)
				.getResultList();

	}
}
