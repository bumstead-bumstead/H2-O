package com.h2o.h2oServer.domain.optionPackage.dto;

import com.h2o.h2oServer.domain.option.dto.OptionDetailsDto;
import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.enums.HashTag;
import com.h2o.h2oServer.domain.optionPackage.entity.PackageEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class PackageDetailsDto {
    private String name;
    private String category;
    private Integer price;
    private Integer choiceRatio;
    private List<String> hashTags;
    private List<OptionDetailsDto> components;

    public static PackageDetailsDto of(PackageEntity packageEntity,
                                       List<HashTagEntity> hashTagEntities,
                                       List<OptionDetailsDto> optionDetailsDtos) {
        return PackageDetailsDto.builder()
                .name(packageEntity.getName())
                .category(packageEntity.getCategory().getLabel())
                .price(packageEntity.getPrice())
                .choiceRatio(Math.round(packageEntity.getChoiceRatio() * 100))
                .hashTags(hashTagEntities.stream()
                        .map(HashTagEntity::getName)
                        .map(HashTag::getLabel)
                        .collect(Collectors.toList()))
                .components(optionDetailsDtos)
                .build();
    }
}
