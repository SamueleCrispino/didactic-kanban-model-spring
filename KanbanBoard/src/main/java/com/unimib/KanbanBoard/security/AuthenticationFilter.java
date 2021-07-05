package com.unimib.KanbanBoard.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		if (! request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("HTTP VERB VERY SBAGLIATO: " + request.getMethod());
		}
		
		UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
		
		System.out.println("token -> " + authRequest);
		
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		username = (username == null) ? "" : username;
		password = (password == null) ? "" : password;
		
		
		
		
		return new UsernamePasswordAuthenticationToken(username, password);
	}

}
