package com.h2o.h2oServer.domain.option.mapper;

import com.h2o.h2oServer.domain.option.entity.HashTagEntity;
import com.h2o.h2oServer.domain.option.entity.OptionDetailsEntity;
import com.h2o.h2oServer.domain.option.entity.OptionEntity;
import com.h2o.h2oServer.domain.options.dto.PageRangeDto;
import com.h2o.h2oServer.domain.options.entity.TrimDefaultOptionEntity;
import com.h2o.h2oServer.domain.options.entity.TrimExtraOptionEntity;
import com.h2o.h2oServer.domain.options.enums.OptionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OptionMapper {
    Optional<OptionDetailsEntity> findOptionDetails(@Param("id") Long id, @Param("trimId") Long trimId);

    Optional<OptionEntity> findOption(Long id);

    List<HashTagEntity> findHashTag(Long id);

    List<TrimExtraOptionEntity> findTrimPackages(Long trimId);

    List<TrimExtraOptionEntity> findTrimPackagesWithRange(@Param("trimId") Long trimId,
                                                          @Param("pageRange") PageRangeDto pageRange);

    List<TrimExtraOptionEntity> findTrimExtraOptions(Long trimId);

    List<TrimExtraOptionEntity> findTrimExtraOptionsWithRange(@Param("trimId") Long trimId,
                                                              @Param("pageRange") PageRangeDto pageRange);

    List<TrimDefaultOptionEntity> findTrimDefaultOptions(Long trimId);

    List<TrimDefaultOptionEntity> findTrimDefaultOptionsWithRange(@Param("trimId") Long trimId,
                                                                  @Param("pageRange") PageRangeDto pageRange);

    List<HashTagEntity> findPackageHashTags(Long packageId);

    List<HashTagEntity> findOptionHashTag(Long optionId);

    Long findTrimPackageSize(Long trimId);

    Long findTrimOptionSize(@Param("trimId") Long trimId, @Param("optionType") OptionType optionType);

    Boolean checkIfOptionExists(Long id);
}
