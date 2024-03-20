package com.plume.backend.api.sample.service;

import com.plume.backend.api.sample.domain.dto.SampleDTO;
import com.plume.backend.api.sample.domain.entity.SampleEntity;
import com.plume.backend.api.sample.domain.vo.SampleVO;
import com.plume.backend.api.sample.repository.jpa.SampleJpaRepository;
import com.plume.backend.api.sample.repository.mybatis.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    private final SampleJpaRepository sampleJpaRepository;

    public SampleDTO.SampleListResponse selectSampleList_mybatis() {

        List<SampleVO> list = sampleRepository.selectSampleList();

        return SampleDTO.SampleListResponse.of(list.stream().map(SampleDTO.SampleResponse::of).collect(Collectors.toList()));
    }

    public SampleDTO.SampleResponse selectSampleList_jpa(long seq) {

        SampleEntity entity = sampleJpaRepository.findBySeqAndDelYn(seq, "N");

        return SampleDTO.SampleResponse.of(entity);
    }
}
