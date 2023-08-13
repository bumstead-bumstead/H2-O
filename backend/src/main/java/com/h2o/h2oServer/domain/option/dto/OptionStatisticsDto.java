package com.h2o.h2oServer.domain.option.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionStatisticsDto {
    private static final int SELL_NUMBER = 3509;
    private boolean isOverHalf;
    private Integer choiceCount;
    private Float useCount;

    public static OptionStatisticsDto of(Float choiceRatio, Float useCount) {
        return OptionStatisticsDto.builder()
                .isOverHalf(choiceRatio > 0.5)
                .useCount(useCount)
                .choiceCount(Math.round(choiceRatio * SELL_NUMBER))
                .build();
    }
}
