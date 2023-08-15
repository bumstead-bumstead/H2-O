package com.h2o.h2oServer.domain.trim.application;

import com.h2o.h2oServer.domain.car.mapper.CarMapper;
import com.h2o.h2oServer.domain.trim.dto.InternalColorDto;
import com.h2o.h2oServer.domain.trim.dto.ExternalColorDto;
import com.h2o.h2oServer.domain.trim.dto.PriceRangeDto;
import com.h2o.h2oServer.domain.trim.dto.TrimDto;
import com.h2o.h2oServer.domain.trim.entity.ExternalColorEntity;
import com.h2o.h2oServer.domain.trim.entity.ImageEntity;
import com.h2o.h2oServer.domain.trim.entity.InternalColorEntity;
import com.h2o.h2oServer.domain.trim.entity.OptionStatisticsEntity;
import com.h2o.h2oServer.domain.trim.entity.TrimEntity;
import com.h2o.h2oServer.domain.trim.mapper.ExternalColorMapper;
import com.h2o.h2oServer.domain.trim.mapper.TrimMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrimService {
    private final TrimMapper trimMapper;
    private final ExternalColorMapper externalColorMapper;
    private final CarMapper carMapper;

    public List<TrimDto> findTrimInformation(Long vehicleId) {
        List<TrimEntity> trimEntities = trimMapper.findByCarId(vehicleId);

        return trimEntities.stream()
                .map(this::manipulateTrimEntity)
                .collect(Collectors.toList());
    }

    public List<ExternalColorDto> findExternalColorInformation(Long trimId) {
        List<ExternalColorEntity> externalColorEntities = trimMapper.findExternalColor(trimId);

        return externalColorEntities.stream()
                .map(this::manipulateExternalColorEntity)
                .collect(Collectors.toList());
    }

    private TrimDto manipulateTrimEntity(TrimEntity trimEntity) {
        Long trimId = trimEntity.getId();
        List<OptionStatisticsEntity> optionStatisticsEntities = trimMapper.findOptionStatistics(trimId);
        List<ImageEntity> imageEntities = trimMapper.findImages(trimId);

        return TrimDto.of(trimEntity, imageEntities, optionStatisticsEntities);
    }

    private ExternalColorDto manipulateExternalColorEntity(ExternalColorEntity externalColorEntity) {
        Long externalColorEntityId = externalColorEntity.getId();
        List<ImageEntity> imageEntities = externalColorMapper.findImages(externalColorEntityId);

        return ExternalColorDto.of(externalColorEntity, imageEntities);
    }

    public List<InternalColorDto> findInternalColorInformation(Long trimId) {
        List<InternalColorEntity> internalColorEntities = trimMapper.findInternalColor(trimId);

        return internalColorEntities.stream()
                .map(InternalColorDto::of)
                .collect(Collectors.toList());
    }

    public PriceRangeDto findPriceRange(Long trimId) {
        TrimEntity trimEntity = trimMapper.findById(trimId);
        Long carId = trimEntity.getCarId();
        Integer trimPrice = trimEntity.getPrice();
        Integer componentPrice = trimMapper.findMaximumComponentPrice(trimId);

        Integer minimumModelTypePrice = carMapper.findMinimumModelTypePrice(carId);
        Integer maximumModelTypePrice = carMapper.findMaximumModelTypePrice(carId);

        return PriceRangeDto.of(trimPrice + maximumModelTypePrice + componentPrice,
                trimPrice + minimumModelTypePrice);
    }
}
