package com.unimib.KanbanBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unimib.KanbanBoard.model.Colonna;

@Repository
public interface ColonnaDao extends JpaRepository<Colonna, Long> {
	
	public Colonna findByTitolo(String titolo);

}
