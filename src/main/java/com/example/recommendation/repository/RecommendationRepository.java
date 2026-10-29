package com.example.recommendation.repository;

import com.example.recommendation.model.ConsumerAffinity;
import com.example.recommendation.model.MerchantInfo;
import com.example.recommendation.model.SimilarMerchant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<ConsumerAffinity> CONSUMER_AFFINITY_ROW_MAPPER =
            (rs, rowNum) -> new ConsumerAffinity(
                    rs.getLong("consumer_id"),
                    rs.getString("merchant_key"),
                    rs.getDouble("affinity_score"),
                    rs.getInt("transaction_count"),
                    rs.getDouble("total_spend"),
                    rs.getInt("recency_days")
            );

    private static final RowMapper<SimilarMerchant> SIMILAR_MERCHANT_ROW_MAPPER =
            (rs, rowNum) -> new SimilarMerchant(
                    rs.getString("source_merchant_key"),
                    rs.getString("target_merchant_key"),
                    rs.getInt("common_consumers"),
                    rs.getDouble("similarity_score")
            );

    private static final RowMapper<MerchantInfo> MERCHANT_INFO_ROW_MAPPER =
            (rs, rowNum) -> new MerchantInfo(
                    rs.getString("merchant_key"),
                    rs.getString("merchant_type"),
                    rs.getString("merchant_name"),
                    rs.getString("merchant_category"),
                    rs.getString("merchant_subcategory"),
                    rs.getDouble("popularity_score")
            );

    /**
     * Returns the consumer's strongest merchant relationships first.
     */
    public List<ConsumerAffinity> findConsumerAffinities(long consumerId) {
        String sql =
                "SELECT consumer_id, merchant_key, affinity_score, " +
                "transaction_count, total_spend, recency_days " +
                "FROM consumer_merchant_affinity " +
                "WHERE consumer_id = ? " +
                "ORDER BY affinity_score DESC";

        return jdbcTemplate.query(
                sql,
                CONSUMER_AFFINITY_ROW_MAPPER,
                consumerId
        );
    }

    /**
     * Returns up to 'limit' similar merchants for one source merchant.
     */
    public List<SimilarMerchant> findSimilarMerchants(
            String sourceMerchantKey,
            int limit) {

        String sql =
                "SELECT source_merchant_key, target_merchant_key, " +
                "       common_consumers, similarity_score " +
                "FROM merchant_similarity " +
                "WHERE source_merchant_key = ? " +
                "ORDER BY similarity_score DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(
                sql,
                SIMILAR_MERCHANT_ROW_MAPPER,
                sourceMerchantKey,
                limit
        );
    }

    /**
     * Returns merchant metadata for a single merchant key.
     */
    public MerchantInfo findMerchantByKey(String merchantKey) {
        String sql =
                "SELECT merchant_key, merchant_type, merchant_name, " +
                "       merchant_category, merchant_subcategory, popularity_score " +
                "FROM merchant_features " +
                "WHERE merchant_key = ?";

        List<MerchantInfo> rows = jdbcTemplate.query(
                sql,
                MERCHANT_INFO_ROW_MAPPER,
                merchantKey
        );

        return rows.isEmpty() ? null : rows.get(0);
    }

    /**
     * Returns merchant metadata for multiple merchant keys in one query.
     * This avoids N+1 database calls when scoring recommendation candidates.
     */
    public List<MerchantInfo> findMerchantsByKeys(List<String> merchantKeys) {
        if (merchantKeys == null || merchantKeys.isEmpty()) {
            return Collections.emptyList();
        }

        String placeholders =
                String.join(
                        ",",
                        Collections.nCopies(merchantKeys.size(), "?")
                );

        String sql =
                "SELECT merchant_key, merchant_type, merchant_name, " +
                "       merchant_category, merchant_subcategory, popularity_score " +
                "FROM merchant_features " +
                "WHERE merchant_key IN (" + placeholders + ")";

        return jdbcTemplate.query(
                sql,
                MERCHANT_INFO_ROW_MAPPER,
                merchantKeys.toArray()
        );
    }

    /**
     * Checks whether a consumer has already used a merchant.
     */
    public boolean consumerHasUsedMerchant(
            long consumerId,
            String merchantKey) {

        String sql =
                "SELECT COUNT(*) " +
                "FROM consumer_merchant_affinity " +
                "WHERE consumer_id = ? " +
                "  AND merchant_key = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                consumerId,
                merchantKey
        );

        return count != null && count > 0;
    }

    /**
     * Quick repository sanity check.
     */
    public long countConsumersWithAffinity() {
        String sql =
                "SELECT COUNT(DISTINCT consumer_id) " +
                "FROM consumer_merchant_affinity";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count == null ? 0L : count;
    }
}
