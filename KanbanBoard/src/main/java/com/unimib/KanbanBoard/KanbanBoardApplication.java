package com.unimib.KanbanBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//Entry Point
@SpringBootApplication
@EnableJpaAuditing
public class KanbanBoardApplication {

	public static void main(String[] args) {
	
		SpringApplication.run(KanbanBoardApplication.class, args);
	}

}
