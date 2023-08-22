package com.btk.service;

import com.btk.entity.Category;
import com.btk.repository.ICategoryRepository;
import com.btk.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends ServiceManager<Category, String> {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    public Boolean save(String categoryName){
        Category category = Category.builder()
                .categoryName(categoryName).build();
        save(category);
        return true;
    }

    public Boolean existByCategoryId(String categoryId){
        return categoryRepository.existsById(categoryId);
    }
}
