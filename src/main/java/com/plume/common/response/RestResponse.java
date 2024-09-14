package com.plume.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    @Schema(description = "결과 코드")
    private int resultCode;

    @Schema(description = "결과 메세지")
    private String resultMessage;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "결과 요청 일시")
    private LocalDateTime resultDate;

    @Schema(description = "결과 데이터")
    private T data;

    public RestResponse(T data) {
        this.resultCode = 200;
        this.resultMessage = "success";
        this.resultDate = LocalDateTime.now();
        this.data = data;
    }

    @Getter
    @Setter
    public static class RestResultResponse {

        @Schema(description = "결과 코드")
        private int resultCode;

        @Schema(description = "결과 메세지")
        private String resultMessage;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "결과 요청 일시")
        private LocalDateTime resultDate;

        @Builder
        public RestResultResponse() {
            this.resultCode = 200;
            //this.resultMessage = null == resultMessage ? MessageUtils.getMessage("message.success") : resultMessage;
            this.resultMessage = "success";
            this.resultDate = LocalDateTime.now();
        }

        @Builder
        public RestResultResponse(int resultCode, String resultMessage) {
            this.resultCode = (resultCode == 0 ? 200 : resultCode);
            //this.resultMessage = null == resultMessage ? MessageUtils.getMessage("message.success") : resultMessage;
            this.resultMessage = "success";
            this.resultDate = LocalDateTime.now();
        }
    }

}
