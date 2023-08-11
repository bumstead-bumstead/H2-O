package com.h2o.h2oServer.domain.trim.dto;

import com.h2o.h2oServer.domain.trim.entity.ExternalColorEntity;
import com.h2o.h2oServer.domain.trim.entity.ImageEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ExternalColorDto {
    Long id;
    String name;
    Float choiceRatio;
    Integer price;
    String hexCode;
    List<String> images;

    public static ExternalColorDto of(ExternalColorEntity externalColorEntity,
                                      List<ImageEntity> imageEntities) {
        return ExternalColorDto.builder()
                .id(externalColorEntity.getId())
                .name(externalColorEntity.getName())
                .choiceRatio(externalColorEntity.getChoiceRatio())
                .hexCode(externalColorEntity.getColorCode())
                .price(externalColorEntity.getPrice())
                .images(imageEntities.stream()
                        .map(ImageEntity::getImage)
                        .collect(Collectors.toList()))
                .build();
    }
}