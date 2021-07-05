package com.unimib.KanbanBoard.model;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;





@Entity
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUser")
	private Long idUser;

	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Login login;

	@NotBlank
	@Column(name="username", unique = true)
	private String username;

	@NotBlank
	@Column(name="email", unique = true)
	private String email;
	
	
	@JsonManagedReference
	@OneToMany(mappedBy="autore", cascade = CascadeType.ALL)
	private Set<Colonna> colonneSet;
	
	@NotNull
	@Column(name="is_admin")
	private boolean isAdmin;
	
	

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public Set<Colonna> getColonneSet() {
		return colonneSet;
	}

	public void setColonneSet(Set<Colonna> colonneSet) {
		this.colonneSet = colonneSet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", login=" + login + ", username=" + username + ", email=" + email
				+ ", colonneSet=" + colonneSet + ", isAdmin=" + isAdmin + "]";
	}
	
	
	


	
	
	

	

}
