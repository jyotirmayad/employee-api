package com.employee.api.controller;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import com.employee.api.model.Employee;
import com.employee.api.model.Goal;
import com.employee.api.model.Leave;

@Stateless
public class LeaveController extends BaseController<Leave> {

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
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Leave create(
			@NotNull(message = ENTITY_VALIDATION) Leave entity)
					throws ConstraintViolationException {

		return super.create(entity);
	}
}
