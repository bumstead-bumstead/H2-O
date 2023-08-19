package com.h2o.h2oServer.domain.quotation.application;

import com.h2o.h2oServer.domain.option.entity.enums.HashTag;

import java.util.Map;

public class CosineSimilarityCalculator {
    public static double calculateCosineSimilarity(Map<HashTag, Integer> vector1, Map<HashTag, Integer> vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (HashTag key : vector1.keySet()) {
            if (vector2.containsKey(key)) {
                dotProduct += vector1.get(key) * vector2.get(key);
            }
            norm1 += Math.pow(vector1.get(key), 2);
        }

        for (Integer value : vector2.values()) {
            norm2 += Math.pow(value, 2);
        }

        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
