package com.h2o.h2oServer.domain.option.application;

import com.h2o.h2oServer.domain.option.dto.OptionDetailsDto;
import com.h2o.h2oServer.domain.option.dto.OptionDto;
import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.OptionDetailsEntity;
import com.h2o.h2oServer.domain.option.entity.OptionEntity;
import com.h2o.h2oServer.domain.option.mapper.OptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionMapper optionMapper;

    public OptionDetailsDto findOptionInformation(Long optionId, Long trimId) {
        OptionDetailsEntity optionDetailsEntity = optionMapper.findOptionDetails(optionId, trimId);
        List<HashTagEntity> hashTagEntities = optionMapper.findHashTag(optionId);

        return OptionDetailsDto.of(optionDetailsEntity, hashTagEntities);
    }

    public OptionDto findOptionInformation(Long optionId) {
        OptionEntity optionEntity = optionMapper.findOption(optionId);
        List<HashTagEntity> hashTagEntities = optionMapper.findHashTag(optionId);

        return OptionDto.of(optionEntity, hashTagEntities);
    }

}
