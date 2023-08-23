package com.btk.controller;

import com.btk.dto.request.AddProductToBasketRequestDto;
import com.btk.dto.request.DeleteProductFromBasketRequestDto;
import com.btk.dto.request.TotalPriceRequestDto;
import com.btk.dto.request.UpdateBasketRequestDto;
import com.btk.dto.response.GetProductDescriptionsFromProductServiceResponseDto;
import com.btk.entity.Basket;
import com.btk.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequestMapping(BASKET)
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;


    @PostMapping("/add-product-to-basket/{token}")
    public ResponseEntity<Boolean> addProductToBasket(@PathVariable String token, @RequestBody AddProductToBasketRequestDto dto) {
        return ResponseEntity.ok(basketService.addProductToBasket(token, dto));
    }

    @PostMapping("/total-price-in-basket/{token}")
    public ResponseEntity<Double> totalPriceInBasket(@PathVariable String token, @RequestBody TotalPriceRequestDto dto) {
        return ResponseEntity.ok(basketService.totalPriceInBasket(token, dto));
    }

    @GetMapping("/find-all/{token}")
    public ResponseEntity<List<GetProductDescriptionsFromProductServiceResponseDto>> findBasketForUser(@PathVariable String token) {
        return ResponseEntity.ok(basketService.findBasketForUser(token));
    }

    @PostMapping("/update-basket/{token}")
    public ResponseEntity<List<GetProductDescriptionsFromProductServiceResponseDto>> updateBasket(@PathVariable String token, @RequestBody UpdateBasketRequestDto dto) {
        return ResponseEntity.ok(basketService.updateBasket(token, dto));
    }
    @PostMapping("/delete-product-from-basket/{token}")
    public ResponseEntity<List<GetProductDescriptionsFromProductServiceResponseDto>> deleteProductFromBasket(@PathVariable String token, @RequestBody DeleteProductFromBasketRequestDto dto) {
        return ResponseEntity.ok(basketService.deleteProductFromBasket(token, dto));
    }
}
