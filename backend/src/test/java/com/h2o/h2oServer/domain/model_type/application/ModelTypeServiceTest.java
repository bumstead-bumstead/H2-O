package com.h2o.h2oServer.domain.model_type.application;

import com.h2o.h2oServer.domain.model_type.mapper.BodytypeMapper;
import com.h2o.h2oServer.domain.model_type.mapper.DrivetrainMapper;
import com.h2o.h2oServer.domain.model_type.mapper.PowertrainMapper;
import com.h2o.h2oServer.domain.model_type.mapper.TechnicalSpecMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ModelTypeServiceTest {
    private ModelTypeService modelTypeService;
    private PowertrainMapper powerTrainMapper;
    private BodytypeMapper bodyTypeMapper;
    private DrivetrainMapper driveTrainMapper;
    private TechnicalSpecMapper technicalSpecMapper;
    private SoftAssertions softly;

    @BeforeEach
    void setup() {
        powerTrainMapper = Mockito.mock(PowertrainMapper.class);
        bodyTypeMapper = Mockito.mock(BodytypeMapper.class);
        driveTrainMapper = Mockito.mock(DrivetrainMapper.class);
        technicalSpecMapper = Mockito.mock(TechnicalSpecMapper.class);
        modelTypeService = new ModelTypeService(powerTrainMapper,
                bodyTypeMapper,
                driveTrainMapper,
                technicalSpecMapper);
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("존재하는 차량에 대해서 ModelTypeDto를 반환한다.")
    void findModelTypes() {
        //given
        Long carId = 1L;
        when(powerTrainMapper.findPowertrainsByCarId(carId)).thenReturn();
        when(powerTrainMapper.findOutput(anyLong())).thenReturn();
        when(powerTrainMapper.findTorque(anyLong())).thenReturn();
        when(bodyTypeMapper.findBodytypesByCarId(carId)).thenReturn();
        when(driveTrainMapper.findDrivetrainsByCarId(carId)).thenReturn();

        //when
        //then
    }

    @Test
    void findTechnicalSpec() {
    }

    @Test
    void findDefaultModelType() {
    }
}
