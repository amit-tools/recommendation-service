# Recommendation Service — Step 6.2

Environment:
- OpenJDK 11.0.18
- Apache Maven 3.8.7
- Spring Boot 2.7.18
- MySQL recommendation_engine

## What Step 6.2 adds

Repository access for:

- consumer_merchant_affinity
- merchant_similarity
- merchant_features

Main repository:

```text
RecommendationRepository
```

It supports:

```text
findConsumerAffinities(consumerId)
findSimilarMerchants(sourceMerchantKey, limit)
findMerchantByKey(merchantKey)
findMerchantsByKeys(merchantKeys)
consumerHasUsedMerchant(consumerId, merchantKey)
countConsumersWithAffinity()
```

## Run

```bash
export DB_USERNAME='reco_app'
export DB_PASSWORD='your-password'

mvn clean spring-boot:run
```

## Verify repository reads

Try consumer 100:

```text
http://localhost:8080/api/v1/repository-test/consumer/100
```

The response should contain:

- consumerId
- affinityCount
- affinities
- topHistoryMerchant
- similarMerchants

This is only a repository verification endpoint.
The real recommendation endpoint will be added in Step 6.3.
