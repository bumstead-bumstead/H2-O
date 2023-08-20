package com.h2o.h2oServer.domain.model_type.api;

import com.h2o.h2oServer.domain.car.exception.NoSuchCarException;
import com.h2o.h2oServer.domain.model_type.application.ModelTypeService;
import com.h2o.h2oServer.domain.model_type.dto.CarBodytypeDto;
import com.h2o.h2oServer.domain.model_type.dto.CarDrivetrainDto;
import com.h2o.h2oServer.domain.model_type.dto.CarPowertrainDto;
import com.h2o.h2oServer.domain.model_type.dto.ModelTypeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.h2o.h2oServer.domain.model_type.BodyTypeFixture.*;
import static com.h2o.h2oServer.domain.model_type.DrivetrainFixture.*;
import static com.h2o.h2oServer.domain.model_type.PowertrainFixture.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ModelTypeController.class)
@AutoConfigureMockMvc
class ModelTypeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ModelTypeService modelTypeService;

    @Nested
    @DisplayName("모델 타입 정보 조회 테스트")
    class GetModelTypeTest {
        @Test
        @DisplayName("존재하는 trim, option id 요청에 대해서 InternalColorDto를 응답한다.")
        void withValidTrimId() throws Exception {
            // given
            Long carId = 1L;

            ModelTypeDto expectedModelTypeDto = ModelTypeDto.of(
                    List.of(generateCarPowertrainDto()),
                    List.of(generateCarBodytypeDto()),
                    List.of(generateCarDrivetrainDto())
            );

            List<CarPowertrainDto> powertrains = expectedModelTypeDto.getPowertrains();
            List<CarBodytypeDto> bodytypes = expectedModelTypeDto.getBodytypes();
            List<CarDrivetrainDto> drivetrains = expectedModelTypeDto.getDrivetrains();

            when(modelTypeService.findModelTypes(carId)).thenReturn(expectedModelTypeDto);

            // when
            mockMvc.perform(get("/car/{carId}/model-type", carId))
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.powertrains").isArray())
                    .andExpect(jsonPath("$.powertrains.length()").value(powertrains.size()))
                    .andExpect(jsonPath("$.powertrains[0].id").value(powertrains.get(0).getId()))
                    .andExpect(jsonPath("$.powertrains[0].name").value(powertrains.get(0).getName()))
                    .andExpect(jsonPath("$.powertrains[0].price").value(powertrains.get(0).getPrice()))
                    .andExpect(jsonPath("$.powertrains[0].choiceRatio").value(powertrains.get(0).getChoiceRatio()))
                    .andExpect(jsonPath("$.powertrains[0].description").value(powertrains.get(0).getDescription()))
                    .andExpect(jsonPath("$.powertrains[0].image").value(powertrains.get(0).getImage()))
                    .andExpect(jsonPath("$.powertrains[0].maxOutput").exists())
                    .andExpect(jsonPath("$.powertrains[0].maxTorque").exists())
                    .andExpect(jsonPath("$.bodytypes").isArray())
                    .andExpect(jsonPath("$.bodytypes.length()").value(bodytypes.size()))
                    .andExpect(jsonPath("$.bodytypes[0].id").value(bodytypes.get(0).getId()))
                    .andExpect(jsonPath("$.bodytypes[0].name").value(bodytypes.get(0).getName()))
                    .andExpect(jsonPath("$.bodytypes[0].description").value(bodytypes.get(0).getDescription()))
                    .andExpect(jsonPath("$.bodytypes[0].image").value(bodytypes.get(0).getImage()))
                    .andExpect(jsonPath("$.bodytypes[0].price").value(bodytypes.get(0).getPrice()))
                    .andExpect(jsonPath("$.bodytypes[0].choiceRatio").value(bodytypes.get(0).getChoiceRatio()))
                    .andExpect(jsonPath("$.drivetrains").isArray())
                    .andExpect(jsonPath("$.drivetrains.length()").value(drivetrains.size()))
                    .andExpect(jsonPath("$.drivetrains[0].id").value(drivetrains.get(0).getId()))
                    .andExpect(jsonPath("$.drivetrains[0].name").value(drivetrains.get(0).getName()))
                    .andExpect(jsonPath("$.drivetrains[0].description").value(drivetrains.get(0).getDescription()))
                    .andExpect(jsonPath("$.drivetrains[0].image").value(drivetrains.get(0).getImage()))
                    .andExpect(jsonPath("$.drivetrains[0].price").value(drivetrains.get(0).getPrice()))
                    .andExpect(jsonPath("$.drivetrains[0].choiceRatio").value(drivetrains.get(0).getChoiceRatio()));
        }

        @Test
        @DisplayName("존재하지 않는 car 요청에 대해서 NotFound로 응답한다.")
        void withInvalidTrim() throws Exception {
            //given
            Long carId = 1L;

            when(modelTypeService.findModelTypes(carId)).thenThrow(new NoSuchCarException());

            //when
            mockMvc.perform(get("/car/{carId}/model-type", carId))
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }
}
