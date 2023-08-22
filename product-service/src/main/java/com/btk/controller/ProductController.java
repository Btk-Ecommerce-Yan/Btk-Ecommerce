package com.btk.controller;

import com.btk.dto.request.ProductSaveRequestDto;
import com.btk.dto.request.ProductUpdateRequestDto;
import com.btk.dto.response.ProductDetailsResponseDto;
import com.btk.dto.response.ProductSaveResponseDto;
import com.btk.dto.response.ProductUpdateResponseDto;
import com.btk.dto.response.SearchProductResponseDto;
import com.btk.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT)
public class ProductController {
    private final ProductService productService;

    @PostMapping(SAVE_PRODUCT + "/{token}")
    public ResponseEntity<ProductSaveResponseDto> save(@RequestBody ProductSaveRequestDto dto, @PathVariable String token){
        return ResponseEntity.ok(productService.save(dto,token));
    }

    @PutMapping(UPDATE_PRODUCT + "/{token}")
    public ResponseEntity<ProductUpdateResponseDto> update(@RequestBody ProductUpdateRequestDto dto, @PathVariable String token){
        return ResponseEntity.ok(productService.update(dto,token));
    }

    @DeleteMapping(DELETED_PRODUCT + "/{token}")
    public ResponseEntity<Boolean> delete(@PathVariable String productId, @PathVariable String token){
        return ResponseEntity.ok(productService.delete(productId,token));
    }

    @GetMapping(PRODUCT_DETAILS + "/{productId}")
    public ResponseEntity<ProductDetailsResponseDto> productDetails(@PathVariable String productId){
        return ResponseEntity.ok(productService.productDetails(productId));
    }

    @GetMapping(PRODUCT_SEARCH_WITH_CATEGORY_NAME + "/{categoryName}")
    public ResponseEntity<SearchProductResponseDto> searchProductWithCategoryName(@PathVariable String categoryName){
        return ResponseEntity.ok(productService.searchProductWithCategoryName(categoryName));
    }
    @GetMapping("/find-by-product-id/{productId}")
    public ResponseEntity<Double> findPriceByProductId(@PathVariable String productId){
        return ResponseEntity.ok(productService.getPriceByProductId(productId));
    }

}
