package com.unimib.KanbanBoard.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tile")
public class Tile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idTile")
	private Long idTile;
	
	@NotBlank
	@Column(name="titolo")
	private String titolo;	
	
	@NotBlank
	@Column(name="autore")
	private String autore;
	
	
	@NotBlank
	@Column(name="tipo_messaggio")   // {ORGANIZZATIVO, INFORMATIVO}
	private String tipoMessaggio;
	
	@Column(name="contenuto_testuale")
	private String contenutoTestuale;
	
	@Lob
	@Column(name = "contenuto_multimediale", length = 1000)
	private byte[] contenutoMultimediale;
	
	@JsonBackReference
	@ManyToOne
	private Colonna colonna;

	public Long getIdTile() {
		return idTile;
	}

	public void setIdTile(Long idTile) {
		this.idTile = idTile;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	
	
	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public String getContenutoTestuale() {
		return contenutoTestuale;
	}

	public void setContenutoTestuale(String contenutoTestuale) {
		this.contenutoTestuale = contenutoTestuale;
	}


	public Colonna getColonna() {
		return colonna;
	}

	public void setColonna(Colonna colonna) {
		this.colonna = colonna;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public byte[] getContenutoMultimediale() {
		return contenutoMultimediale;
	}

	public void setContenutoMultimediale(byte[] contenutoMultimediale) {
		this.contenutoMultimediale = contenutoMultimediale;
	}
	
	
	
	
	

}
