/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.employee.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author 
 */
@javax.persistence.MappedSuperclass
public abstract class Entity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -73112170881659955L;

	// Guarantee unique id for all entities

	/**
	 *
	 */
	@Id
	@SequenceGenerator(name = "EntitySequence", sequenceName = "ENTITY_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EntitySequence")
	@Column(name = "ID")
	protected Long id;

	@NotNull
	@Column(name = "LAST_MODIFIED")
	private Long lastModified = System.currentTimeMillis();

	@JsonIgnore
	@Column(name = "DATE_CREATED", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", 
	updatable=false)
	private Long createdAt;

	// TODO: store class name from subclass in an ENTITY table
	// @NotNull
	@Column(name = "ENTITY_CLASS")
	private String entityClass;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Entity)) {
			return false;
		}
		Entity other = (Entity) object;
		if ((this.id == null && other.getId() != null)
				|| (this.id != null && !this.getId().equals(other.getId()))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().toString() + "[ id=" + id + " ]";
	}

	/**
	 *
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PreUpdate
	void updateModificationTimestamp() {
		lastModified = System.currentTimeMillis();
	}

	public Long getCreatedAt() {
		return createdAt;
	}

}
