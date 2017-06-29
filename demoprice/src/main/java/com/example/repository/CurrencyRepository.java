package com.example.repository;

import com.example.entity.Currency;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by summit on 6/26/17.
 */
@Repository
@Transactional
public interface CurrencyRepository extends MongoRepository<Currency,String>{

    @Query("{'_id' : ?0}")
    Currency findByProductId(String pid);
}
