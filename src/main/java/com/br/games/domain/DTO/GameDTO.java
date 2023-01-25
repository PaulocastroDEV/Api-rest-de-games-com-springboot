package com.br.games.domain.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
	private Long id;
	private String nome;
	private String lancamento;
	private String genero;
}
