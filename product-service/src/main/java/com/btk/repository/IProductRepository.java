package com.btk.repository;

import com.btk.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA/Mongo yapısı gereği RepositoryPattern uygular.
 * Spring geliştiricilerin projeler çok kapsamlı olmadığı sürece
 * veya özel bir veri tabanı yönetimi ve soyutlaması gerekmediği sürece RepositoryPattern uygulamaya gerek duyulmaz.
 *
 */
@Repository
public interface IProductRepository extends MongoRepository<Product, String> {

}
