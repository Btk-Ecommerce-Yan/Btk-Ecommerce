package com.btk.controller;

import com.btk.dto.request.ActivateRequestDto;
import com.btk.dto.request.LoginRequestDto;
import com.btk.dto.request.RegisterUserRequestDto;
import com.btk.dto.response.LoginResponseDto;
import com.btk.dto.response.ToAuthPasswordChangeDto;
import com.btk.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Kullanıcı kayıt olurken kullanılıyor.")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterUserRequestDto dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    @PostMapping(REGISTER_SITE_MANAGER)
    @Operation(summary = "Admin Site-Manager kayıt ederken kullanılıyor.")
    public ResponseEntity<String> registerSiteManager(@RequestBody RegisterUserRequestDto dto, @PathVariable String token) {
        return ResponseEntity.ok(authService.registerSiteManager(dto, token));
    }

    @PostMapping(ACTIVATE_STATUS)
    @Operation(summary = "Kullanıcı kayıt olduğunda, 'PENDING' olan hesap durumunu 'ACTIVE'e dönüştürmek.")
    public ResponseEntity<String> activateStatus(@RequestBody ActivateRequestDto dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping(FORGOT_PASSWORD)
    @Operation(summary = "Kullanıcı şifresini unuttuğunda kullanacağı şifre değiştirme metodu.")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @Hidden
    @PutMapping(PASSWORD_CHANGE)
    ResponseEntity<Boolean> changePassword(@RequestBody ToAuthPasswordChangeDto dto) {
        return ResponseEntity.ok(authService.changePassword(dto));
    }
}
