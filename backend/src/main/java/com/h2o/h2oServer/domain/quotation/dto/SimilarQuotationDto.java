package com.h2o.h2oServer.domain.quotation.dto;

import com.h2o.h2oServer.domain.model_type.dto.ModelTypeNameDto;
import com.h2o.h2oServer.domain.option.dto.OptionSummaryDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimilarQuotationDto {
    private ModelTypeNameDto modelType;
    private String image;
    private Integer price;
    private List<OptionSummaryDto> options;

    public static SimilarQuotationDto of(ModelTypeNameDto modelTypeNameDto,
                                         String image,
                                         Integer price,
                                         List<OptionSummaryDto> options) {
        return SimilarQuotationDto.builder()
                .modelType(modelTypeNameDto)
                .image(image)
                .price(price)
                .options(options)
                .build();
    }
}
