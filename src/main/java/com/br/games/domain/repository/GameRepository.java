package com.br.games.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.games.domain.model.Game;

public interface GameRepository extends JpaRepository<Game, Long>{

}
