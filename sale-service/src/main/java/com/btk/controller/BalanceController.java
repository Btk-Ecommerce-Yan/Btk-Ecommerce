package com.btk.controller;

import com.btk.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.btk.constant.ApiUrls.*;

@RestController
@RequestMapping(BALANCE)
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;
}
