package com.unimib.KanbanBoard.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.unimib.KanbanBoard.dto.LoginInfoDto;
import com.unimib.KanbanBoard.dto.UserInfoDto;
import com.unimib.KanbanBoard.service.ColonnaService;
import com.unimib.KanbanBoard.service.LoginService;
import com.unimib.KanbanBoard.service.UserService;

@CrossOrigin(origins = "http://localhost:8080/")
@Controller
@RequestMapping("/KanbanModel/admin")
public class AdminController {
	
	@Autowired
	private ColonnaService colonnaService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	
	@RequestMapping(value={"/getColonne/{username}"}, method = RequestMethod.POST)
    public ResponseEntity<UserInfoDto> getColonneUser(Authentication authentication, @PathVariable String username){
		
		System.out.println("getColonne");		
		
		
		UserInfoDto userInfoDto = userService.getUserInfoAll(username);
		
		
		
        
        return new ResponseEntity<UserInfoDto>(userInfoDto, HttpStatus.OK);
    }
	
	
	@RequestMapping(value={"/cambiaStatoColonna/{idColonna}/{archiviato}"}, method = RequestMethod.POST)
	public ResponseEntity<String> cambiaStatoColonna(@PathVariable Long idColonna, @PathVariable boolean archiviato){
		
		System.out.println("/cambiaStatoColonna");
		System.out.println(idColonna);
		System.out.println(archiviato);
		
		colonnaService.updateStato(idColonna, archiviato);

		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@RequestMapping(value={"/adminPage"}, method = RequestMethod.GET)
	public String getAdminPage(){
		
		System.out.println("/adminPage");

		return "adminArea";
	}
	
	
	@RequestMapping(value={"/observeUser"}, method = RequestMethod.GET)
    public ResponseEntity<List<LoginInfoDto>> observeUser(Authentication authentication){
		
		System.out.println("observeUser");		
		
		List<LoginInfoDto> dtoList = loginService.findAll();
		
		System.out.println("TS LOGIN");
		dtoList.stream().forEach(x -> System.out.println(x.getTimestampLogin()));
		
        
        return new ResponseEntity<List<LoginInfoDto>>(dtoList, HttpStatus.OK);
    }
	
	@RequestMapping(value={"/eliminaUtente/{userId}/{username}"}, method = RequestMethod.POST)
    public ResponseEntity<String> eliminaUtente(Authentication authentication, @PathVariable Long userId, @PathVariable String username,
    		HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("/eliminaUtente");
		System.out.println(userId);
		System.out.println(username);
		System.out.println(authentication.getName());
		
		if (username.trim().equalsIgnoreCase(authentication.getName().trim()) ) {
			System.out.println("admin elimina admin");

			
			System.out.println("LOGOUT");		
			System.out.println(username);
			
			Cookie cookieWithSlash = new Cookie("JSESSIONID", null);
	        //Tomcat adds extra slash at the end of context path (e.g. "/foo/")
	        cookieWithSlash.setPath(request.getContextPath() + "/"); 
	        cookieWithSlash.setMaxAge(0); 

	        Cookie cookieWithoutSlash = new Cookie("JSESSIONID", null);
	        //JBoss doesn't add extra slash at the end of context path (e.g. "/foo")
	        cookieWithoutSlash.setPath(request.getContextPath()); 
	        cookieWithoutSlash.setMaxAge(0); 

	        //Remove cookies on logout so that invalidSessionURL (session timeout) is not displayed on proper logout event
	        response.addCookie(cookieWithSlash); //For cookie added by Tomcat 
	        response.addCookie(cookieWithoutSlash); //For cookie added by JBoss
	        
			userService.deleteUser(userId);
			userService.deleteContext();
	        
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("signUp");

			
			return new ResponseEntity<String>("adminEliminato", HttpStatus.OK);
			
		} else {
			userService.deleteUser(userId);
			ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("adminArea");
			
	        return new ResponseEntity<String>("utenteEliminato", HttpStatus.OK);
		}
				
    }

}
