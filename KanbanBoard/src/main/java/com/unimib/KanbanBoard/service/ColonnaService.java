package com.unimib.KanbanBoard.service;

import com.unimib.KanbanBoard.dto.TileDto;
import com.unimib.KanbanBoard.model.Colonna;
import com.unimib.KanbanBoard.model.User;


public interface ColonnaService {
	
	public abstract User createColonna(String nomeColonna, String autore);
	
	public abstract Boolean deleteColumn(Long idColonna);
	
	public abstract Colonna createTile(String autore, TileDto tileDto);
	
	public abstract Colonna findById(Long id);
	
	public abstract Colonna updateStato(Long id, Boolean archiviato);
	
	public abstract Colonna updateTitle(Long id, String nuovoNome);
	
	
}
