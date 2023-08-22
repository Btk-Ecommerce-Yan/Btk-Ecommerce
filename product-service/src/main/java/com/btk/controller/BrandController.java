package com.btk.controller;

import com.btk.entity.Brand;
import com.btk.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BRAND)
public class BrandController {

    private final BrandService brandService;

    @PostMapping("")
    public ResponseEntity<Boolean> save(String brandName){
        return ResponseEntity.ok(brandService.save(brandName));
    }

    @GetMapping("")
    public ResponseEntity<List<Brand>> getAll(){
        return ResponseEntity.ok(brandService.findAll());
    }
}
