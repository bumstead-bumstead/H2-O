package com.h2o.h2oServer.domain.trim.api;

import com.h2o.h2oServer.domain.trim.application.TrimService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TrimController.class)
class TrimControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private TrimService trimService;

    @BeforeEach
    void setup() {
        trimService = Mockito.mock(TrimService.class);
    }

//    @Nested
//    @DisplayName("트림 정보 조회 테스트")
//    class getTrimInformationTest {

//        @Test
//        @DisplayName("존재하는 trim 요청에 대해서 유효한 값을 갖는 TrimDto를 응답한다.")
//        void withValidTrimId() {
//            //given
////            mockMvc.perform(MockMvcRequestBuilders.get("car/{carId}/trim", 2L))
////                    .andExpect(MockMvcResultMatchers.status().isOk())
////                    .andExpect(MockMvcResultMatchers.content().string("Expected Response Body"));
//            //when
//
//            //then
//        }
//    }

}
