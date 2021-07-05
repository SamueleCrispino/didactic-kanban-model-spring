package com.unimib.KanbanBoard.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unimib.KanbanBoard.dto.SignUpDto;
import com.unimib.KanbanBoard.dto.TileDto;
import com.unimib.KanbanBoard.dto.UserInfoDto;
import com.unimib.KanbanBoard.model.User;
import com.unimib.KanbanBoard.repository.UserDao;
import com.unimib.KanbanBoard.service.ColonnaService;
import com.unimib.KanbanBoard.service.LoginService;
import com.unimib.KanbanBoard.service.TileService;
import com.unimib.KanbanBoard.service.UserService;

@CrossOrigin(origins = "http://localhost:8080/")
@Controller
@RequestMapping("/KanbanModel")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ColonnaService colonnaService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private TileService tileService;
	

	@Autowired
	@Qualifier("persistentTokenRepository")
	private PersistentTokenRepository persistentTokenRepository;

	private final static int MAX_DIMENSION = 810000;
	
	
	@RequestMapping(value={"/cancellaUtente"}, method = RequestMethod.POST)
	public String cancellaUtente(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
		System.out.println("/eliminaUtente");
		
		String username = authentication.getName();
		
		System.out.println(username);
		
		User user = userService.findUserByUsername(username);

		
		if (user.isAdmin()) {
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
	        
	        
	        
			userService.deleteUser(user.getIdUser());
			userService.deleteContext();
	        
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("signUp");
	        
	       

			
			return "signUp";
			
		} else {
			userService.deleteUser(user.getIdUser());
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("signUp");
			
	        return "signUp";
		}
				
    }
	
	@RequestMapping(value={"/updateTile/{tileId}"}, method = RequestMethod.POST)
	public String updateTile(Authentication authentication, @PathVariable Long tileId, @RequestParam Long colonnaId ){
		
		System.out.println(tileId);
		System.out.println(colonnaId);
		
		tileService.updateTile(tileId, colonnaId, authentication.getName());
		
		
		return "signUp";
	}


	// @RequestParam("radioOrg") String radioOrg,
	// titolo, testo, radioOrg, radioInfo, file
	@RequestMapping(value={"/createTile"}, method = RequestMethod.POST, headers = ("content-type=multipart/*"),
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String createTile( 
			Authentication authentication,
			
			@RequestParam("file") MultipartFile file,
			@RequestParam("tipoMessaggio") String tipoMessaggio,

			@RequestParam("testo") String testo,
			@RequestParam("titolo") String titolo,
			@RequestParam("colonna") Long colonna
			){

		System.out.println("/createTile");		
		System.out.println(tipoMessaggio);	
		//System.out.println(radioOrg);	
		System.out.println(testo);	
		System.out.println(titolo);	
		System.out.println(colonna);

		System.out.println("dimensione file");
		System.out.println(file.getSize());
		
		TileDto tileDto = new TileDto();

		if (! file.isEmpty()) {
			try {
				byte[] image = file.getBytes();

				InputStream is = new ByteArrayInputStream(image);
				BufferedImage newBi = ImageIO.read(is);
				
				int width          = newBi.getWidth();
				int height         = newBi.getHeight();

				System.out.println(width);
				System.out.println(height);


				if (width * height > MAX_DIMENSION) {
					BufferedImage resizedImage = new BufferedImage(900, 900, BufferedImage.TYPE_INT_RGB);
					Graphics2D graphics2D = resizedImage.createGraphics();
					graphics2D.drawImage(newBi, 0, 0, 900, 900, null);
					graphics2D.dispose();
					
					System.out.println(resizedImage.getWidth());
					System.out.println(resizedImage.getHeight());
				}
				
				tileDto.setContenutoMultimediale(image);
				tileDto.setTesto(null);

			} catch (Exception e) {
				e.printStackTrace();
				return "error";

			}			
		} else {
			tileDto.setTesto(testo);
			tileDto.setContenutoMultimediale(null);
		}
		
		String username = authentication.getName();

		
		
		tileDto.setIdColonna(colonna);
		
		tileDto.setTipoMessaggio(tipoMessaggio);
		tileDto.setTitolo(titolo);


		colonnaService.createTile(username, tileDto);



		return "error";
	}

	@RequestMapping(value={"/deleteTile/{tileId}"}, method = RequestMethod.POST)
	public ResponseEntity<String> deleteTile(Authentication authentication, @PathVariable Long tileId ){

		System.out.println("/deleteTile");
		System.out.println(tileId);

		tileService.deleteTile(tileId);


		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}



	@RequestMapping(value={"/deleteColumn/{colonnaId}"}, method = RequestMethod.POST)
	public ResponseEntity<String> deleteColumn(Authentication authentication, @PathVariable Long colonnaId ){

		System.out.println("/deleteColumn");
		System.out.println(colonnaId);

		colonnaService.deleteColumn(colonnaId);


		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(value={"/uploadTitoloColonna/{idColonna}/{nuovoNome}"}, method = RequestMethod.POST)
	public ResponseEntity<String> uploadTitoloColonna(Authentication authentication, 
			@PathVariable Long idColonna,
			@PathVariable String nuovoNome){

		System.out.println("/uploadTitoloColonna");
		System.out.println(idColonna);
		System.out.println(nuovoNome);

		String username = authentication.getName();

		if (colonnaService.updateTitle(idColonna, nuovoNome) == null) {
			return new ResponseEntity<String>("Nome Già Presente", HttpStatus.OK);
		}



		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}


	@RequestMapping(value={"/createColumn/{nomeColonna}"}, method = RequestMethod.POST)
	public ResponseEntity<String> createColumn(Authentication authentication, @PathVariable String nomeColonna ){

		System.out.println("/createColumn");

		String username = authentication.getName();

		// viene visualizzata alla refresh della pagina
		colonnaService.createColonna(nomeColonna, username);	



		return new ResponseEntity<String>("OK", HttpStatus.CREATED);
	}

	@RequestMapping(value={"/userInfo"}, method = RequestMethod.GET)
	public ResponseEntity<UserInfoDto> getUserInfo(Authentication authentication){

		System.out.println("getUserInfo");		

		// TODO must be DTO
		UserInfoDto userInfoDto = userService.getUserInfo(authentication.getName());


		return new ResponseEntity<UserInfoDto>(userInfoDto, HttpStatus.OK);
	}
	
	@RequestMapping(value={"/userInfoArchiviate"}, method = RequestMethod.GET)
	public ResponseEntity<UserInfoDto> getUserInfoArchiviate(Authentication authentication){

		System.out.println("getUserInfoArchiviate");		

		// TODO must be DTO
		UserInfoDto userInfoDto = userService.getUserInfoArchiviate(authentication.getName());


		return new ResponseEntity<UserInfoDto>(userInfoDto, HttpStatus.OK);
	}

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		System.out.println("LOGIN");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signUp");
		return modelAndView;
	}
	
	@RequestMapping(value={"/goToArchiviate"}, method = RequestMethod.GET)
	public String getAdminPage(){
		
		System.out.println("/adminPage");

		return "archiviate";
	}

	@GetMapping("/logout")
	public ModelAndView logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {

		String username =  authentication.getName();

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

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signUp");

		// save logout timestamp
		loginService.updateLogoutTimestamp(username);

		return modelAndView;
	}



	@RequestMapping(value={"/success"}, method = RequestMethod.GET)
	public ModelAndView successLogin(Authentication authentication){
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("TENTATIVO LOGIN IN CORSO");
		if (authentication != null) {
			System.out.println("LOGIN SUCCESS");
			String username =  authentication.getName();
			System.out.println(username);

			System.out.println("LOGIN SUCCESS at " + loginService.updateLoginTimestamp(username));

			modelAndView.setViewName("personalPage");
		} else {
			System.out.println("NON DOVRESTI ESSERE QUI");
			modelAndView.setViewName("signUp");
		}



		return modelAndView;
	}

	@RequestMapping(value={"/error"}, method = RequestMethod.GET)
	public ModelAndView failedLogin(){
		System.out.println("ERROR");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signUp");
		return modelAndView;
	}

	@PostMapping(path = "/saveUser")
	public ResponseEntity<String> createUser(@RequestBody @Validated SignUpDto signUpDto) {

		try {
			// return new ResponseEntity<String>("creato utente " + userService.saveUser(signUpDto), HttpStatus.CREATED);
			return new ResponseEntity<String>("creato utente " + userService.saveUser(signUpDto), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			//return new ResponseEntity<String>(""Username o email gia presente nel sistema"", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>("Username o email gia presente nel sistema", HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			//return new ResponseEntity<String>("Failed", HttpStatus.OK);
			return new ResponseEntity<String>("Failed", HttpStatus.OK);
		}
	} 
}
