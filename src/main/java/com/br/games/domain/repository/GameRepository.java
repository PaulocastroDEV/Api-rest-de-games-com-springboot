package com.br.games.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.games.domain.model.Game;
@Repository
public interface GameRepository extends JpaRepository<Game, Long>{

}
