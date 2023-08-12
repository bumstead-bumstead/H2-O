package com.h2o.h2oServer.domain.trim.dto;


import com.h2o.h2oServer.domain.trim.entity.InternalColorEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InternalColorDto {
    private Long id;
    private String name;
    private Float choiceRatio;
    private Integer price;
    private String fabricImage;
    private String bannerImage;

    public static InternalColorDto of(InternalColorEntity internalColorEntity) {
        return InternalColorDto.builder()
                .id(internalColorEntity.getId())
                .name(internalColorEntity.getName())
                .choiceRatio(internalColorEntity.getChoiceRatio())
                .price(internalColorEntity.getPrice())
                .fabricImage(internalColorEntity.getFabricImage())
                .bannerImage(internalColorEntity.getInternalImage())
                .build();
    }
}
