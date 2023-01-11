package com.br.games.domain.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.games.domain.exception.ResourceNotFoundException;
import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;
import com.br.games.domain.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	private GameService gameService;
	@GetMapping
	public ResponseEntity<List<Game>> findAll(){
		var list = gameService.findAll();
		return ResponseEntity.ok().body(list);
		
	}
	@GetMapping("/{gameId}")
	public ResponseEntity<Game> findById(@PathVariable long gameId){
		var obj = gameService.findById(gameId);
		return ResponseEntity.ok().body(obj);
	}
	@PostMapping
	public ResponseEntity<Game> create(@RequestBody Game obj){
		var game = gameService.save(obj);
		return ResponseEntity.status(HttpStatus.CREATED).body(game);
		
	}
	@PutMapping("/{gameId}")
	public ResponseEntity<Game> Update(@PathVariable Long gameId,@RequestBody Game obj){
			var game= gameService.update(gameId, obj);
			return ResponseEntity.status(HttpStatus.OK).body(game);
		
	}
	@DeleteMapping("/{gameId}")
	public ResponseEntity<?> delete(@PathVariable Long gameId){
		gameService.remove(gameId);
		return ResponseEntity.noContent().build();
	}
}
