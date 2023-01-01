package com.br.games.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.games.domain.exception.EntidadeNaoEncontradaException;
import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;

@Service
public class CadastroGameService {
	@Autowired
	private GameRepository gameRepository;
	
	public Game save(Game game) {
		return gameRepository.save(game);	
	}
	
	public void remove(Long gameid) {
		try {
			gameRepository.deleteById(gameid);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um game cadastrado com o id %d",gameid));
		}
	}

}
