package com.unimib.KanbanBoard.service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.unimib.KanbanBoard.dto.SignUpDto;
import com.unimib.KanbanBoard.dto.UserInfoDto;
import com.unimib.KanbanBoard.model.KanbanContext;
import com.unimib.KanbanBoard.model.User;

public interface UserService {
	
	public abstract String saveUser(SignUpDto signUpDto) throws Exception;
	
	public abstract User findUserByUsername(String username);
	
	public abstract UserInfoDto getUserInfo(String username);
	
	public abstract UserInfoDto getUserInfoArchiviate(String username);
	
	public boolean deleteUser(Long userId);
	
	public boolean deleteContext();
	
	public abstract UserInfoDto getUserInfoAll(String username);
	
	
	

}
