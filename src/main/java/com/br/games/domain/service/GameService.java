package com.br.games.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.br.games.domain.exception.ResourceNotFoundException;
import com.br.games.domain.exception.ValidationException;
import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	public List<Game> findAll(){
		return gameRepository.findAll();
	}

	public Game findById(@PathVariable Long gameid){
		Optional<Game> game= gameRepository.findById(gameid);
		
		return gameRepository.findById(gameid).orElseThrow(() -> new ResourceNotFoundException("Game não encontra Id: %d" + gameid));
	}

	public Game save(Game game) {
		return gameRepository.save(game);
	}
	
	public Game update( Long gameid,Game game){
		try {
			findById(gameid);
			game.setId(gameid);
			return gameRepository.save(game);
		}catch(RuntimeException e) {
			throw new ValidationException(e.getMessage());
		}
	}
	public void remove(Long gameId){
		try {
			gameRepository.deleteById(gameId);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não existe um Game cadastrado com o id " + gameId);
		}		
	}


}
