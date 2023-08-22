package com.btk.controller;

import com.btk.entity.Category;
import com.btk.service.CategoryService;
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
@RequestMapping(CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Boolean> save(String categoryName){
        return ResponseEntity.ok(categoryService.save(categoryName));
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }
}
