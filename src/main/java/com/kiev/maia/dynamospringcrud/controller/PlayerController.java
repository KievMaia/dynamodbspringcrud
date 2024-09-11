package com.kiev.maia.dynamospringcrud.controller;

import com.kiev.maia.dynamospringcrud.controller.dto.ScoreDto;
import com.kiev.maia.dynamospringcrud.entity.PlayerHistoryEntity;
import com.kiev.maia.dynamospringcrud.repository.PlayerHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
public class PlayerController {

    private final PlayerHistoryRepository repository;

    public PlayerController(final PlayerHistoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/{username}/games")
    public ResponseEntity<Void> save(@RequestBody ScoreDto scoreDto, @PathVariable final String username) {
        final var entity = PlayerHistoryEntity.fromScore(username, scoreDto);
        repository.save(entity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/games")
    public ResponseEntity<List<PlayerHistoryEntity>> findAllByUsername(@PathVariable final String username) {
        return ResponseEntity.ok(repository.findAllByUsername(username));
    }

    @GetMapping("/{username}/games/{gameId}")
    public ResponseEntity<PlayerHistoryEntity> findById(@PathVariable final String username,
                                                        @PathVariable final String gameId) {
        return ResponseEntity.ok(repository.findById(username, gameId));
    }

    @PutMapping("/{username}/games/{gameId}")
    @GetMapping("/{username}/games/{gameId}")
    public ResponseEntity<Void> updateGame(@PathVariable final String username,
                                           @PathVariable final String gameId,
                                           @RequestBody ScoreDto scoreDto) {
        repository.updateGameHistory(username, gameId, scoreDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}/games/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable final String username,
                                         @PathVariable final String gameId) {
        repository.deleteGameHistory(username, gameId);
        return ResponseEntity.noContent().build();
    }
}
