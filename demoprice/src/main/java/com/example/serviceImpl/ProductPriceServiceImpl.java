package com.example.serviceImpl;

import com.example.daoService.ProductPriceDAOService;
import com.example.entity.Currency;
import com.example.service.ProductPriceService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by summit on 6/26/17.
 */

@Service
public class ProductPriceServiceImpl implements ProductPriceService {


    @Autowired
    private ProductPriceDAOService productPriceDAOService;

    @Override
    public JSONObject save(Currency currency) {
        return productPriceDAOService.save(currency);
    }

    @Override
    public Currency findPriceById(String id) {
        return productPriceDAOService.findPriceById(id);
    }

    @Override
    public JSONObject updateCurrency(String id,Currency currency) {
        return productPriceDAOService.updateCurrency(id,currency);
    }
}
