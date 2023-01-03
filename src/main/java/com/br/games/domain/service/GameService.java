package com.br.games.domain.service;

import com.br.games.domain.exception.ResourceNotFoundException;
import com.br.games.domain.exception.ValidationException;
import com.br.games.domain.model.Game;
import com.br.games.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Game não encontrada Id: " + id));
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Long id, Game obj) {
        try {
            findById(id);
            obj.setId(id);
            return gameRepository.save(obj);
        } catch (RuntimeException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void remove(Long gameId) {
        try {
            gameRepository.deleteById(gameId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Não existe um Game cadastrado com o id " + gameId);
        }
    }

}
