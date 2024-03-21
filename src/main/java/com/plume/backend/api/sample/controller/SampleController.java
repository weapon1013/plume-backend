package com.plume.backend.api.sample.controller;

import com.plume.backend.api.sample.domain.dto.SampleDTO;
import com.plume.backend.api.sample.service.SampleService;
import com.plume.common.annotation.ResponseOperationStatus;
import com.plume.common.response.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@Tag(name = "Sample", description = "샘플 테이블 조회")
@RestController("Api_SampleController")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    /**
     * 샘플 리스트 조회 (MyBatis)
     * @return
     */
    @ResponseOperationStatus
    @Operation(summary = "전체 샘플 목록 조회 (Mybatis)", description = "전체 샘플 목록 조회 API - Mybatis")
    @GetMapping(value = "/sample/mybatis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<SampleDTO.SampleListResponse>> getSampleList_mybatis() {

        SampleDTO.SampleListResponse response = sampleService.selectSampleList_mybatis();

        return ResponseEntity.ok(new RestResponse<>(response));
    }

    /**
     * 샘플 조회 (Jpa)
     * @return
     */
    @ResponseOperationStatus
    @Operation(summary = "개별 샘플 조회 (Jpa)", description = "개별 샘플 조회 API - Jpa")
    @GetMapping(value = "/sample/jpa/{seq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<SampleDTO.SampleResponse>> getSampleList_jpa(
            @Parameter(description = "샘플 시퀀스") @PathVariable("seq") long seq) {

        SampleDTO.SampleResponse response = sampleService.selectSampleList_jpa(seq);

        return ResponseEntity.ok(new RestResponse<>(response));
    }
}
