package com.kiev.maia.dynamospringcrud.entity;

import com.kiev.maia.dynamospringcrud.controller.dto.ScoreDto;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.util.UUID;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerHistoryEntity {
    private String username;
    private UUID gameId;
    private Double score;
    private Instant createdAt;

    public static PlayerHistoryEntity fromScore(String username, ScoreDto scoreDto) {
        return PlayerHistoryEntity.builder()
                .username(username)
                .gameId(UUID.randomUUID())
                .score(scoreDto.score())
                .createdAt(Instant.now())
                .build();
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("username")
    public String getUsername() {
        return username;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("game_id")
    public UUID getGameId() {
        return gameId;
    }
}
