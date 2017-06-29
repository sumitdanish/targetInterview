package com.example.daoService;

import com.example.entity.Currency;
import org.json.simple.JSONObject;

/**
 * Created by summit on 6/26/17.
 */
public interface ProductPriceDAOService {
    public JSONObject save(Currency currency);
    public Currency findPriceById(String id);
    public JSONObject updateCurrency(String id,Currency currency);
}
