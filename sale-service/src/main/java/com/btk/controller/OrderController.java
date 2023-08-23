package com.btk.controller;

import com.btk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.btk.constant.ApiUrls.*;


@RestController
@RequestMapping(ORDER)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(CREATE_ORDER+"/{balanceId}/{token}")
    public ResponseEntity<String> createOrder(@PathVariable String balanceId, @PathVariable String token){
        return ResponseEntity.ok(orderService.createOrder(balanceId,token));
    }
}
