package com.btk.manager;

import com.btk.dto.response.GetProductDescriptionsFromProductServiceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9092/api/v1/product",name = "sale-product")
public interface IProductManager {
    @GetMapping("/get-by-product-id/{productId}")
    public ResponseEntity<Double> findPriceByProductId(@PathVariable String productId);
    @GetMapping("/find-product-descriptions-by-product-id/{productId}")
    public ResponseEntity<GetProductDescriptionsFromProductServiceResponseDto> findDescriptionsByProductId(@PathVariable String productId);
}
