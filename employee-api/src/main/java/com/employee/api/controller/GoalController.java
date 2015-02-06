package com.employee.api.controller;


import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import com.employee.api.model.Goal;

@Stateless
public class GoalController extends BaseController<Goal> {
	
	public GoalController() {
		this.type = Goal.class;
	}
	
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
	
	public List<Goal> getGoalsByEmployeeDate(Long id, Date date) {

		TypedQuery<Goal> query = entityManager.createQuery(
				"SELECT g FROM Goal AS g WHERE g.employee=" + id + " AND g.date=:date", Goal.class)
				.setParameter("date", date, TemporalType.DATE);
		List<Goal> goals = query.getResultList();

		return goals;


	}
	
	/**
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Goal update(
			@NotNull(message = ENTITY_VALIDATION) Goal entity)
					throws ConstraintViolationException, IllegalArgumentException {

		Goal goal = super.find(entity.getId());
		goal.setStatus(entity.getStatus());
		return goal;
	}
}
