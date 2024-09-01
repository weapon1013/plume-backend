package com.plume.backend.api.auth.controller;

import com.plume.backend.api.auth.domain.dto.AuthDTO;
import com.plume.backend.api.auth.domain.entity.AuthEntity;
import com.plume.backend.api.auth.repository.jpa.AuthRepository;
import com.plume.backend.api.auth.service.AuthService;
import com.plume.common.response.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController("Api_UserController")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(@RequestBody AuthDTO.JoinRequest r) {

        authService.join(r.toEntity());

        return ResponseEntity.ok("Security Test");
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<AuthDTO.TokenResponse>> login(
            @RequestBody @Valid AuthDTO.LoginRequest requestBody
    ) {

        AuthDTO.TokenResponse response = authService.login(requestBody.toVO());
        return ResponseEntity.ok(new RestResponse<>(response));
    }

    @GetMapping(value = "/test/select", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> t(@RequestParam("userSeq") long userSeq) {

        return ResponseEntity.ok(authService.t(userSeq));
    }

}
