package com.br.games.domain.DTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInputDTO {
	@NotNull
	@Column
	private String nome;
	@NotNull
	@Column
	private Long lancamento;
	@NotNull
	@Column
	private String genero;
}
