package com.h2o.h2oServer.domain.option.application;

import com.h2o.h2oServer.domain.option.OptionFixture;
import com.h2o.h2oServer.domain.option.dto.OptionDetailsDto;
import com.h2o.h2oServer.domain.option.dto.OptionStatisticsDto;
import com.h2o.h2oServer.domain.option.entity.enums.OptionCategory;
import com.h2o.h2oServer.domain.option.mapper.OptionMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.h2o.h2oServer.domain.option.HashTagFixture.generateHashTagEntities;
import static org.mockito.Mockito.when;

class OptionServiceTest {
    private static OptionMapper optionMapper;
    private static OptionService optionService;
    private static SoftAssertions softly;

    @BeforeAll
    static void beforeAll() {
        optionMapper = Mockito.mock(OptionMapper.class);
        optionService = new OptionService(optionMapper);
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("존재하는 option, trim에 대한 요청인 경우 Dto로 formatting해서 반환한다.")
    void findOptionInformation() {
        //given
        long optionId = 1L;
        long trimId = 1L;
        when(optionMapper.findOptionDetails(optionId, trimId)).thenReturn(OptionFixture.generateOptionDetailsEntity());
        when(optionMapper.findHashTag(optionId)).thenReturn(generateHashTagEntities());

        //when
        OptionDetailsDto actualOptionDetailsDto = optionService.findOptionInformation(optionId, trimId);

        //then
        softly.assertThat(actualOptionDetailsDto).as("null이 아니다.").isNotNull();
        softly.assertThat(actualOptionDetailsDto.getHashTags()).as("세 개의 hashtag 정보를 포함한다.").hasSize(3);
        softly.assertThat(actualOptionDetailsDto.getName()).as("name = Option 1이다.").isEqualTo("Option 1");
        softly.assertThat(actualOptionDetailsDto.getCategory()).as("category = 성능/파워트레인").isEqualTo(OptionCategory.POWERTRAIN_PERFORMANCE.getLabel());
        softly.assertThat(actualOptionDetailsDto.getHmgData()).as("유효한 hmgData를 포함한다.").isEqualTo(OptionStatisticsDto.of(0.3f, 13));
        softly.assertAll();
    }
}
