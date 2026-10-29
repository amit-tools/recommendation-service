package com.example.recommendation.model;

public class MerchantInfo {

    private final String merchantKey;
    private final String merchantType;
    private final String merchantName;
    private final String merchantCategory;
    private final String merchantSubcategory;
    private final double popularityScore;

    public MerchantInfo(
            String merchantKey,
            String merchantType,
            String merchantName,
            String merchantCategory,
            String merchantSubcategory,
            double popularityScore) {
        this.merchantKey = merchantKey;
        this.merchantType = merchantType;
        this.merchantName = merchantName;
        this.merchantCategory = merchantCategory;
        this.merchantSubcategory = merchantSubcategory;
        this.popularityScore = popularityScore;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public String getMerchantSubcategory() {
        return merchantSubcategory;
    }

    public double getPopularityScore() {
        return popularityScore;
    }
}
