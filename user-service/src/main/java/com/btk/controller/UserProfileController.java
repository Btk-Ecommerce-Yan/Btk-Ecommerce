package com.btk.controller;

import com.btk.dto.request.UserChangePasswordRequestDto;
import com.btk.dto.response.ForgotPasswordUserResponseDto;
import com.btk.dto.response.NewCreateUserResponseDto;
import com.btk.dto.response.UserProfileResponseDto;
import com.btk.service.UserProfileService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequestMapping(USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Hidden
    @PostMapping(CREATE_USER)
    public ResponseEntity<Boolean> createVisitorUser(@RequestBody NewCreateUserResponseDto dto) {
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }
    @Hidden
    @PutMapping(ACTIVATE_STATUS + "/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId) {
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }
    @Hidden
    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody ForgotPasswordUserResponseDto dto) {
        return ResponseEntity.ok(userProfileService.forgotPassword(dto));
    }
    @PutMapping(PASS_CHANGE)
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequestDto dto) {
        return ResponseEntity.ok(userProfileService.changePassword(dto));
    }

    @GetMapping("/find-by-auth-id/{authId}")
    public ResponseEntity<String> findByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.findByAuthId(authId));
    }

}
