package com.unimib.KanbanBoard.dto;

import java.sql.Blob;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.unimib.KanbanBoard.model.Colonna;
import com.unimib.KanbanBoard.model.User;

public class TileDto {
	
	
	private String titolo;	

	
	private String tipoMessaggio;	
	
	private String testo;	
	

	private byte[] contenutoMultimediale;
	
	private Long idColonna;

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	
	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Long getIdColonna() {
		return idColonna;
	}

	public void setIdColonna(Long idColonna) {
		this.idColonna = idColonna;
	}

	



	public byte[] getContenutoMultimediale() {
		return contenutoMultimediale;
	}

	public void setContenutoMultimediale(byte[] contenutoMultimediale) {
		this.contenutoMultimediale = contenutoMultimediale;
	}

	@Override
	public String toString() {
		return "TileDto [titolo=" + titolo + ", tipoMessaggio=" + tipoMessaggio + ", testo=" + testo
				+ ", contenutoMultimediale=" + ", idColonna=" + idColonna
				+ "]";
	}
	

	

	
	

	
	
}
