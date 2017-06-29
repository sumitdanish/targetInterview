package com.example.serviceImpl;

import com.example.daoService.DAOService;
import com.example.requestentity.ProductDetails;
import com.example.service.ProductService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by summit on 6/26/17.
 */

@Service
public class ServiceImpl implements ProductService {


    @Autowired
    private DAOService daoService;

    @Override
    public JSONObject save(ProductDetails product,String accessToken) {
        return daoService.save(product,accessToken);
    }

    @Override
    public JSONObject productDetail(String pid,String accessToken) {
        return daoService.productDetail(pid,accessToken);
    }

    @Override
    public JSONObject updateProduct(JSONObject productDetails,String accessToken) {
        return daoService.updateProduct(productDetails,accessToken);
    }
}
