package com.btk.controller;

import com.btk.dto.request.ProductSaveRequestDto;
import com.btk.dto.request.ProductUpdateRequestDto;
import com.btk.dto.response.ProductDetailsResponseDto;
import com.btk.dto.response.ProductSaveResponseDto;
import com.btk.dto.response.ProductUpdateResponseDto;
import com.btk.dto.response.SearchProductResponseDto;
import com.btk.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT)
public class ProductController {
    private final ProductService productService;

    @PostMapping(SAVE_PRODUCT + "/{token}")
    public ResponseEntity<ProductSaveResponseDto> save(@RequestBody @Valid ProductSaveRequestDto dto, @PathVariable String token) {
        return ResponseEntity.ok(productService.save(dto, token));
    }

    @PutMapping(UPDATE_PRODUCT + "/{token}")
    public ResponseEntity<ProductUpdateResponseDto> update(@RequestBody ProductUpdateRequestDto dto, @PathVariable String token) {
        return ResponseEntity.ok(productService.update(dto, token));
    }

    @DeleteMapping(DELETED_PRODUCT + "/{token}")
    public ResponseEntity<Boolean> delete(@PathVariable String productId, @PathVariable String token) {
        return ResponseEntity.ok(productService.delete(productId, token));
    }

    @GetMapping(PRODUCT_DETAILS + "/{productId}")
    public ResponseEntity<ProductDetailsResponseDto> productDetails(@PathVariable String productId) {
        return ResponseEntity.ok(productService.productDetails(productId));
    }

    @GetMapping(PRODUCT_SEARCH_WITH_CATEGORY_NAME + "/{categoryName}")
    public ResponseEntity<List<SearchProductResponseDto>> searchProductWithCategoryName(@PathVariable String categoryName) {
        return ResponseEntity.ok(productService.searchProductWithCategoryName(categoryName));
    }

    @GetMapping(PRODUCT_SEARCH_WITH_PRODUCT_NAME + "/{productName}")
    @Operation(summary = "Ürünleri, isimlerine göre aratabilmek.")
    public ResponseEntity<List<SearchProductResponseDto>> searchProductWithProductName(@PathVariable String productName) {
        return ResponseEntity.ok(productService.searchProductWithProductName(productName));
    }

    @GetMapping(PRODUCT_SEARCH_WITH_PRICE)
    @Operation(summary = "Ürünleri, istenilen fiyat ya da fiyat aralığında aratabilmek.")
    public ResponseEntity<List<SearchProductResponseDto>> searchProductWithProductPrice(@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(productService.searchProductWithProductPrice(minPrice, maxPrice));
    }

    @GetMapping("/get-by-product-id/{productId}")
    public ResponseEntity<Double> getPriceByProductId(@PathVariable String productId){
        return ResponseEntity.ok(productService.getPriceByProductId(productId));
    }


}
