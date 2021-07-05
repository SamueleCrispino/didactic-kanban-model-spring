package com.unimib.KanbanBoard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kanban_context")
public class KanbanContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUser")
	private long contextId;
	
	
	@Column(name = "exist_admin")
	private boolean existAdimn;
	
	
	
	public KanbanContext() {
		super();
	}	

	public KanbanContext(boolean existAdimn) {
		super();
		this.existAdimn = existAdimn;
	}

	public boolean isExistAdimn() {
		return existAdimn;
	}

	public void setExistAdimn(boolean existAdimn) {
		this.existAdimn = existAdimn;
	}
	
	

}
