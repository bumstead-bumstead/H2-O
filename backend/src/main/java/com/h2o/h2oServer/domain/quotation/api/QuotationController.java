package com.h2o.h2oServer.domain.quotation.api;

import com.h2o.h2oServer.domain.quotation.application.QuotationService;
import com.h2o.h2oServer.domain.quotation.dto.QuotationResponseDto;
import com.h2o.h2oServer.domain.quotation.dto.QuotationRequestDto;
import com.h2o.h2oServer.domain.quotation.dto.SimilarQuotationDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotation")
@RequiredArgsConstructor
public class QuotationController {

    private final QuotationService quotationService;

    @PostMapping
    public ResponseEntity<QuotationResponseDto> saveQuotation(@RequestBody QuotationRequestDto quotationRequestDto) {
        QuotationResponseDto quotationResponseDto = quotationService.saveQuotation(quotationRequestDto);
        return new ResponseEntity<>(quotationResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/similar")
    public List<SimilarQuotationDto> getSimilarQuotations(@RequestBody QuotationRequestDto quotationRequestDto) {
        return quotationService.findSimilarQuotations(quotationRequestDto);
    }
}
