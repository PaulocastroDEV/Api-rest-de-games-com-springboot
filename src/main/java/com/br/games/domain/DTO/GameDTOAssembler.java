package com.br.games.domain.DTO;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.games.domain.model.Game;
@Component
public class GameDTOAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public GameDTO toModel(Game game) {
		return modelMapper.map(game, GameDTO.class);
	}
	public List<GameDTO> toCollectionMode(List<Game> games ){
		return games.stream().map(game -> toModel(game)).collect(Collectors.toList());
	}
}
