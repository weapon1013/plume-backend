package com.plume.common.annotation;

import com.plume.common.response.RestResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = RestResponse.class))),
//        @ApiResponse(responseCode = "400", description = "Bad Request Operation", content = @Content(schema = @Schema(implementation = RestResponse.class)))
        @ApiResponse(responseCode = "200", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "Bad Request Operation")
})
public @interface ResponseOperationStatus {
}
