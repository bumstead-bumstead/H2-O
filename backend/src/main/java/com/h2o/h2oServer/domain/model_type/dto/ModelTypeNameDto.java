package com.h2o.h2oServer.domain.model_type.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelTypeNameDto {
    private String powertrainName;
    private String bodytypeName;
    private String drivetrainName;

    public static ModelTypeNameDto of(String powertrainName,
                                      String bodytypeName,
                                      String drivetrainName) {
        return ModelTypeNameDto.builder()
                .powertrainName(powertrainName)
                .bodytypeName(bodytypeName)
                .drivetrainName(drivetrainName)
                .build();
    }
}
