package com.h2o.h2oServer.domain.trim.mapper;

import com.h2o.h2oServer.domain.trim.ExternalColorFixture;
import com.h2o.h2oServer.domain.trim.InternalColorFixture;
import com.h2o.h2oServer.domain.trim.TrimFixture;
import com.h2o.h2oServer.domain.trim.entity.ExternalColorEntity;
import com.h2o.h2oServer.domain.trim.entity.InternalColorEntity;
import com.h2o.h2oServer.domain.trim.entity.TrimEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
class TrimMapperTest {

    @Autowired
    private TrimMapper trimMapper;

    private SoftAssertions softly;

    @BeforeEach
    void setup() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("존재하는 회원에 대해서, 해당하는 row를 Trim 객체에 담아 반환한다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void findById() {
        //given
        Long targetId = 1L;
        TrimEntity expectedTrimEntity = TrimFixture.generateTrimEntity();

        //when
        TrimEntity actualTrimEntity = trimMapper.findById(targetId);

        //then
        softly.assertThat(actualTrimEntity).as("유효한 데이터가 매핑되었는지 확인").isNotNull();
        softly.assertThat(actualTrimEntity).as("데이터베이스에 존재하는 데이터인지 확인").isEqualTo(expectedTrimEntity);
        softly.assertAll();
    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해서는 null을 반환한다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void findByIdWithoutResult() {
        //given
        Long targetId = Long.MAX_VALUE;

        //when
        TrimEntity actualTrimEntity = trimMapper.findById(targetId);

        //then
        assertThat(actualTrimEntity).isNull();
    }

    @Test
    @DisplayName("존재하는 회원에 대해서, 해당하는 row를 Trim 객체에 담아 반환한다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void findByCarId() {
        //given
        Long targetCarId = 1L;
        List<TrimEntity> expectedTrimEntities = TrimFixture.generateTrimEntityList();

        //when
        List<TrimEntity> actualTrimEntities = trimMapper.findByCarId(targetCarId);

        //then
        softly.assertThat(actualTrimEntities).as("유효한 데이터가 매핑되었는지 확인").isNotEmpty();
        softly.assertThat(actualTrimEntities).as("유효한 데이터만 매핑되었는지 확인").hasSize(2);
        softly.assertThat(actualTrimEntities).as("CarId에 해당하는 trim 객체가 모두 매핑되었는지 확인")
                .containsAll(expectedTrimEntities);
        softly.assertAll();
    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해서는 null을 반환한다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void findByCarIdWithoutResult() {
        //given
        Long targetCarId = Long.MAX_VALUE;

        //when
        List<TrimEntity> actualTrimEntities = trimMapper.findByCarId(targetCarId);

        //then
        assertThat(actualTrimEntities).isEmpty();
    }

    @Test
    @DisplayName("데이터베이스 상의 모든 row를 가져온다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void findAll() {
        //given
        Long expectedLength = 10L;

        //when
        List<TrimEntity> actualTrimEntities = trimMapper.findAll();

        //then
        assertThat(actualTrimEntities).as("db에 존재하는 row의 개수와 길이가 같은지 확인").hasSize(Math.toIntExact(expectedLength));
    }

    @Test
    @DisplayName("유효한 trim에 대해서, externalColorEntity를 반환한다.")
    @Sql("classpath:db/trim/external-color-data.sql")
    void findExternalColor() {
        //given
        Long trimId = 1L;
        List<ExternalColorEntity> expectedExternalColorEntities = ExternalColorFixture.generateExternalColorEntityList();

        //when
        List<ExternalColorEntity> actualExternalColorEntities = trimMapper.findExternalColor(trimId);

        //then
        assertThat(actualExternalColorEntities).as("유효한 데이터가 매핑된다.").isNotEmpty();
        assertThat(actualExternalColorEntities).as("유효한 데이터가 매핑된다.").hasSize(2);
        softly.assertThat(actualExternalColorEntities).as("trimId에 해당하는 externalColorEntity 객체가 모두 매핑되었는지 확인")
                .containsAll(expectedExternalColorEntities);
        softly.assertAll();
    }

    @Test
    @DisplayName("존재하는 trimId에 대한 내부 색상 요청에 대해서 InternalColorEntity List를 반환한다. ")
    @Sql("classpath:db/trim/internal-color-data.sql")
    void findInternalColor() {
        //given
        Long trimId = 1L;
        List<InternalColorEntity> expectedInternalColorEntities = InternalColorFixture.generateInernalColorEntityList();

        //when
        List<InternalColorEntity> actualEntities = trimMapper.findInternalColor(trimId);

        //then
        softly.assertThat(actualEntities).as("유효한 데이터가 매핑되었는지 확인").isNotEmpty();
        softly.assertThat(actualEntities).as("유효한 데이터만 매핑되었는지 확인").hasSize(2);
        softly.assertThat(actualEntities).as("trimId에 해당하는 InternalColorEntity 객체가 모두 매핑되었는지 확인")
                .containsAll(expectedInternalColorEntities);
        softly.assertAll();
    }

    @Test
    @DisplayName("트림이 가질 수 있는 패키지/추가 옵션 가격의 합을 반환한다.")
    @Sql("classpath:db/trim/trims-option-data.sql")
    void findMaximumComponentPrice() {
        //given
        Long trimId = 1L;
        Integer expectedPrice = 4890;

        //when
        Integer actualPrice = trimMapper.findMaximumComponentPrice(trimId);

        //then
        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("존재하는 트림인 경우 true를 반환한다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void checkIfTrimExists() {
        //given
        Long id = 1L;

        //when
        Boolean isExists = trimMapper.checkIfTrimExists(id);

        //then
        assertThat(isExists).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 트림인 경우 false를 반환한다.")
    @Sql("classpath:db/trim/trims-data.sql")
    void checkIfTrimExistsFalse() {
        //given
        Long id = 11L;

        //when
        Boolean isExists = trimMapper.checkIfTrimExists(id);

        //then
        assertThat(isExists).isFalse();
    }

    @Test
    @DisplayName("가격 범위 내의 출고 개수만을 반환한다.")
    @Sql("classpath:db/trim/sold-car-data.sql")
    void findQuantityBetween() {
        //given
        Long trimId = 1L;
        int from = 30000;
        int to = 70000;
        int expectedResult = 9;

        //when
        Integer actualResult = trimMapper.findQuantityBetween(trimId, from, to);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
