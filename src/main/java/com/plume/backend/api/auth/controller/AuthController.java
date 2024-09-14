package com.plume.backend.api.auth.controller;

import com.plume.backend.api.auth.domain.dto.AuthDTO;
import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.api.auth.service.AuthService;
import com.plume.common.response.RestResponse;
import com.plume.common.util.MailUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

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

        AuthDTO.CheckResponse response = authService.check(checkStr, type);
        return ResponseEntity.ok(new RestResponse<>(response));
    }

    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() throws Exception {

        MailUtils.sendMail();

        return ResponseEntity.ok("response success");
    }

}
