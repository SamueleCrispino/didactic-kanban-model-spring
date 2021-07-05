package com.unimib.KanbanBoard.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unimib.KanbanBoard.model.Login;
import com.unimib.KanbanBoard.model.User;
import com.unimib.KanbanBoard.repository.LoginDao;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private LoginDao loginDao;
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		 User user = userService.findUserByUsername(userName);
		 
		 
		 if (user == null) {
			 throw new UsernameNotFoundException("username non trovato");
		 }
		 
		 Login login = loginDao.findByUsername(userName);
		 
		 //System.out.println(username);
		 //System.out.println(login.toString());
		 
		 String username = user.getUsername();
		 String password = login.getPassword();
		 
		 String role = "ROLE_" + String.valueOf(user.isAdmin());
		 Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		 
		 SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
		 
		 roles.add(sga);
		 
		 List<GrantedAuthority> authorities = new ArrayList<>(roles);
		 
		 
		
		
		return buildUserForAuthentication(username, password, authorities);
	}
	
	private UserDetails buildUserForAuthentication(String username, String password, List<GrantedAuthority> authorities) {
        
		return new org.springframework.security.core.userdetails.User(username, password,
                true, true, true, true, authorities);
    }
	
	

}
