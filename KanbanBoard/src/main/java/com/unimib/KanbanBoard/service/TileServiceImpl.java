package com.unimib.KanbanBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimib.KanbanBoard.dto.TileDto;
import com.unimib.KanbanBoard.model.Colonna;
import com.unimib.KanbanBoard.model.Tile;
import com.unimib.KanbanBoard.repository.TileDao;


@Service
public class TileServiceImpl implements TileService{
	
	@Autowired
	private TileDao tileDao;
	
	@Autowired
	private ColonnaServiceImpl colonnaService;

	@Override
	public boolean deleteTile(Long tileId) {
		// TODO Auto-generated method stub
		
		tileDao.deleteById(tileId);
		
		return true;
	}

	@Override
	public boolean updateTile(Long tileId, Long colonnaId, String autore) {
		// TODO Auto-generated method stub
		
		Tile tile = tileDao.getById(tileId);
				
		tileDao.deleteById(tileId);
		
		TileDto tileDto = new TileDto();
		
		tileDto.setContenutoMultimediale(tile.getContenutoMultimediale());
		tileDto.setIdColonna(colonnaId);
		tileDto.setTesto(tile.getContenutoTestuale());
		tileDto.setTipoMessaggio(tile.getTipoMessaggio());
		tileDto.setTitolo(tile.getTitolo());
		
		
		colonnaService.createTile(autore, tileDto);
		
		
		
		return true;
	}

}
