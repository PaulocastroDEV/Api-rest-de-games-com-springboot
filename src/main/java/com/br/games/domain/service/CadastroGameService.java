package com.br.games.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;

@Service
public class CadastroGameService {
	@Autowired
	private GameRepository gameRepository;
	
	public Game save(Game game) {
		return gameRepository.save(game);	
	}
	
	
}
