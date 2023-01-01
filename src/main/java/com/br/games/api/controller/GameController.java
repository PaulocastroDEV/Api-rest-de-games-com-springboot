package com.br.games.api.controller;

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

import com.br.games.domain.exception.EntidadeNaoEncontradaException;
import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;
import com.br.games.domain.service.CadastroGameService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/games")
public class GameController {
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private CadastroGameService cadastroGame;
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
	@PutMapping("/{gameid}")
	public ResponseEntity<?> update(@PathVariable Long gameid,@RequestBody Game game){
			Optional<Game> gameAtual = gameRepository.findById(gameid);
			if(gameAtual.isPresent()) {
				BeanUtils.copyProperties(game, gameAtual.get(),"id");
				Game gameSalvo = cadastroGame.save(gameAtual.get());
				return ResponseEntity.ok(gameSalvo);
			}
			return ResponseEntity.notFound().build();	
	}
	@PostMapping
	public ResponseEntity<Game> create(@RequestBody Game game){
		game = cadastroGame.save(game);
		return ResponseEntity.status(HttpStatus.CREATED).body(game);
		
	}
	@DeleteMapping("/{gameid}")
	public ResponseEntity<?> delete(@PathVariable Long gameid){
		try {
			cadastroGame.remove(gameid);
			return ResponseEntity.noContent().build();
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}		
	}
	@PatchMapping("/{gameid}")
	public ResponseEntity<?> updatePart(@PathVariable Long gameid, @RequestBody Map<String,Object> campos){
		Optional<Game> gameAtual = gameRepository.findById(gameid);
		if(gameAtual == null) {
			return ResponseEntity.notFound().build();
		}
		merge(campos,gameAtual.get());
		return update(gameid, gameAtual.get());
		
		
	}
	
	private void merge(Map<String,Object> dadosOrigem,Game gameDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Game gameOrigem = objectMapper.convertValue(dadosOrigem, Game.class);
		dadosOrigem.forEach((nomePropiedade,valorPropriedade)->{
			Field field = ReflectionUtils.findField(Game.class, nomePropiedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, gameOrigem);
			ReflectionUtils.setField(field, gameDestino, novoValor);
		});
		
	}
}
