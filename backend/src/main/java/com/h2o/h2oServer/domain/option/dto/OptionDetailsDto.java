package com.h2o.h2oServer.domain.option.dto;

import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.OptionEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class OptionDetailsDto {
    private String name;
    private String category;
    private List<String> hashTags;
    private String image;
    private String description;
    private OptionStatisticsDto hmgData;

    public static OptionDetailsDto of(OptionEntity optionEntity, List<HashTagEntity> hashTagEntities) {
        return OptionDetailsDto.builder()
                .name(optionEntity.getName())
                .category(optionEntity.getCategory().getLabel())
                .hashTags(hashTagEntities.stream()
                        .map(hashTagEntity -> hashTagEntity.getName().getLabel())
                        .collect(Collectors.toList()))
                .image(optionEntity.getImage())
                .description(optionEntity.getDescription())
                .hmgData(OptionStatisticsDto.of(optionEntity.getChoiceRatio(),
                        optionEntity.getUseCount()))
                .build();
    }
}
