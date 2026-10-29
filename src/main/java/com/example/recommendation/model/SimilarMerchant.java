package com.example.recommendation.model;

public class SimilarMerchant {

    private final String sourceMerchantKey;
    private final String targetMerchantKey;
    private final int commonConsumers;
    private final double similarityScore;

    public SimilarMerchant(
            String sourceMerchantKey,
            String targetMerchantKey,
            int commonConsumers,
            double similarityScore) {
        this.sourceMerchantKey = sourceMerchantKey;
        this.targetMerchantKey = targetMerchantKey;
        this.commonConsumers = commonConsumers;
        this.similarityScore = similarityScore;
    }

    public String getSourceMerchantKey() {
        return sourceMerchantKey;
    }

    public String getTargetMerchantKey() {
        return targetMerchantKey;
    }

    public int getCommonConsumers() {
        return commonConsumers;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }
}
