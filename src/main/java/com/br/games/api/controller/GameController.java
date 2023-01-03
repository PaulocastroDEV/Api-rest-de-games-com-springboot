package com.br.games.api.controller;

import com.br.games.domain.model.Game;
import com.br.games.domain.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        var list = gameService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> findById(@PathVariable Long gameId) {
        var obj = gameService.findById(gameId);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Game> update(@PathVariable Long gameId, @RequestBody Game obj) {
        var game = gameService.update(gameId, obj);
        return ResponseEntity.ok().body(game);
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game obj) {
        var game = gameService.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);

    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<?> delete(@PathVariable Long gameId) {
        gameService.remove(gameId);
        return ResponseEntity.noContent().build();
    }

}
