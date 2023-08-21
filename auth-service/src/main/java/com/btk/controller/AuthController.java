package com.btk.controller;

import com.btk.dto.request.ActivateRequestDto;
import com.btk.dto.request.LoginRequestDto;
import com.btk.dto.request.RegisterUserRequestDto;
import com.btk.dto.response.LoginResponseDto;
import com.btk.dto.response.ToAuthPasswordChangeDto;
import com.btk.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER_USER)
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterUserRequestDto dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<String> activateStatus(@RequestBody ActivateRequestDto dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        return ResponseEntity.ok(authService.forgotPassword(email));
    }
    @Hidden
    @PutMapping("/password-change")
    ResponseEntity<Boolean> changePassword(@RequestBody ToAuthPasswordChangeDto dto) {
        return ResponseEntity.ok(authService.changePassword(dto));
    }
}
