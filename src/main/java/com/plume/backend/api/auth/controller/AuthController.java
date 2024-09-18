package com.plume.backend.api.auth.controller;

import com.plume.backend.api.auth.domain.dto.AuthDTO;
import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.domain.entity.MailEntity;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.api.auth.repository.jpa.MailRepository;
import com.plume.backend.api.auth.service.AuthService;
import com.plume.common.response.RestResponse;
import com.plume.common.util.MailUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Optional;

@RequestMapping("/api/v1/auth")
@RestController("Api_UserController")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse.RestResultResponse> sign(@RequestBody AuthDTO.SignRequest request) {

        authService.sign(request.toEntity());

        return ResponseEntity.ok(new RestResponse.RestResultResponse());
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<AuthDTO.TokenResponse>> login(
            @RequestBody @Valid AuthDTO.LoginRequest requestBody
    ) {

        AuthDTO.TokenResponse response = authService.login(requestBody.toVO());
        return ResponseEntity.ok(new RestResponse<>(response));
    }

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<AuthDTO.CheckResponse>> check(
            @RequestParam("checkStr") String checkStr,
            @RequestParam("type") String type
    ) {

        AuthDTO.CheckResponse response = authService.idOrNicknameCheck(checkStr, type);
        return ResponseEntity.ok(new RestResponse<>(response));
    }

    @PostMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse.RestResultResponse> redis(@RequestBody AuthDTO.MailRequest request) throws Exception {

        boolean check = authService.emailCheck(request.toVO());
        // 상태 코드 기준 정해야 함
        return ResponseEntity.ok(new RestResponse.RestResultResponse(100, check ? "fail" : "success"));
    }

    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse.RestResultResponse> redisGet(@RequestParam("code") String code) throws Exception {

        boolean check = authService.authCodeCheck(code);

        return ResponseEntity.ok(new RestResponse.RestResultResponse(100, check ? "fail" : "success"));
    }



}
