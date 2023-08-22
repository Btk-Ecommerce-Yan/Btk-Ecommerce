package com.btk.controller;

import com.btk.dto.request.CreateOrderRequestDto;
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

    @PostMapping("/create-order/{token}")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequestDto dto, @PathVariable String token){
        return ResponseEntity.ok(orderService.createOrder(dto,token));
    }
}
