package com.example.recommendation.model;

public class ConsumerAffinity {

    private final long consumerId;
    private final String merchantKey;
    private final double affinityScore;
    private final int transactionCount;
    private final double totalSpend;
    private final int recencyDays;

    public ConsumerAffinity(
            long consumerId,
            String merchantKey,
            double affinityScore,
            int transactionCount,
            double totalSpend,
            int recencyDays) {
        this.consumerId = consumerId;
        this.merchantKey = merchantKey;
        this.affinityScore = affinityScore;
        this.transactionCount = transactionCount;
        this.totalSpend = totalSpend;
        this.recencyDays = recencyDays;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public double getAffinityScore() {
        return affinityScore;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public int getRecencyDays() {
        return recencyDays;
    }
}
