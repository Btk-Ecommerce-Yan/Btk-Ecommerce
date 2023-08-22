package com.btk.controller;

import com.btk.dto.request.AddProductToBasketRequestDto;
import com.btk.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequestMapping(BASKET)
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;


    @PostMapping("/add-product-to-basket/{token}")
    public ResponseEntity<Boolean> addProductToBasket(@PathVariable String token, @RequestBody AddProductToBasketRequestDto dto){
        return ResponseEntity.ok(basketService.addProductToBasket(token,dto));
    }
}
