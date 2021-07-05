package com.unimib.KanbanBoard.service;

public interface TileService {
	
	public abstract boolean deleteTile(Long tileId);
	
	public abstract boolean updateTile(Long tileId, Long colonnaId, String autore);

}
