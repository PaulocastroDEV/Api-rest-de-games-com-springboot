package com.br.games.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	private GameRepository gameRepository;
	@GetMapping
	public List<Game> findAll(){
		return gameRepository.findAll();
	}
	@GetMapping("/{gameid}")
	public ResponseEntity<Game> findById(@PathVariable Long gameid){
		Optional<Game> game = gameRepository.findById(gameid);
		if(game.isPresent()) {
			return ResponseEntity.ok(game.get());
		}
		return ResponseEntity.notFound().build();
	}
	
}