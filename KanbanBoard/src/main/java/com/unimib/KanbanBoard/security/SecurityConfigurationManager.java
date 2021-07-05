package com.unimib.KanbanBoard.security;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.unimib.KanbanBoard.repository.LoginRepository;
import com.unimib.KanbanBoard.service.MyUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfigurationManager extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	@Qualifier("persistentTokenRepository")
	private PersistentTokenRepository persistentTokenRepository;


	@Autowired
	private MyUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	};



	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}

	private static final String[] adminExclusivePaths = {
			"/KanbanModel/admin/**",
			"/KanbanModel/admin",
			"/KanbanModel/adminPage",
	};

	private static final String[] userExclusivePaths = {
			"/KanbanModel/createColumn",
			"/KanbanModel/userInfo",
			"/KanbanModel/userInfo/**",
			"/KanbanModel/deleteColumn",
			"/KanbanModel/createTile",
			"/KanbanModel/deleteTile",
			"/KanbanModel/updateTile",
			"/KanbanModel/goToArchiviate",
			"/KanbanModel/userInfoArchiviate",
			"/KanbanModel/uploadTitoloColonna/",
			"/KanbanModel/uploadTitoloColonna/**",

	};

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http
		.authorizeRequests()
		.antMatchers("/KanbanModel/login").permitAll()
		.antMatchers("/KanbanModel/saveUser").permitAll()
		.antMatchers("/KanbanModel/prova").permitAll()
		.antMatchers("/KanbanModel/success").permitAll()
		.antMatchers("/KanbanModel/error").permitAll()
		.antMatchers(userExclusivePaths).access("hasRole('false') or hasRole('true')")
		.antMatchers(adminExclusivePaths).access("hasRole('true')")
		.antMatchers("/").permitAll().anyRequest()			
		.authenticated()
		.and()
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.formLogin() // form che appare se si accede a path non auth
		.loginPage("/KanbanModel/login").permitAll() // url del form di login (in realtà controller)
		.loginProcessingUrl("/KanbanModel/prova").permitAll() // 
		.failureUrl("/KanbanModel/error").permitAll()
		.defaultSuccessUrl("/KanbanModel/success", true).permitAll() // true permette di venire sempre rediretti in caso di successo
		.usernameParameter("username")
		.passwordParameter("password")

		.and()
		.exceptionHandling()
		.accessDeniedPage("/KanbanModel/error")
		.and()
		.csrf().disable();

		/*
		 * .loginPage("/KanbanModel/login").failureUrl("/login?error=true")
					.defaultSuccessUrl("/admin/home")
					.usernameParameter("username")
					.passwordParameter("password")
		 */

	}

	public Filter authenticationFilter() throws Exception {
		System.out.println("authenticationFilter");
		// TODO Auto-generated method stub
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationFailureHandler(failureHandler());
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		filter.setRememberMeServices(customRememberMeService());


		return filter;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices customRememberMeService() {
		System.out.println("customRememberMeService");
		// TODO Auto-generated method stub
		String key = "ricordiSbocciavanLeVioleConLeNostreParoleNonCiLascieremoMaiMaiEPoiMai";

		PersistentTokenBasedRememberMeServices 
		rememberMeServices = new CustomRememberMeServices(key,
				userDetailsService,
				persistentTokenRepository);

		// hai un'ora, non deludermi.
		rememberMeServices.setTokenValiditySeconds(60 * 60 );
		rememberMeServices.setParameter("ricordami");
		// Frank della sicurezza non sarà d'accordo
		rememberMeServices.setUseSecureCookie(false); 

		return rememberMeServices;

	}

	/*

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {

		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
	}

	 */

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
		System.out.println("authenticationSuccessHandler");
		// TODO Auto-generated method stub
		SavedRequestAwareAuthenticationSuccessHandler auth = 
				new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth ;
	}

	public AuthenticationFailureHandler failureHandler() {
		// TODO Auto-generated method stub
		System.out.println("failureHandler");
		return new SimpleUrlAuthenticationFailureHandler("/KanbanModel/error");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/resources/**", "/templates/", 
				"/static/**", 
				"/css/**", 
				"/javascript/**",
				"/images/**",
				"/templates/**",
				"/KanbanBoard/src/main/resources/static/css/signUpStyle.css",
				"/js/**", "/img/**", "/json/**");
	}

}
