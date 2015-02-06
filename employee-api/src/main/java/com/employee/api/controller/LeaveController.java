package com.employee.api.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import com.employee.api.model.EmployeeLeaveDate;
import com.employee.api.model.Leave;

/**
 * Class to define the Leave DAO functions to be consumed by leave APIs.
 * 
 * @author Jyoti
 * @version 1.0
 */
@Stateless
public class LeaveController extends BaseController<Leave> {

	/**
	 * Constructor.
	 * 
	 */
	public LeaveController() {
		this.type = Leave.class;
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
	 * Create a leave record
	 * 
	 * @param Leave entity
	 * @return Leave entity
	 * @throws ConstraintViolationException
	 * 
	 */
	@Override
	public Leave create(
			@NotNull(message = ENTITY_VALIDATION) Leave entity)
					throws ConstraintViolationException {

		return super.create(entity);
	}

	/**
	 * Fetch Leave days for employee in a given year.
	 * 
	 * @param employee id
	 * @param year
	 * @return EmployeeLeaveDays List
	 * @throws 
	 * 
	 */
	public List<EmployeeLeaveDate> getLeaveDaysByEmployee(long id, int year) {
		TypedQuery<EmployeeLeaveDate> query = entityManager.createQuery(
				"SELECT ld FROM EmployeeLeaveDate AS ld WHERE ld.employee=" + id + 
				" AND ld.date BETWEEN '" + year + "/01/01' AND '" + year + "/12/31'", EmployeeLeaveDate.class);
		List<EmployeeLeaveDate> leaves = query.getResultList();

		return leaves;
	}

	/**
	 * Fetch Leave days for employee in a given month.
	 * 
	 * @param employee id
	 * @param month
	 * @return EmployeeLeaveDays List
	 * @throws 
	 * 
	 */
	public List<EmployeeLeaveDate> getLeaveDaysByEmployeeMonth(long id, int month,int year) {

		int lastDay = this.getLastDayOfMonth(month, year);
		String firstDate = year + "/" + month + "/01";
		String lastDate = year + "/" + month + "/" + lastDay;

		TypedQuery<EmployeeLeaveDate> query = entityManager.createQuery(
				"SELECT ld FROM EmployeeLeaveDate AS ld WHERE ld.employee=" + id + 
				" AND ld.date BETWEEN '" + firstDate + "' AND '" + lastDate + "'", EmployeeLeaveDate.class);
		List<EmployeeLeaveDate> leaves = query.getResultList();

		return leaves;
	}

	/**
	 * Fetch Leave requests for employee in a given year.
	 * 
	 * @param employee id
	 * @param year
	 * @return Leave List
	 * @throws 
	 * 
	 */
	public List<Leave> getLeaveRequestsByEmployee(long id, int year) {
		TypedQuery<Leave> query = entityManager.createQuery(
				"SELECT l FROM Leave AS l WHERE l.employee=" + id + 
				" AND l.createdAt BETWEEN '" + year + "/01/01' AND '" + year + "/12/31'", Leave.class);
		List<Leave> leaves = query.getResultList();

		return leaves;
	}

	/**
	 * Fetch Leave detail by id.
	 * 
	 * @param Leave id
	 * @return Leave
	 * @throws 
	 * 
	 */
	public Leave findById(long id) {

		Leave entity = entityManager.find(type, id);
		return entity;
	}

	/**
	 * Update Leave
	 * @param entity Leave
	 * @return Leave
	 */
	@Override
	public Leave update(
			@NotNull(message = ENTITY_VALIDATION) Leave entity)
					throws ConstraintViolationException, IllegalArgumentException {

		return super.update(entity);
	}

	/**
	 * To get the Last day of a month.
	 * 
	 * @param month number
	 * @param year
	 * @return integer last day of the month
	 * @throws 
	 * 
	 */
	public int getLastDayOfMonth(int month, int year) {

		int mapLastDay = 0;

		switch (month) {
		case 1: // fall through
		case 3: // fall through
		case 5: // fall through
		case 7: // fall through
		case 8: // fall through
		case 10: // fall through
		case 12:
			mapLastDay = 31;
			break;
		case 4: // fall through
		case 6: // fall through
		case 9: // fall through
		case 11:
			mapLastDay = 30;
			break;
		case 2:
			if (0 == year % 4 && 0 != year % 100 || 0 == year % 400)
			{
				mapLastDay = 29;
			}
			else
			{
				mapLastDay = 28;
			}
			break;
		}
		return mapLastDay;
	}
}
