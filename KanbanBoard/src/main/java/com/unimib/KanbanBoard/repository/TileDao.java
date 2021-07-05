package com.unimib.KanbanBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unimib.KanbanBoard.model.Tile;

@Repository
public interface TileDao extends JpaRepository<Tile, Long>{

}
