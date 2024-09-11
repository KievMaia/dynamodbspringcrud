#!/bin/bash

# Variáveis
TABLE_NAME="PlayerHistory"
DYNAMO_ENDPOINT="http://localhost:4566"  # URL do LocalStack
REGION="us-east-1"

# Verifica se a tabela já existe
TABLE_EXIST=$(aws dynamodb list-tables --endpoint-url $DYNAMO_ENDPOINT --region $REGION | grep $TABLE_NAME)

if [ -z "$TABLE_EXIST" ]; then
    echo "Criando tabela $TABLE_NAME..."

    aws dynamodb create-table \
        --table-name $TABLE_NAME \
        --attribute-definitions \
            AttributeName=username,AttributeType=S \
            AttributeName=game_id,AttributeType=S \
        --key-schema \
            AttributeName=username,KeyType=HASH \
            AttributeName=game_id,KeyType=RANGE \
        --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
        --region $REGION \
        --endpoint-url $DYNAMO_ENDPOINT

    echo "Tabela $TABLE_NAME criada com sucesso!"
else
    echo "Tabela $TABLE_NAME já existe."
fi