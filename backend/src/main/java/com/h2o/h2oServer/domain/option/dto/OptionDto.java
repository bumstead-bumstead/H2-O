package com.h2o.h2oServer.domain.option.dto;

import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.OptionEntity;
import com.h2o.h2oServer.domain.option.entity.enums.HashTag;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class OptionDto {
    private String name;
    private String category;
    private List<String> hashTags;
    private String image;
    private String description;
    private Integer useCount;
    private boolean containsHmgData;

    public static OptionDto of(OptionEntity optionEntity, List<HashTagEntity> hashTagEntities) {
        OptionDtoBuilder optionDtoBuilder = OptionDto.builder();

        if (containsHmgData(optionEntity)) {
            optionDtoBuilder.useCount(Math.round(optionEntity.getUseCount()));
        }

        return OptionDto.builder()
                .name(optionEntity.getName())
                .category(optionEntity.getCategory().getLabel())
                .hashTags(hashTagEntities.stream()
                        .map(HashTagEntity::getName)
                        .map(HashTag::getLabel)
                        .collect(Collectors.toList()))
                .image(optionEntity.getImage())
                .description(optionEntity.getDescription())
                .containsHmgData(containsHmgData(optionEntity))
                .build();
    }

    private static boolean containsHmgData(OptionEntity optionEntity) {
        return optionEntity.getUseCount() != null;
    }
}