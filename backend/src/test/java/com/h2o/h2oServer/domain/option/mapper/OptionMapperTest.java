package com.h2o.h2oServer.domain.option.mapper;

import com.h2o.h2oServer.domain.option.entity.OptionEntity;
import com.h2o.h2oServer.domain.option.entity.enums.OptionCategory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
class OptionMapperTest {

    @Autowired
    private OptionMapper optionMapper;
    private SoftAssertions softly = new SoftAssertions();

    @Test
    @DisplayName("존재하는 trim, option에 대해서 유효한 OptionEntity 객체를 반환한다.")
    @Sql("classpath:db/option-data.sql")
    void findOption() {
        //given
        Long trimId = 1L;
        Long optionId = 1L;
        OptionEntity expectedOptionEntity = OptionEntity.builder()
                .name("Option 1")
                .image("image_url_1")
                .description("Description for Option 1")
                .choiceRatio(0.3f)
                .useCount(12.5f)
                .category(OptionCategory.POWERTRAIN_PERFORMANCE)
                .build();

        //when
        OptionEntity actualOptionEntity = optionMapper.findOption(optionId, trimId);

        //then
        softly.assertThat(actualOptionEntity).as("유효한 데이터가 매핑되었는지 확인").isNotNull();
        softly.assertThat(actualOptionEntity).as("데이터베이스에 존재하는 데이터인지 확인").isEqualTo(expectedOptionEntity);
        softly.assertAll();
    }
}
