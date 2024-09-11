package com.kiev.maia.dynamospringcrud.repository;

import com.kiev.maia.dynamospringcrud.controller.dto.ScoreDto;
import com.kiev.maia.dynamospringcrud.entity.PlayerHistoryEntity;
import com.kiev.maia.dynamospringcrud.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayerHistoryRepository {

    private final DynamoDbTable<PlayerHistoryEntity> table;

    public PlayerHistoryRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("PlayerHistory", TableSchema.fromBean(PlayerHistoryEntity.class));
    }

    public void save(PlayerHistoryEntity playerHistoryEntity) {
        table.putItem(playerHistoryEntity);
    }

    public List<PlayerHistoryEntity> findAllByUsername(final String username) {
        final var key = Key.builder().partitionValue(username).build();
        final var conditional = QueryConditional.keyEqualTo(key);
        final var query = QueryEnhancedRequest.builder().queryConditional(conditional).build();

        return table.query(query)
                .items()
                .stream()
                .toList();
    }

    public PlayerHistoryEntity findById(final String username, final String gameId) {
        return Optional.ofNullable(table.getItem(Key.builder()
                                                         .partitionValue(username)
                                                         .sortValue(gameId)
                                                         .build()))
                .orElseThrow(() -> new NotFoundException("Item not found with username: " + username + " and gameId: " + gameId));
    }

    public void updateGameHistory(final String username, final String gameId, final ScoreDto scoreDto) {
        final var playerHistoryEntity = this.findById(username, gameId);
        playerHistoryEntity.setScore(scoreDto.score());
        this.save(playerHistoryEntity);
    }

    public void deleteGameHistory(final String username, final String gameId) {
        final var playerHistoryEntity = this.findById(username, gameId);
        table.deleteItem(playerHistoryEntity);
    }
}
