package com.h2o.h2oServer.domain.trim.mapper;

import com.h2o.h2oServer.domain.trim.TrimEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrimMapper {
    TrimEntity findById(Long id);

    List<TrimEntity> findByCarId(Long id);

    List<TrimEntity> findAll();

    List<String> showTables();
}
