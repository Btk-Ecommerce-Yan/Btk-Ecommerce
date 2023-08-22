package com.btk.controller;

import com.btk.dto.request.ProductSaveRequestDto;
import com.btk.dto.request.ProductUpdateRequestDto;
import com.btk.dto.response.ProductSaveResponseDto;
import com.btk.dto.response.ProductUpdateResponseDto;
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

    @PostMapping("")
    public ResponseEntity<ProductSaveResponseDto> save(@RequestBody ProductSaveRequestDto dto){
        return ResponseEntity.ok(productService.save(dto));
    }

    @PutMapping("")
    public ResponseEntity<ProductUpdateResponseDto> update(@RequestBody ProductUpdateRequestDto dto){
        return ResponseEntity.ok(productService.update(dto));
    }

    @DeleteMapping(DELETED_PRODUCT)
    public ResponseEntity<Boolean> delete(@PathVariable String productId){
        return ResponseEntity.ok(productService.delete(productId));
    }
}
