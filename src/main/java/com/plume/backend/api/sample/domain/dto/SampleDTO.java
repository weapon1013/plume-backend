package com.plume.backend.api.sample.domain.dto;

import com.plume.backend.api.sample.domain.entity.SampleEntity;
import com.plume.backend.api.sample.domain.enums.SampleEnum;
import com.plume.backend.api.sample.domain.vo.SampleVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SampleDTO {

    @Getter
    @Builder
    public static class SampleResponse {

        @ApiModelProperty(position = 1, value = "샘플_시퀀스")
        private long seq;

        @ApiModelProperty(position = 1, value = "제목")
        private String title;

        @ApiModelProperty(position = 1, value = "내용")
        private String content;

        @ApiModelProperty(position = 1, value = "샘플_컬럼_1")
        private String sampleColumn1;

        @ApiModelProperty(position = 1, value = "샘플_컬럼_2")
        private String sampleColumn2;

        @ApiModelProperty(position = 1, value = "샘플_타입")
        private SampleEnum type;

        @ApiModelProperty(position = 1, value = "생성_일자")
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

        @ApiModelProperty(position = 1, value = "샘플 리스트")
        private List<SampleResponse> items;

        public static SampleListResponse of(List<SampleResponse> items) {
            return SampleListResponse.builder()
                    .items(items)
                    .build();
        }
    }
}
