package com.plume.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    @ApiModelProperty(position = 1, value = "결과 코드")
    private int resultCode;

    @ApiModelProperty(position = 2, value = "결과 메세지")
    private String resultMessage;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(position = 3, value = "결과 요청 일시")
    private LocalDateTime resultDate;

    @ApiModelProperty(position = 4, value = "결과 데이터")
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

        @ApiModelProperty(position = 1, value = "결과 코드")
        private int resultCode;

        @ApiModelProperty(position = 2, value = "결과 메세지")
        private String resultMessage;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(position = 3, value = "결과 요청 일시")
        private LocalDateTime resultDate;

        @Builder
        public RestResultResponse(int resultCode, String resultMessage) {
            this.resultCode = (resultCode == 0 ? 200 : resultCode);
            //this.resultMessage = null == resultMessage ? MessageUtils.getMessage("message.success") : resultMessage;
            this.resultMessage = "success";
            this.resultDate = LocalDateTime.now();
        }
    }

}
