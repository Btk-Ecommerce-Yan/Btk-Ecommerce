package com.btk.controller;

import com.btk.dto.response.AddBalanceResponseDto;
import com.btk.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequestMapping(BALANCE)
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping("/add-balance/{amountOfBalance}/{token}")
    private ResponseEntity<AddBalanceResponseDto> addBalance(@PathVariable Double amountOfBalance ,@PathVariable String token){
        return ResponseEntity.ok(balanceService.addBalance(amountOfBalance, token));
    }

    @PostMapping("/create-balance/{authId}")
    private ResponseEntity<Boolean> createBalance(@PathVariable Long authId){
        return ResponseEntity.ok(balanceService.createBalance(authId));
    }
}
