package com.h2o.h2oServer.domain.trim.application;

import com.h2o.h2oServer.domain.trim.dto.ExternalColorDto;
import com.h2o.h2oServer.domain.trim.dto.TrimDto;
import com.h2o.h2oServer.domain.trim.entity.ExternalColorEntity;
import com.h2o.h2oServer.domain.trim.entity.ImageEntity;
import com.h2o.h2oServer.domain.trim.entity.OptionStatisticsEntity;
import com.h2o.h2oServer.domain.trim.entity.TrimEntity;
import com.h2o.h2oServer.domain.trim.mapper.ExternalColorMapper;
import com.h2o.h2oServer.domain.trim.mapper.TrimMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class TrimServiceTest {

    private static TrimMapper trimMapper;
    private static ExternalColorMapper externalColorMapper;
    private static TrimService trimService;
    private static SoftAssertions softly;

    @BeforeAll
    static void beforeAll() {
        trimMapper = Mockito.mock(TrimMapper.class);
        externalColorMapper = Mockito.mock(ExternalColorMapper.class);
        trimService = new TrimService(trimMapper, externalColorMapper);
        softly = new SoftAssertions();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("존재하는 vehicle에 대한 요청인 경우 Dto로 formatting해서 반환한다.")
    void findTrimInformation() {
        //given
        Long vehicleId = 1L;
        when(trimMapper.findByCarId(vehicleId)).thenReturn(generateTrimEntityList());
        when(trimMapper.findOptionStatistics(1L)).thenReturn(generateOptionStatisticsList());
        when(trimMapper.findOptionStatistics(2L)).thenReturn(generateOptionStatisticsList());
        when(trimMapper.findImages(1L)).thenReturn(generateImageEntityList());
        when(trimMapper.findImages(2L)).thenReturn(generateImageEntityList());

        //when
        List<TrimDto> actualTrimDtos = trimService.findTrimInformation(vehicleId);

        //then
        softly.assertThat(actualTrimDtos).as("null이 아니다.").isNotNull();
        softly.assertThat(actualTrimDtos).as("TrimDto를 포함한다.").isNotEmpty();
        softly.assertThat(actualTrimDtos.get(0).getImages()).as("Images를 포함한다.").isNotNull();
        softly.assertThat(actualTrimDtos.get(0).getOptions()).as("Options를 포함한다.").isNotNull();
        softly.assertAll();
    }

    @Test
    @DisplayName("존재하지 않는 vehicle에 대한 요청인 경우 빈 Dto를 반한환다.")
    void findTrimInformationNotExist() {
        //given
        Long vehicleId = Long.MAX_VALUE;
        when(trimMapper.findByCarId(vehicleId)).thenReturn(List.of());

        //when
        List<TrimDto> actualTrimDtos = trimService.findTrimInformation(vehicleId);

        //then
        softly.assertThat(actualTrimDtos).as("null이 아니다.").isNotNull();
        softly.assertThat(actualTrimDtos).as("TrimDto를 포함한다.").isEmpty();
        softly.assertAll();
    }

    private static List<ImageEntity> generateImageEntityList() {
        return List.of(
                ImageEntity.builder()
                        .image("url1")
                        .id(1L)
                        .build(),
                ImageEntity.builder()
                        .image("url2")
                        .id(2L)
                        .build()
        );
    }

    private List<OptionStatisticsEntity> generateOptionStatisticsList() {
        return List.of(
                OptionStatisticsEntity.builder()
                        .id(1L)
                        .name("Option A")
                        .useCount(0.75F)
                        .build(),
                OptionStatisticsEntity.builder()
                        .id(2L)
                        .name("Option B")
                        .useCount(0.5F)
                        .build()
        );
    }

    private List<TrimEntity> generateTrimEntityList() {
        return List.of(
                TrimEntity.builder()
                        .id(1L)
                        .name("Trim A")
                        .description("Description of Trim A")
                        .price(50000)
                        .build(),
                TrimEntity.builder()
                        .id(2L)
                        .name("Trim B")
                        .description("Description of Trim b")
                        .price(70000)
                        .build()
        );
    }
}
