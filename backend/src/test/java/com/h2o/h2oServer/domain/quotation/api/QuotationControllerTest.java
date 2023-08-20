package com.h2o.h2oServer.domain.quotation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h2o.h2oServer.domain.car.exception.NoSuchCarException;
import com.h2o.h2oServer.domain.model_type.exception.NoSuchBodyTypeException;
import com.h2o.h2oServer.domain.model_type.exception.NoSuchDriveTrainException;
import com.h2o.h2oServer.domain.model_type.exception.NoSuchPowertrainException;
import com.h2o.h2oServer.domain.option.exception.NoSuchOptionException;
import com.h2o.h2oServer.domain.optionPackage.exception.NoSuchPackageException;
import com.h2o.h2oServer.domain.quotation.QuotationFixture;
import com.h2o.h2oServer.domain.quotation.application.QuotationService;
import com.h2o.h2oServer.domain.quotation.dto.QuotationRequestDto;
import com.h2o.h2oServer.domain.quotation.dto.QuotationResponseDto;
import com.h2o.h2oServer.domain.trim.Exception.NoSuchTrimException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuotationController.class)
@AutoConfigureMockMvc
class QuotationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    QuotationService quotationService;

    @Nested
    @DisplayName("견적 저장 테스트")
    class SaveQuotationTest {
        @Test
        @DisplayName("유효한 요청에 대해서 CREATED를 응답한다.")
        void withValidRequest() throws Exception {
            //given
            long expectedId = 1L;
            QuotationRequestDto requestDto = QuotationFixture.generateQuotationRequestDto();
            String jsonBody = objectMapper.writeValueAsString(requestDto);
            when(quotationService.saveQuotation(requestDto)).thenReturn(QuotationResponseDto.of(expectedId));

            //when
            mockMvc.perform(post("/quotation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBody))
                    //then
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.quotationId").value(expectedId));
        }

        @ParameterizedTest
        @DisplayName("유효하지 않은 id를 포함하는 경우 NotFound로 응답한다.")
        @ValueSource(classes = {
                NoSuchPackageException.class,
                NoSuchDriveTrainException.class,
                NoSuchPowertrainException.class,
                NoSuchBodyTypeException.class,
                NoSuchCarException.class,
                NoSuchTrimException.class,
                NoSuchOptionException.class,
                NoSuchPackageException.class
        })        void withInvalidRequest(Class<? extends RuntimeException> exceptionType) throws Exception {
            //given
            QuotationRequestDto requestDto = QuotationFixture.generateQuotationRequestDto();
            String jsonBody = objectMapper.writeValueAsString(requestDto);
            when(quotationService.saveQuotation(requestDto)).thenThrow(exceptionType);

            //when
            mockMvc.perform(post("/quotation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonBody))
                    //then
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void saveQuotation() {
    }

    @Nested
    @DisplayName("유사 견적 조회 테스트")
    class getSimilarQuotationsTest {

    }

}
