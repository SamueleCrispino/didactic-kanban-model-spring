package com.unimib.KanbanBoard.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.unimib.KanbanBoard.model.Colonna;
import com.unimib.KanbanBoard.model.Login;
import com.unimib.KanbanBoard.model.Tile;

public class UserInfoDto {
	
	private static final long serialVersionUID = 1L;


	private String username;


	private String email;

	private Set<Tile> tileSet;
	
	private Set<Colonna> colonneSet;	

	private boolean isAdmin;


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

	public Set<Tile> getTileSet() {
		return tileSet;
	}

	public void setTileSet(Set<Tile> tileSet) {
		this.tileSet = tileSet;
	}

	public Set<Colonna> getColonneSet() {
		return colonneSet;
	}

	public void setColonneSet(Set<Colonna> colonneSet) {
		this.colonneSet = colonneSet;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
