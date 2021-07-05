package com.unimib.KanbanBoard.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "colonna")
public class Colonna implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idColonna")
	private Long idColonna;
	
	@NotBlank
	@Column(name="titolo")
	private String titolo;
	
	@JsonBackReference
	@ManyToOne()
	@JoinColumn(name="idUser", referencedColumnName = "idUser")
	private User autore;
	
	@Column(name = "isArchiviato")
	private boolean isArchiviato; 
	
	@JsonManagedReference
	@OneToMany(mappedBy="colonna", cascade = CascadeType.ALL)
	private Set<Tile> tileSet;

	public Long getIdColonna() {
		return idColonna;
	}

	public void setIdColonna(Long idColonna) {
		this.idColonna = idColonna;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public User getAutore() {
		return autore;
	}

	public void setAutore(User autore) {
		this.autore = autore;
	}

	public Set<Tile> getTileSet() {
		return tileSet;
	}

	public void setTileSet(Set<Tile> tileSet) {
		this.tileSet = tileSet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isArchiviato() {
		return isArchiviato;
	}

	public void setArchiviato(boolean isArchiviato) {
		this.isArchiviato = isArchiviato;
	}
	
	
	
	

}
