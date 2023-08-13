package com.h2o.h2oServer.domain.model_type.mapper;

import com.h2o.h2oServer.domain.model_type.Entity.BodytypeEntity;
import com.h2o.h2oServer.domain.model_type.Entity.CarBodytypeEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@MybatisTest
@Sql("classpath:db/bodytype-data.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BodytypeMapperTest {

    @Autowired
    BodytypeMapper bodytypeMapper;

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @Order(2)
    @DisplayName("존재하는 바디타입을 조회하면 해당 데이터를 BodytypeEntity로 반환한다.")
    void findById() {
        //given
        Long bodytypeId = 1L;
        BodytypeEntity bodytype = BodytypeEntity.builder()
                .id(bodytypeId)
                .name("name1")
                .description("description1")
                .image("img_url1")
                .build();

        //when
        BodytypeEntity foundBodytype = bodytypeMapper.findById(bodytypeId);

        //then
        softly.assertThat(foundBodytype).as("유효한 데이터가 매핑되었는지 확인")
                .isEqualTo(bodytype);
    }

    @Test
    @Order(1)
    @DisplayName("특정 차량의 바디타입을 조회하면 해당 차량에 적용 가능한 한 모든 바디타입을 CarBodytypeEntity의 리스트로 반환한다.")
    void findBodytypeByCarId() {
        //given
        Long carId = 1L;
        CarBodytypeEntity entity1 = CarBodytypeEntity.builder()
                .carId(carId)
                .name("name1")
                .description("description1")
                .image("img_url1")
                .bodytypeId(1L)
                .price(10000)
                .choiceRatio(0.11f)
                .build();

        CarBodytypeEntity entity2 = CarBodytypeEntity.builder()
                .carId(carId)
                .name("name2")
                .description("description2")
                .image("img_url2")
                .bodytypeId(2L)
                .price(20000)
                .choiceRatio(0.21f)
                .build();

        //when
        List<CarBodytypeEntity> foundEntities = bodytypeMapper.findBodytypesByCarId(carId);

        //then
        softly.assertThat(foundEntities).as("유효한 데이터가 매핑되었는지 확인")
                .isNotEmpty();
        softly.assertThat(foundEntities).as("유효한 데이터만 매핑되었는지 확인")
                .hasSize(2);
        softly.assertThat(foundEntities).as("carId에 해당하는 Entity가 모두 매핑되었는지 확인")
                .contains(entity1)
                .contains(entity2);

        softly.assertAll();
    }
}
