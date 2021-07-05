package com.unimib.KanbanBoard.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unimib.KanbanBoard.model.Login;

@Repository("persistentTokenRepository")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
public class LoginRepository implements PersistentTokenRepository {
	
	@PersistenceContext
	protected EntityManager entityManager;

		@Override
	public void createNewToken(PersistentRememberMeToken token) {
		// TODO Auto-generated method stub
		Login login = new Login();
		login.setUsername(token.getUsername());
		login.setTimestampLogin(token.getDate());
		// login.setSerie(token.getSeries());
		
		this.entityManager.persist(login);
		flushAndClear();
		
	}

	private void flushAndClear() {
			// TODO Auto-generated method stub
		entityManager.flush();
		entityManager.clear();
	}

	@Override
	public void updateToken(String username, String tokenValue, Date lastUsed) {
		// TODO Auto-generated method stub
		String JPQL = "SELECT a FROM Login a WHERE a.username = :username ";

		Login login = (Login) entityManager.createQuery(JPQL)
				   .setParameter("username", username)
				   .getSingleResult();
		
		login.setToken(tokenValue);
		login.setTimestampLogin(lastUsed);
		
		this.entityManager.merge(login); 
		flushAndClear();
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		// TODO Auto-generated method stub
	String JPQL = "SELECT a FROM Login a WHERE a.serie = :series ";
		 
		
		Login login = (Login) entityManager.createQuery(JPQL)
						   .setParameter("series", seriesId)
						   .getSingleResult();
		 
		if (login != null) 
		{
		      return new PersistentRememberMeToken(
		    		  login.getUsername(), 
		    		  login.getSerie(), 
		    		  login.getToken(),
		    		  login.getTimestampLogin());
		}
		
		return null;
	}

	@Override
	public void removeUserTokens(String username) {
		// TODO Auto-generated method stub
		{
			String JPQL = "delete from Login where username = :username";

			entityManager
				.createQuery(JPQL)
				.setParameter("username", username)
				.executeUpdate();
					
			flushAndClear(); 
			
		}
		
	}

	

}
