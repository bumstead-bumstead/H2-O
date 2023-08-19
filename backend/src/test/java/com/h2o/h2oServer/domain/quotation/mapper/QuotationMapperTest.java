package com.h2o.h2oServer.domain.quotation.mapper;

import com.h2o.h2oServer.domain.quotation.dto.QuotationDto;
import com.h2o.h2oServer.domain.quotation.dto.QuotationRequestDto;
import com.h2o.h2oServer.domain.quotation.entity.OptionQuotationEntity;
import com.h2o.h2oServer.domain.quotation.entity.PackageQuotationEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.h2o.h2oServer.domain.quotation.QuotationFixture.generateQuotationRequestDto;

@MybatisTest
class QuotationMapperTest {

    @Autowired
    private QuotationMapper quotationMapper;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Nested
    @DisplayName("견적 저장 테스트")
    class saveQuotationsTest {
        @Test
        @DisplayName("견적을 저장하면 견적 id가 생성된다.")
        void saveQuotationReturnPK() {
            //given
            QuotationRequestDto request = generateQuotationRequestDto();
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
    }
}
