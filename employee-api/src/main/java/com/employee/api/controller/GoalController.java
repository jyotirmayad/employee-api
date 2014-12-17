package com.employee.api.controller;

import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import com.employee.api.model.Goal;

@Stateless
public class GoalController  extends BaseController<Goal> {
	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Goal create(
			@NotNull(message = ENTITY_VALIDATION) Goal entity)
					throws ConstraintViolationException {

		return super.create(entity);
	}
}
