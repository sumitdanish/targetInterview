package com.example.daoService;

import com.example.requestentity.ProductDetails;
import org.json.simple.JSONObject;

/**
 * Created by summit on 6/26/17.
 */
public interface DAOService {
    public JSONObject save(ProductDetails product,String accessToken);
    public JSONObject productDetail(String pid,String accessToken);
    public JSONObject updateProduct(JSONObject productDetails,String accessToken);
}
