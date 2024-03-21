package com.plume.backend.api.sample.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plume.backend.api.sample.domain.entity.SampleEntity;
import com.plume.backend.api.sample.domain.enums.SampleEnum;
import com.plume.backend.api.sample.domain.vo.SampleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SampleDTO {

    @Getter
    @Builder
    public static class SampleResponse {

        @Schema(description = "샘플_시퀀스")
        private long seq;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "내용")
        private String content;

        @Schema(description = "샘플_컬럼_1")
        private String sampleColumn1;

        @Schema(description = "샘플_컬럼_2")
        private String sampleColumn2;

        @Schema(description = "샘플_타입")
        private SampleEnum type;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "생성_일자")
        private LocalDateTime regDt;

        // SampleVO -> SampleDTO
        public static SampleResponse of(SampleVO sampleVO) {
            return SampleResponse.builder()
                    .seq(sampleVO.getSeq())
                    .title(sampleVO.getTitle())
                    .content(sampleVO.getContent())
                    .sampleColumn1(sampleVO.getSampleColumn1())
                    .sampleColumn2(sampleVO.getSampleColumn2())
                    .type(sampleVO.getType())
                    .regDt(sampleVO.getRegDt())
                    .build();
        }

        public static SampleResponse of(SampleEntity sampleEntity) {
            return SampleResponse.builder()
                    .seq(sampleEntity.getSeq())
                    .title(sampleEntity.getTitle())
                    .content(sampleEntity.getContent())
                    .sampleColumn1(sampleEntity.getSampleColumn1())
                    .sampleColumn2(sampleEntity.getSampleColumn2())
                    .type(sampleEntity.getType())
                    .regDt(sampleEntity.getRegDt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class SampleListResponse {

        @Schema(description = "샘플 리스트")
        private List<SampleResponse> items;

        public static SampleListResponse of(List<SampleResponse> items) {
            return SampleListResponse.builder()
                    .items(items)
                    .build();
        }
    }
}
