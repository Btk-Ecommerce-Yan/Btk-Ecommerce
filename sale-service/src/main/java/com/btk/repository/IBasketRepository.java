package com.btk.repository;

import com.btk.entity.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBasketRepository extends MongoRepository<Basket,String> {
    Optional<Basket> findOptionalByBasketId(String basketId);
}
