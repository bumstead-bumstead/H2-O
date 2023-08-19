package com.h2o.h2oServer.domain.quotation.mapper;

import com.h2o.h2oServer.domain.model_type.dto.ModelTypeIdDto;
import com.h2o.h2oServer.domain.quotation.dto.QuotationDto;
import com.h2o.h2oServer.domain.quotation.dto.QuotationRequestDto;
import com.h2o.h2oServer.domain.quotation.entity.OptionQuotationEntity;
import com.h2o.h2oServer.domain.quotation.entity.PackageQuotationEntity;
import com.h2o.h2oServer.domain.quotation.entity.ReleaseEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class QuotationMapperTest {

    @Autowired
    private QuotationMapper quotationMapper;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("견적을 저장하면 견적 id가 생성된다.")
    void saveQuotationReturnPK() {
        //given
        QuotationRequestDto request = createMockRequest();
        QuotationDto quotationDto1 = QuotationDto.of(request);
        QuotationDto quotationDto2 = QuotationDto.of(request);

        //when
        quotationMapper.saveQuotation(quotationDto1);
        quotationMapper.saveQuotation(quotationDto2);

        //then
        softly.assertThat(quotationDto1.getId()).as("id가 생성된다.")
                .isNotNull();
        softly.assertThat(quotationDto2.getId()).as("AUTO INCREMENT로 id가 정해진다.")
                .isEqualTo(2L);
        softly.assertAll();
    }

    @Test
    @DisplayName("옵션 ID 리스트를 테이블에 저장하면 리스트의 개수만큼 레코드가 생성된다.")
    void saveOptionQuotation() {
        //given
        OptionQuotationEntity optionQuotationEntity = OptionQuotationEntity.builder()
                .quotationId(1L)
                .optionIds(List.of(2L, 3L, 4L))
                .build();

        //when
        quotationMapper.saveOptionQuotation(optionQuotationEntity);

        //then
        softly.assertThat(quotationMapper.countOptionQuotation())
                .isEqualTo(3L);
    }

    @Test
    @DisplayName("패키지 ID 리스트를 테이블에 저장하면 리스트의 개수만큼 레코드가 생성된다.")
    void savePackageQuotation() {
        //given
        PackageQuotationEntity packageQuotationEntity = PackageQuotationEntity.builder()
                .quotationId(1L)
                .packageIds(List.of(5L, 6L))
                .build();

        //when
        quotationMapper.savePackageQuotation(packageQuotationEntity);

        //then
        softly.assertThat(quotationMapper.countPackageQuotation())
                .isEqualTo(2L);
    }

    private QuotationRequestDto createMockRequest() {
        ModelTypeIdDto modelTypeId = new ModelTypeIdDto();
        modelTypeId.setPowertrainId(1L);
        modelTypeId.setBodytypeId(2L);
        modelTypeId.setDrivetrainId(3L);

        QuotationRequestDto request = new QuotationRequestDto();
        request.setCarId(4L);
        request.setTrimId(5L);
        request.setModelTypeIds(modelTypeId);
        request.setInternalColorId(6L);
        request.setExternalColorId(7L);
        request.setOptionIds(List.of(8L, 9L, 10L));
        request.setPackageIds(List.of(11L));

        return request;
    }
    
    @Test
    @DisplayName("동일한 출고 견적의 데이터와 갯수 정보를 반환한다.")
    @Sql("classpath:db/quotation/release-data.sql")
    void findReleaseQuotationWithVolume() {
        //given
        long trimId = 2L;
        ReleaseEntity expectedReleaseEntity = ReleaseEntity.builder()
                .powertrainId(2L)
                .bodytypeId(2L)
                .drivetrainId(2L)
                .internalColorId(2L)
                .externalColorId(2L)
                .price(35000)
                .trimId(2L)
                .quotationCount(2)
                .optionCombination("1,4")
                .packageCombination("3")
                .build();
        ReleaseEntity expectedReleaseEntity2 = ReleaseEntity.builder()
                .powertrainId(1L)
                .bodytypeId(1L)
                .drivetrainId(1L)
                .internalColorId(1L)
                .externalColorId(1L)
                .price(30000)
                .trimId(2L)
                .quotationCount(1)
                .optionCombination("1")
                .packageCombination("1,2")
                .build();
        //when
        List<ReleaseEntity> actualReleaseEntities = quotationMapper.findReleaseQuotationWithVolume(trimId);

        //then
        assertThat(actualReleaseEntities).contains(expectedReleaseEntity)
                .contains(expectedReleaseEntity2);
    }
}
