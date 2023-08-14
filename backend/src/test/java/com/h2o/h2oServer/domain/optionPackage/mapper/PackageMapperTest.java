package com.h2o.h2oServer.domain.optionPackage.mapper;

import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.enums.HashTag;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@MybatisTest
class PackageMapperTest {

    @Autowired
    PackageMapper packageMapper;
    SoftAssertions softly = new SoftAssertions();

    @Test
    void findPackage() {
    }

    @Test
    @DisplayName("존재하는 option에 대해서 유효한 HashTagEntity 객체를 반환한다.")
    @Sql("classpath:db/package-hashtag-data.sql")
    void findHashTag() {
        //given
        Long packageId = 1L;
        List<HashTagEntity> expectedHashTagEntities = List.of(
                HashTagEntity.builder()
                        .name(HashTag.CAMPING)
                        .build(),
                HashTagEntity.builder()
                        .name(HashTag.LEISURE)
                        .build(),
                HashTagEntity.builder()
                        .name(HashTag.SPORTS)
                        .build()
        );
        //when
        List<HashTagEntity> actualHashTagEntities = packageMapper.findHashTag(packageId);

        //then
        softly.assertThat(actualHashTagEntities).as("유효한 데이터가 매핑되었는지 확인").isNotEmpty();
        softly.assertThat(actualHashTagEntities).as("packageId에 해당하는 HashTagEntity 객체가 모두 매핑되었는지 확인")
                .contains(expectedHashTagEntities.get(0))
                .contains(expectedHashTagEntities.get(1))
                .contains(expectedHashTagEntities.get(2));
        softly.assertAll();
    }
}
