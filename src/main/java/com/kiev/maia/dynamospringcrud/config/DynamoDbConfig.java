package com.kiev.maia.dynamospringcrud.config;

import com.kiev.maia.dynamospringcrud.entity.PlayerHistoryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public DynamoDbTable<PlayerHistoryEntity> playerHistoryTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("PlayerHistory", TableSchema.fromBean(PlayerHistoryEntity.class));
    }
}
