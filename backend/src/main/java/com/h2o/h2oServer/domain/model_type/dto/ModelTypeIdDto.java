package com.h2o.h2oServer.domain.model_type.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelTypeIdDto {
    private Long powertrainId;
    private Long bodytypeId;
    private Long drivetrainId;
}
