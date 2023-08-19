package com.h2o.h2oServer.domain.option;

import com.h2o.h2oServer.domain.trim.entity.OptionStatisticsEntity;

import java.util.List;

public class OptionFixture {
    public static List<OptionStatisticsEntity> generateOptionStatisticsList() {
        return List.of(
                OptionStatisticsEntity.builder()
                        .id(1L)
                        .name("Option A")
                        .useCount(0.75F)
                        .build(),
                OptionStatisticsEntity.builder()
                        .id(2L)
                        .name("Option B")
                        .useCount(0.5F)
                        .build()
        );
    }
}
