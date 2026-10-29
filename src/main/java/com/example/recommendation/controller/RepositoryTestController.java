package com.example.recommendation.controller;

import com.example.recommendation.model.ConsumerAffinity;
import com.example.recommendation.model.MerchantInfo;
import com.example.recommendation.model.SimilarMerchant;
import com.example.recommendation.repository.RecommendationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/repository-test")
public class RepositoryTestController {

    private final RecommendationRepository recommendationRepository;

    public RepositoryTestController(
            RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @GetMapping("/consumer/{consumerId}")
    public Map<String, Object> testConsumerRepository(
            @PathVariable long consumerId) {

        List<ConsumerAffinity> affinities =
                recommendationRepository.findConsumerAffinities(consumerId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("consumerId", consumerId);
        response.put("affinityCount", affinities.size());
        response.put("affinities", affinities);
        
        if (!affinities.isEmpty()) {
            String topMerchantKey = affinities.get(0).getMerchantKey();

            MerchantInfo merchant =
                    recommendationRepository.findMerchantByKey(topMerchantKey);

            List<SimilarMerchant> similar =
                    recommendationRepository.findSimilarMerchants(
                            topMerchantKey,
                            10
                    );

            response.put("topHistoryMerchant", merchant);
            response.put("similarMerchants", similar);
        }

        return response;
    }
}
