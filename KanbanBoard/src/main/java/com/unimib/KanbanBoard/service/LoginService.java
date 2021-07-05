package com.unimib.KanbanBoard.service;

import java.util.Date;
import java.util.List;

import com.unimib.KanbanBoard.dto.LoginInfoDto;

public interface LoginService {
	
	public abstract Date updateLoginTimestamp(String Username);
	
	public abstract Date updateLogoutTimestamp(String Username);
	
	public abstract List<LoginInfoDto> findAll();
}
