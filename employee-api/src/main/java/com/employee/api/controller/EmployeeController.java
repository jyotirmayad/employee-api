package com.employee.api.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.employee.api.model.Employee;
import com.employee.api.model.Goal;
import com.employee.api.model.Leave;


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
		//user.getGoals().size();
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
		entity.setEmp_no(null);
		entity = super.create(entity);
		entity.setEmp_no(this.calculateEmpNo(entity.getId()));
		return this.update(entity);
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

		Employee employee = super.find(entity.getId());
		employee.getGoals().size();
		entity.setPassword(employee.passwordVal());
		entity.setEmp_no(employee.getEmp_no());
		entity.setGoals(employee.getGoals());
		
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

		// Lazy load of skills
		user.getSkills().size();
		user.getGoals().size();

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

	/**
	 *
	 * @param Long
	 * @return String
	 */
	private String calculateEmpNo(Long id) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		return dateFormat.format(date) + "-" + id;

	}
	
	/**
	 *
	 * @param Long
	 * @return integer
	 */
	public int getEmpPresentStatusForToday(Long id) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(id) FROM Leave AS el WHERE '" + currentDate +
				"' BETWEEN el.fromDate AND el.toDate", Long.class);
		
		//RETURN 2 IF ON LEAVE, 1 IF PRESENT
		if ( query.getSingleResult() > 0) {
			return 2;
		}
		else {
			return 1;
		}
	}
	
	/**
	 *
	 * @param Long
	 * @return integer
	 */
	public Long checkForOverlapDateInLeaveRequest(Long id, String startDate, String endDate) {
		
		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(id) FROM Leave AS el WHERE '" + startDate + "' <= el.toDate "
						+ "AND '" + endDate + "' >= el.fromDate", Long.class);
		
		//RETURN 
		return query.getSingleResult();

	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public Employee findEmployeeGoals(@Min(value = 1, message = ID_NOT_NULL_VALIDATION) long id)
			throws ConstraintViolationException, NoResultException {


		Employee user = super.find(id);

		// Lazy load of skills
		//user.getSkills().size();
		user.getGoals().size();
		return user;
	}
}
