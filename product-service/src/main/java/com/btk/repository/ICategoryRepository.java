package com.btk.repository;

import com.btk.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, String> {

    boolean existsById(String categoryId);
}
