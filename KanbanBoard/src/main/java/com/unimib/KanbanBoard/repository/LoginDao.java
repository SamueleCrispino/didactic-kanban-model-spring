package com.unimib.KanbanBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unimib.KanbanBoard.model.Login;
import com.unimib.KanbanBoard.model.User;

@Repository
public interface LoginDao extends JpaRepository<Login, Long>  {
	
	Login findByUsername(String userName);
	
	
}
