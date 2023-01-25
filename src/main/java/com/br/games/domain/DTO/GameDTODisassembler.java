package com.br.games.domain.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.games.domain.model.Game;

@Component
public class GameDTODisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Game toObjectDomain(GameInputDTO gameInputDTO) {
		return modelMapper.map(gameInputDTO, Game.class);
	}
	
}
