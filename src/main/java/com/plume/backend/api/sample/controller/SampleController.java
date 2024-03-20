package com.plume.backend.api.sample.controller;

import com.plume.backend.api.sample.domain.dto.SampleDTO;
import com.plume.backend.api.sample.service.SampleService;
import com.plume.common.response.RestResponse;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(tags = "Sample", value = "전체 샘플 목록 조회 (MyBatis)", notes = "전체 샘플 목록 조회 (MyBatis)")
    @GetMapping(value = "/samples/mybatis", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<SampleDTO.SampleListResponse>> getSampleList_mybatis() {

        SampleDTO.SampleListResponse response = sampleService.selectSampleList_mybatis();

        return ResponseEntity.ok(new RestResponse<>(response));
    }

    /**
     * 샘플 조회 (Jpa)
     * @return
     */
    @ApiOperation(tags = "Sample", value = "개별 샘플 조회 (Jpa)", notes = "개별 샘플 조회 (Jpa)")
    @GetMapping(value = "/samples/jpa/{seq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<SampleDTO.SampleResponse>> getSampleList_jpa(@PathVariable("seq") long seq) {

        SampleDTO.SampleResponse response = sampleService.selectSampleList_jpa(seq);

        return ResponseEntity.ok(new RestResponse<>(response));
    }
}
