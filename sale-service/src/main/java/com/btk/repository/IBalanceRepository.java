package com.btk.repository;

import com.btk.entity.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBalanceRepository extends MongoRepository<Balance,String> {
}
