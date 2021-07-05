package com.unimib.KanbanBoard.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class CustomRememberMeServices extends PersistentTokenBasedRememberMeServices {

	public CustomRememberMeServices(String key, UserDetailsService userDetailsService,
			PersistentTokenRepository tokenRepository) {
		
		super(key, userDetailsService, tokenRepository);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean remeberMeRequested(HttpServletRequest request, String parameter) {
		System.out.println("remeberMeRequested");
		String isRegularLogin = request.getParameter("isRegularLogin");
		
		if (isRegularLogin != null && "true".equals(isRegularLogin)) {
			
			return super.rememberMeRequested(request, parameter);
		} else {
			return true;
		}
		
	}
	
}
