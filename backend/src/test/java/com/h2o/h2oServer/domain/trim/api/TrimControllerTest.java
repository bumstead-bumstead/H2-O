package com.h2o.h2oServer.domain.trim.api;

import com.h2o.h2oServer.domain.car.exception.NoSuchCarException;
import com.h2o.h2oServer.domain.trim.Exception.NoSuchTrimException;
import com.h2o.h2oServer.domain.trim.ExternalColorFixture;
import com.h2o.h2oServer.domain.trim.InternalColorFixture;
import com.h2o.h2oServer.domain.trim.application.TrimService;
import com.h2o.h2oServer.domain.trim.dto.ExternalColorDto;
import com.h2o.h2oServer.domain.trim.dto.InternalColorDto;
import com.h2o.h2oServer.domain.trim.dto.TrimDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.h2o.h2oServer.domain.trim.TrimFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrimController.class)
@AutoConfigureMockMvc
class TrimControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrimService trimService;

    @BeforeEach
    void setup() {
    }

    @Nested
    @DisplayName("트림 정보 조회 테스트")
    class getTrimInformationTest {

        @Test
        @DisplayName("존재하는 trim 요청에 대해서 유효한 값을 갖는 TrimDto를 응답한다.")
        void withValidTrimId() throws Exception {
            // given
            Long carId = 1L;

            List<TrimDto> expectedTrimList = List.of(generateTrimDto(), generateTrimDto());
            when(trimService.findTrimInformation(any())).thenReturn(expectedTrimList);

            // when
            mockMvc.perform(get("/car/{carId}/trim", carId))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(expectedTrimList.size()))
                    .andExpect(jsonPath("$[0].id").value(expectedTrimList.get(0).getId()))
                    .andExpect(jsonPath("$[0].name").value(expectedTrimList.get(0).getName()))
                    .andExpect(jsonPath("$[0].description").value(expectedTrimList.get(0).getDescription()))
                    .andExpect(jsonPath("$[0].price").value(expectedTrimList.get(0).getPrice()))
                    .andExpect(jsonPath("$[0].images").isArray())
                    .andExpect(jsonPath("$[0].images.length()").value(expectedTrimList.get(0).getImages().size()))
                    .andExpect(jsonPath("$[0].options").isArray())
                    .andExpect(jsonPath("$[0].options.length()").value(expectedTrimList.get(0).getOptions().size()))
                    .andExpect(jsonPath("$[0].options[0].dataLabel").value(expectedTrimList.get(0).getOptions().get(0).getDataLabel()))
                    .andExpect(jsonPath("$[0].options[0].frequency").value(expectedTrimList.get(0).getOptions().get(0).getFrequency()))
                    .andReturn();
        }

        @Test
        @DisplayName("존재하지 않는 trim 요청에 대해서 NotFound로 응답한다.")
        void withInvalidTrim() throws Exception {
            //given
            Long carId = Long.MAX_VALUE;
            when(trimService.findTrimInformation(carId)).thenThrow(NoSuchCarException.class);

            //when
            mockMvc.perform(get("/car/{carId}/trim", carId))
                    //then
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("외부 색상 정보 조회 테스트")
    class getExternalColorTest {
        @Test
        @DisplayName("존재하는 trim 요청에 대해서 ExternalColorDto를 응답한다.")
        void withValidTrimId() throws Exception {
            // given
            Long trimId = 1L;

            List<ExternalColorDto> expectedExternalColorDtos = ExternalColorFixture.generateExternalColorDtos();
            when(trimService.findExternalColorInformation(any())).thenReturn(expectedExternalColorDtos);

            // when
            mockMvc.perform(get("/trim/{trimId}/external-color", trimId))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].id").value(expectedExternalColorDtos.get(0).getId()))
                    .andExpect(jsonPath("$[0].name").value(expectedExternalColorDtos.get(0).getName()))
                    .andExpect(jsonPath("$[0].choiceRatio").value(expectedExternalColorDtos.get(0).getChoiceRatio()))
                    .andExpect(jsonPath("$[0].price").value(expectedExternalColorDtos.get(0).getPrice()))
                    .andExpect(jsonPath("$[0].hexCode").value(expectedExternalColorDtos.get(0).getHexCode()))
                    .andExpect(jsonPath("$[0].images").isArray());
        }

        @Test
        @DisplayName("존재하지 않는 trim 요청에 대해서 NotFound로 응답한다.")
        void withInvalidTrim() throws Exception {
            //given
            Long trimId = Long.MAX_VALUE;
            when(trimService.findExternalColorInformation(trimId)).thenThrow(NoSuchTrimException.class);

            //when
            mockMvc.perform(get("/trim/{trimId}/external-color", trimId))
                    //then
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("내부 색상 조회 테스트")
    class GetInternalColorInformationTest {
        @Test
        @DisplayName("존재하는 trim 요청에 대해서 InternalColorDto를 응답한다.")
        void withValidTrimId() throws Exception {
            // given
            Long trimId = 1L;

            List<InternalColorDto> expectedInternalColorDtos = InternalColorFixture.generateInternalColorDtos();
            when(trimService.findInternalColorInformation(trimId)).thenReturn(expectedInternalColorDtos);

            // when
            mockMvc.perform(get("/trim/{trimId}/internal-color", trimId))
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(expectedInternalColorDtos.size()))
                    .andExpect(jsonPath("$[0].id").value(expectedInternalColorDtos.get(0).getId()))
                    .andExpect(jsonPath("$[0].name").value(expectedInternalColorDtos.get(0).getName()))
                    .andExpect(jsonPath("$[0].choiceRatio").value(expectedInternalColorDtos.get(0).getChoiceRatio()))
                    .andExpect(jsonPath("$[0].price").value(expectedInternalColorDtos.get(0).getPrice()))
                    .andExpect(jsonPath("$[0].fabricImage").value(expectedInternalColorDtos.get(0).getFabricImage()))
                    .andExpect(jsonPath("$[0].bannerImage").value(expectedInternalColorDtos.get(0).getBannerImage()));
        }

        @Test
        @DisplayName("존재하지 않는 trim 요청에 대해서 NotFound로 응답한다.")
        void withInvalidTrim() throws Exception {
            //given
            Long trimId = Long.MAX_VALUE;
            when(trimService.findExternalColorInformation(trimId)).thenThrow(NoSuchTrimException.class);

            //when
            mockMvc.perform(get("/trim/{trimId}/external-color", trimId))
                    //then
                    .andExpect(status().isNotFound());
        }
    }

}
