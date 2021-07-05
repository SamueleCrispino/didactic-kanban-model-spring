package com.unimib.KanbanBoard.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unimib.KanbanBoard.model.User;

public class LoginInfoDto {

	private Long idLogin;

	private String username;

	private String timestampCreazione;

	private String timestampLogin;

	private String timestampLogout;

	public Long getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Long idLogin) {
		this.idLogin = idLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimestampCreazione() {
		return timestampCreazione;
	}

	public void setTimestampCreazione(String timestampCreazione) {
		this.timestampCreazione = timestampCreazione;
	}

	public String getTimestampLogin() {
		return timestampLogin;
	}

	public void setTimestampLogin(String timestampLogin) {
		this.timestampLogin = timestampLogin;
	}

	public String getTimestampLogout() {
		return timestampLogout;
	}

	public void setTimestampLogout(String timestampLogout) {
		this.timestampLogout = timestampLogout;
	}

}