package com.example.daoServiceImpl;

import com.example.daoService.ProductPriceDAOService;
import com.example.entity.Currency;
import com.example.repository.CurrencyRepository;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by summit on 6/26/17.
 */

@Service
public class ProductPriceDAOServiceImpl implements ProductPriceDAOService{


    @Autowired
    private CurrencyRepository currencyRepository;


    @Override
    public JSONObject save(Currency currency) {
        JSONObject response = new JSONObject();
        try{
            currency = currencyRepository.save(currency);
            response.put("success",currency.get_id());
        }catch (Exception ex){
            response.put("error",ex.getMessage());
        }
        return response;
    }

    @Override
    public Currency findPriceById(String id) {
        try{
            return currencyRepository.findByProductId(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JSONObject updateCurrency(String id,Currency currency) {
        JSONObject response = new JSONObject();
        try{
            Currency currency1 = currencyRepository.findByProductId(id);
            currency1.setCurrencyType(currency.getCurrencyType());
            currency1.setValue(currency.getValue());
            currencyRepository.save(currency1);
            response.put("success",currency1.get_id());
        }catch (Exception ex){
            ex.printStackTrace();
            response.put("error",ex.getMessage());
        }
        return response;
    }
}
