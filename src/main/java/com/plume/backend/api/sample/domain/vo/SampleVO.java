package com.plume.backend.api.sample.domain.vo;

import com.plume.backend.api.sample.domain.enums.SampleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SampleVO {

    private long seq;
    private String title;
    private String content;
    private String sampleColumn1;
    private String sampleColumn2;
    private SampleEnum type;
    private LocalDateTime regDt;

    @Builder
    public SampleVO(long seq, String title, String content, String sampleColumn1, String sampleColumn2, SampleEnum type, LocalDateTime regDt) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.sampleColumn1 = sampleColumn1;
        this.sampleColumn2 = sampleColumn2;
        this.type = type;
        this.regDt = regDt;
    }
}
