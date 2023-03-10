package com.br.games.domain.controller;

import java.util.List;

import javax.validation.Valid;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.games.domain.DTO.GameDTO;
import com.br.games.domain.DTO.GameDTOAssembler;
import com.br.games.domain.DTO.GameDTODisassembler;
import com.br.games.domain.DTO.GameInputDTO;
import com.br.games.domain.model.Game;
import com.br.games.domain.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameDTOAssembler gameDTOAssembler;
	
	@Autowired
	private GameDTODisassembler gameDTODisassembler;
	
	@GetMapping
	public List<GameDTO> findAll(){
		
		return gameDTOAssembler.toCollectionMode(gameService.findAll());
		
	}
	@GetMapping("/{gameId}")
	public GameDTO findById(@PathVariable long gameId){
		Game game = gameService.findById(gameId);
		return gameDTOAssembler.toModel(game);
	}
	@PostMapping
	public ResponseEntity<Game> create(@RequestBody @Valid GameInputDTO gameInputDTO){
		Game game = gameDTODisassembler.toObjectDomain(gameInputDTO);
		gameService.save(game);
		return ResponseEntity.status(HttpStatus.CREATED).body(game);
		
	}
	@PutMapping("/{gameId}")
	public ResponseEntity<Game> Update(@PathVariable Long gameId,@RequestBody @Valid GameInputDTO gameInputDTO){
			Game game= gameDTODisassembler.toObjectDomain(gameInputDTO);
			gameService.update(gameId, game);
			return ResponseEntity.status(HttpStatus.OK).body(game);
		
	}
	@DeleteMapping("/{gameId}")
	public ResponseEntity<?> delete(@PathVariable Long gameId){
		gameService.remove(gameId);
		return ResponseEntity.noContent().build();
	}
}
