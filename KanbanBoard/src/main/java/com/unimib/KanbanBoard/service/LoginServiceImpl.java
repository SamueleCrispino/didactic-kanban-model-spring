package com.unimib.KanbanBoard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimib.KanbanBoard.dto.LoginInfoDto;
import com.unimib.KanbanBoard.model.Login;
import com.unimib.KanbanBoard.repository.LoginDao;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginDao loginDao;

	
	public Date updateLoginTimestamp(String Username) {
		// TODO Auto-generated method stub
		Login login = loginDao.findByUsername(Username);
		
		if ( ! login.getTimestampLogin().after(login.getTimestampLogout())) {
			
			login.setTimestampLogin(new Date());
			
			loginDao.save(login);
		}
		
		
		
		return login.getTimestampLogin();
	}

	@Override
	public Date updateLogoutTimestamp(String Username) {
		// TODO Auto-generated method stub
		
		Login login = loginDao.findByUsername(Username);
		
		login.setTimestampLogout(new Date());
		
		loginDao.save(login);
		
		return login.getTimestampLogout();
	}

	@Override
	public List<LoginInfoDto> findAll() {
		// TODO Auto-generated method stub
		
		List<Login> loginList = loginDao.findAll();
		List<LoginInfoDto> loginInfoDtoList = new ArrayList<LoginInfoDto>();
		
		System.out.println(loginInfoDtoList.size());
		
		loginList.stream().forEach(x -> {
			LoginInfoDto dto = new LoginInfoDto();
			dto.setIdLogin(x.getIdLogin());
			dto.setTimestampCreazione(x.getTimestampCreazione().toString());
			dto.setTimestampLogin(x.getTimestampLogin().toString());
			dto.setTimestampLogout(x.getTimestampLogout().toString());
			dto.setUsername(x.getUsername());
			loginInfoDtoList.add(dto);
		});
		
		System.out.println(loginInfoDtoList.size());
		
		
		return loginInfoDtoList;
	}

}
