package com.plume.backend.api.sample.domain.record;

import com.plume.backend.api.sample.domain.enums.SampleEnum;

import java.time.LocalDateTime;

public record SampleRecord(

        long seq,
        String title,
        String content,
        String sampleColumn1,
        String sampleColumn2,
        SampleEnum type,
        LocalDateTime regDt
) {}
