package com.unimib.KanbanBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unimib.KanbanBoard.model.KanbanContext;


@Repository
public interface KanbanContextDao extends JpaRepository<KanbanContext, Long> {

}
