package com.h2o.h2oServer.domain.option.application;

import com.h2o.h2oServer.domain.option.dto.OptionDetailsDto;
import com.h2o.h2oServer.domain.option.dto.OptionDto;
import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.OptionDetailsEntity;
import com.h2o.h2oServer.domain.option.entity.OptionEntity;
import com.h2o.h2oServer.domain.option.exception.NoSuchOptionException;
import com.h2o.h2oServer.domain.option.mapper.OptionMapper;
import com.h2o.h2oServer.domain.options.dto.*;
import com.h2o.h2oServer.domain.options.entity.TrimDefaultOptionEntity;
import com.h2o.h2oServer.domain.options.entity.TrimExtraOptionEntity;
import com.h2o.h2oServer.domain.options.enums.OptionType;
import com.h2o.h2oServer.global.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionMapper optionMapper;

    public OptionDetailsDto findDetailedOptionInformation(Long optionId, Long trimId) {
        OptionDetailsEntity optionDetailsEntity = optionMapper.findOptionDetails(optionId, trimId)
                .orElseThrow(NoSuchOptionException::new);

        List<HashTagEntity> hashTagEntities = optionMapper.findHashTag(optionId);

        return OptionDetailsDto.of(optionDetailsEntity, hashTagEntities);
    }

    public OptionDto findOptionInformation(Long optionId) {
        OptionEntity optionEntity = optionMapper.findOption(optionId).orElseThrow(NoSuchOptionException::new);

        List<HashTagEntity> hashTagEntities = optionMapper.findHashTag(optionId);

        return OptionDto.of(optionEntity, hashTagEntities);
    }

    public List<TrimExtraOptionDto> findTrimPackages(Long trimId) {
        List<TrimExtraOptionEntity> extraOptionEntities = optionMapper.findTrimPackages(trimId);
        Validator.validateExistenceOfOptions(extraOptionEntities);
        return addPackages(extraOptionEntities);
    }

    public List<TrimExtraOptionDto> findTrimPackages(Long trimId, PageRangeDto pageRangeDto) {
        List<TrimExtraOptionEntity> extraOptionEntities = optionMapper.findTrimPackagesWithRange(trimId, pageRangeDto);
        Validator.validateExistenceOfOptions(extraOptionEntities);
        return addPackages(extraOptionEntities);
    }

    public List<TrimExtraOptionDto> findTrimExtraOptions(Long trimId) {
        List<TrimExtraOptionEntity> extraOptionEntities = optionMapper.findTrimExtraOptions(trimId);
        Validator.validateExistenceOfOptions(extraOptionEntities);
        return addExtraOptions(extraOptionEntities);
    }

    public List<TrimExtraOptionDto> findTrimExtraOptions(Long trimId, PageRangeDto pageRangeDto) {
        List<TrimExtraOptionEntity> extraOptionEntities = optionMapper.findTrimExtraOptionsWithRange(trimId, pageRangeDto);
        Validator.validateExistenceOfOptions(extraOptionEntities);
        return addExtraOptions(extraOptionEntities);
    }

    public List<TrimDefaultOptionDto> findTrimDefaultOptions(Long trimId) {
        List<TrimDefaultOptionEntity> defaultOptionEntities = optionMapper.findTrimDefaultOptions(trimId);
        Validator.validateExistenceOfOptions(defaultOptionEntities);
        return addDefaultOptions(defaultOptionEntities);
    }

    public List<TrimDefaultOptionDto> findTrimDefaultOptions(Long trimId, PageRangeDto pageRangeDto) {
        List<TrimDefaultOptionEntity> defaultOptionEntities = optionMapper.findTrimDefaultOptionsWithRange(trimId, pageRangeDto);
        Validator.validateExistenceOfOptions(defaultOptionEntities);
        return addDefaultOptions(defaultOptionEntities);
    }

    public ExtraOptionSizeDto findTrimExtraOptionSize(Long trimId) {
        return ExtraOptionSizeDto.of(
                optionMapper.findTrimPackageSize(trimId), optionMapper.findTrimOptionSize(trimId, OptionType.EXTRA)
        );
    }

    public DefaultOptionSizeDto findTrimDefaultOptionSize(Long trimId) {
        return DefaultOptionSizeDto.of(optionMapper.findTrimOptionSize(trimId, OptionType.DEFAULT));
    }

    private List<TrimExtraOptionDto> addPackages(List<TrimExtraOptionEntity> extraOptionEntities) {
        List<TrimExtraOptionDto> trimExtraOptionDtos = new ArrayList<>();

        for (TrimExtraOptionEntity extraOptionEntity : extraOptionEntities) {
            Long packageId = extraOptionEntity.getId();
            List<HashTagEntity> packageHashTags = optionMapper.findPackageHashTags(packageId);

            TrimExtraOptionDto trimExtraOptionDto = TrimExtraOptionDto.of(true, extraOptionEntity, packageHashTags);

            Collections.sort(trimExtraOptionDto.getHashTags());

            trimExtraOptionDtos.add(trimExtraOptionDto);
        }

        return trimExtraOptionDtos;
    }

    private List<TrimExtraOptionDto> addExtraOptions(List<TrimExtraOptionEntity> extraOptionEntities) {
        List<TrimExtraOptionDto> trimExtraOptionDtos = new ArrayList<>();

        for (TrimExtraOptionEntity extraOptionEntity : extraOptionEntities) {
            Long optionId = extraOptionEntity.getId();
            List<HashTagEntity> optionHashTags = optionMapper.findOptionHashTag(optionId);

            TrimExtraOptionDto trimExtraOptionDto = TrimExtraOptionDto.of(false, extraOptionEntity, optionHashTags);

            Collections.sort(trimExtraOptionDto.getHashTags());

            trimExtraOptionDtos.add(trimExtraOptionDto);
        }

        return trimExtraOptionDtos;
    }

    private List<TrimDefaultOptionDto> addDefaultOptions(List<TrimDefaultOptionEntity> defaultOptionEntities) {
        List<TrimDefaultOptionDto> trimDefaultOptionDtos = new ArrayList<>();

        for (TrimDefaultOptionEntity defaultOptionEntity : defaultOptionEntities) {
            Long optionId = defaultOptionEntity.getId();
            List<HashTagEntity> optionHashTags = optionMapper.findOptionHashTag(optionId);

            TrimDefaultOptionDto trimDefaultOptionDto = TrimDefaultOptionDto.of(defaultOptionEntity, optionHashTags);

            Collections.sort(trimDefaultOptionDto.getHashTags());

            trimDefaultOptionDtos.add(trimDefaultOptionDto);
        }

        return trimDefaultOptionDtos;
    }
}
