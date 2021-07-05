package com.unimib.KanbanBoard.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "login")
@EntityListeners(AuditingEntityListener.class)
// This annotation is used because we don’t want the clients of the rest api
// to supply the createdAt and updatedAt values
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Login implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLogin")
	private Long idLogin;
	
	@Column(name="serie")
	private String serie;
	
	@JsonBackReference
	@OneToOne(mappedBy = "login")
	private User user;
	
	@NotBlank
	@Column(name="username", unique = true)
	private String username;
	
	@NotBlank
	@Column(name="password")
	private String password;
	
	@Column(nullable = false, updatable = false, name="timestamp_creazione")
	@JsonFormat(timezone = "Europe/Rome", pattern = "yyyy-MM-dd'T'HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date timestampCreazione;

	@Column(nullable = false, name="timestamp_login")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "Europe/Rome", pattern = "yyyy-MM-dd'T'HH:mm")
	private Date timestampLogin;
	
	@Column(name="timestamp_logout")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "Europe/Rome", pattern = "yyyy-MM-dd'T'HH:mm")
	private Date timestampLogout;
	
	@Column(name = "token")
	private String token;

	public Long getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Long idLogin) {
		this.idLogin = idLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getTimestampCreazione() {
		return timestampCreazione;
	}

	public void setTimestampCreazione(Date timestampCreazione) {
		this.timestampCreazione = timestampCreazione;
	}

	public Date getTimestampLogin() {
		return timestampLogin;
	}

	public void setTimestampLogin(Date timestampLogin) {
		this.timestampLogin = timestampLogin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	

	public Date getTimestampLogout() {
		return timestampLogout;
	}

	public void setTimestampLogout(Date timestampLogout) {
		this.timestampLogout = timestampLogout;
	}

	@Override
	public String toString() {
		return "Login [idLogin=" + idLogin + ", serie=" + serie + ", user=" + user + ", username=" + username
				+ ", password=" + password + ", timestampCreazione=" + timestampCreazione + ", timestampLogin="
				+ timestampLogin + ", token=" + token + "]";
	}
	
	


	
	

}
