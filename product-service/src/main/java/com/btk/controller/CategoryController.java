package com.btk.controller;

import com.btk.dto.request.CategoryUpdateRequestDto;
import com.btk.entity.Category;
import com.btk.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.btk.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<Boolean> save(String categoryName, String token) {
        return ResponseEntity.ok(categoryService.save(categoryName, token));
    }

    @PutMapping("/update-category/{token}")
    public ResponseEntity<Boolean> updateCategory(@RequestBody CategoryUpdateRequestDto dto,@PathVariable String token) {
        return ResponseEntity.ok(categoryService.updateCategory(dto,token));
    }


    @GetMapping("")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
