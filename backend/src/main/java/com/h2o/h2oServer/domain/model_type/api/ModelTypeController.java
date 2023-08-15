package com.h2o.h2oServer.domain.model_type.api;

import com.h2o.h2oServer.domain.model_type.application.ModelTypeService;
import com.h2o.h2oServer.domain.model_type.dto.ModelTypeDto;
import com.h2o.h2oServer.domain.model_type.dto.TechnicalSpecDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags = "모델 타입")
public class ModelTypeController {

    private final ModelTypeService modelTypeService;

    @ApiOperation(value = "차량 모델 타입 정보 조회", notes = "car_id를 기준으로 가능한 모델타입 정보를 반환하는 API")
    @GetMapping("/car/{carId}/model-type")
    public ModelTypeDto modelType(@PathVariable Long carId) {
        return modelTypeService.findModelTypes(carId);
    }

    @GetMapping("/technical-spec")
    @ApiOperation(value = "차량 성능 정보 조회", notes = "구동 방식, 파워트레인에 대한 배기량, 연비 정보를 반환하는 API")
    public TechnicalSpecDto technicalSpec(@RequestParam Long powertrainId, @RequestParam Long drivetrainId) {
        return modelTypeService.findTechnicalSpec(powertrainId, drivetrainId);
    }
}
