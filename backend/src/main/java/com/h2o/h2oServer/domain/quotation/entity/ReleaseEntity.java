package com.h2o.h2oServer.domain.quotation.entity;

import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ReleaseEntity {
    private Long trimId;
    private Long powertrainId;
    private Long bodytypeId;
    private Long drivetrainId;
    private Long internalColorId;
    private Long externalColorId;
    private String optionCombination;
    private String packageCombination;
    private Integer price;
    private Integer quotationCount;
}
