package com.unimib.KanbanBoard.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimib.KanbanBoard.dto.TileDto;
import com.unimib.KanbanBoard.model.Colonna;
import com.unimib.KanbanBoard.model.Tile;
import com.unimib.KanbanBoard.model.User;
import com.unimib.KanbanBoard.repository.ColonnaDao;
import com.unimib.KanbanBoard.repository.UserDao;


@Service
public class ColonnaServiceImpl implements ColonnaService {

	@Autowired
	private ColonnaDao colonnaDao;

	@Autowired
	private UserDao userDao;

	@Override
	public User createColonna(String nomeColonna, String autore) {
		// TODO Auto-generated method stub
		User user = userDao.findByUsername(autore); 

		Colonna colonna = new Colonna();
		colonna.setTitolo(nomeColonna);
		colonna.setArchiviato(false);
		colonna.setAutore(user);

		user.getColonneSet().add(colonna);

		


		return userDao.save(user);
	}

	@Override
	public Boolean deleteColumn(Long idColonna) {
		// TODO Auto-generated method stub
		colonnaDao.deleteById(idColonna);
		
		
		return true;
	}

	@Override
	public Colonna createTile(String autore, TileDto tileDto) {
		// TODO Auto-generated method stub
		
		Colonna colonna = colonnaDao.findById(tileDto.getIdColonna()).get();
		
		Tile tile = new Tile();
		
		
		if (tileDto.getTesto() != null && tileDto.getContenutoMultimediale() == null) {
			tile.setContenutoTestuale(tileDto.getTesto());
		} else if (tileDto.getTesto() == null && tileDto.getContenutoMultimediale() != null) {
			tile.setContenutoMultimediale(tileDto.getContenutoMultimediale());
		} else {
			return null;
		}
		
		
		tile.setAutore(autore);		
		tile.setColonna(colonna);
		tile.setTipoMessaggio(tileDto.getTipoMessaggio());
		tile.setTitolo(tileDto.getTitolo());
		
		
	 
		
		colonna.getTileSet().add(tile);
		
		
		
		return colonnaDao.save(colonna);
	}

	@Override
	public Colonna findById(Long id) {
		// TODO Auto-generated method stub
		return colonnaDao.findById(id).get();
	}

	@Override
	public Colonna updateStato(Long id, Boolean archiviato) {
		// TODO Auto-generated method stub
		Colonna colonna = colonnaDao.findById(id).get();
		
		colonna.setArchiviato(! archiviato);
		
		colonnaDao.save(colonna);
				
		return null;
	}

	@Override
	public Colonna updateTitle(Long id, String nuovoNome) {
		// TODO Auto-generated method stub
		Colonna test = colonnaDao.findByTitolo(nuovoNome);
		
		if (test == null) {
			Colonna colonna = colonnaDao.findById(id).get();
			colonna.setTitolo(nuovoNome);
			
			return colonnaDao.save(colonna);
		}
		
		
		
		return null;
	}
	

}
