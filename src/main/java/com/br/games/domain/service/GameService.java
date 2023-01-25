package com.br.games.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.br.games.domain.exception.ResourceNotFoundException;
import com.br.games.domain.exception.ValidationException;
import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	@Transactional
	public List<Game> findAll(){
		return gameRepository.findAll();
	}
	@Transactional
	public Game findById(@PathVariable Long gameid){
		Optional<Game> game= gameRepository.findById(gameid);
		
		return gameRepository.findById(gameid).orElseThrow(() -> new ResourceNotFoundException("Não existe um game com Id: " + gameid +" digite um id válido"));
	}
	@Transactional
	public Game save(Game game) {
		return gameRepository.save(game);
	}
	@Transactional
	public Game update( Long gameid,Game game){
		try {
			findById(gameid);
			game.setId(gameid);
			return gameRepository.save(game);
		}catch(RuntimeException e) {
			throw new ValidationException(e.getMessage());
		}
	}
	@Transactional
	public void remove(Long gameId){
		try {
			gameRepository.deleteById(gameId);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não existe um Game cadastrado com o id " + gameId);
		}		
	}


}
