package com.br.games.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
