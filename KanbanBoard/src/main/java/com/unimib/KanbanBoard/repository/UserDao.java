package com.unimib.KanbanBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unimib.KanbanBoard.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	
	 User findByEmail(String email);
	 
	 User findByUsername(String userName);

}
