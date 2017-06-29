package com.example.daoServiceImpl;

import com.example.daoService.DAOService;
import com.example.productentity.Currency;
import com.example.productentity.Product;
import com.example.requestentity.ProductDetails;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by summit on 6/26/17.
 */

@Service
public class DAOServiceImpl implements DAOService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    @Value("${product.save.nosql}")
    private String postURI;

    @Value("${product.gen.nosql}")
    private String getPutURI;

    @Override
    @Transactional
    public JSONObject save(ProductDetails productDetails,String accessToken){
        JSONObject jsonObject = new JSONObject();
        JSONObject currencyJSON = new JSONObject();
        Product product = null;
        try{
            currencyJSON.put("_id",productDetails.getId());
            currencyJSON.put("value",productDetails.getCurrency().getValue());
            currencyJSON.put("currencyType",productDetails.getCurrency().getCurrencyType());
            product = new Product(productDetails.getId(),productDetails.getName(),productDetails.getDescription(),productDetails.getLanguage(),productDetails.getWriter(),productDetails.getDateOfPublish(),productDetails.getCompanyName());
            if(product.getDateOfPublish() == null){
                product.setDateOfPublish(new Date());
            }

            sessionFactory.getCurrentSession().save(product);
            saveItemPrice(currencyJSON,accessToken,"POST");
            jsonObject.put("success",product.getId());

        }catch(HibernateException ex){
            ex.printStackTrace();
            jsonObject.put("Error",ex.getMessage());
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }catch (Exception ex){
            ex.printStackTrace();
            jsonObject.put("Error",ex.getMessage());
        }
        finally{
            sessionFactory.getCurrentSession().flush();
        }
        return jsonObject;
    }

    @Override
    @Transactional
    public JSONObject productDetail(String id,String accessToken) {

        ProductDetails productDetails = null;
        JSONObject jsonObject = new JSONObject();
        try{
            Product product = (Product) sessionFactory.getCurrentSession().get(Product.class,id);
            if(product != null){
                Currency currency = (Currency) getProductPrice(id,accessToken).get("price");
                productDetails = new ProductDetails(product.getId(),product.getName(),product.getDescription(),product.getLanguage(),product.getWriter(),product.getDateOfPublish(),product.getCompanyName(),currency);
            }else{
                jsonObject.put("Error","No Product is Found");
                return jsonObject;
            }
            jsonObject.put("items",productDetails);
        }catch (Exception ex){
            ex.printStackTrace();
            jsonObject.put("Error",ex.getMessage());
        }
        return jsonObject;
    }

    @Override
    @Transactional
    public JSONObject updateProduct(JSONObject productDetailsJSON,String accessToken) {
        JSONObject jsonObject = new JSONObject();
        JSONObject currencyJSON = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        Map<String,Object> valMap = (LinkedHashMap)productDetailsJSON.get("items");
        Map<String,Object> currencyMap = (LinkedHashMap)valMap.get("currency");
        System.out.println("VAL : "+ currencyMap.size());
        try{
            currencyJSON.put("_id",String.valueOf(valMap.get("id")));
            currencyJSON.put("value",Double.parseDouble(String.valueOf(currencyMap.get("value"))));
            currencyJSON.put("currencyType",(String)currencyMap.get("currencyType"));
            product = new Product(String.valueOf(valMap.get("id")),String.valueOf(valMap.get("name")),String.valueOf(valMap.get("description")),String.valueOf(valMap.get("language")),String.valueOf(valMap.get("writer")),new Date(Long.parseLong(String.valueOf(valMap.get("dateOfPublish")))),String.valueOf(valMap.get("companyName")));
            if(product.getDateOfPublish() == null){
                product.setDateOfPublish(new Date());
            }

            sessionFactory.getCurrentSession().update(product);
            saveItemPrice(currencyJSON,accessToken,"PUT");
            jsonObject.put("success",product.getId());

        }catch(SQLException ex){
            ex.printStackTrace();
            jsonObject.put("Error",ex.getMessage());
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }catch (Exception ex){
            ex.printStackTrace();
            jsonObject.put("Error",ex.getMessage());
        }
        finally{
            sessionFactory.getCurrentSession().flush();
        }
        return jsonObject;
    }





    private JSONObject getProductPrice(String pid,String accessToken) throws Exception{
        ObjectMapper mapper = null;

        Currency currency = null;
        JSONObject jsonObject = new JSONObject();
        LinkedHashMap<String,Object> currencyMap = null;
        try{
            Object o = restTemplate.getForObject(getPutURI+"/"+pid+"?access_token="+accessToken,Object.class);
            Map<String,Object> m = (LinkedHashMap)o;
            for(Map.Entry m1 : m.entrySet()){
                String key = (String)m1.getKey();
                System.out.println(key+" : "+m1.getValue().toString());
                currencyMap = (LinkedHashMap<String, Object>) m1.getValue();
            }
            currency = new Currency((Double)currencyMap.get("value"),String.valueOf(currencyMap.get("currencyType")));
            jsonObject.put("price",currency);
        }catch (Exception ex){
            ex.printStackTrace();
            jsonObject.put("Error",ex.getMessage());
        }
        return jsonObject;
    }




    private void saveItemPrice(JSONObject currency,String accessToken,String requestType) throws Exception{
        HttpHeaders headers = null;
        HttpEntity<JSONObject> entity = null;
        HttpMethod httpMethod = null;
        String requestEndPoint = null;
        try{
            if(requestType.equalsIgnoreCase("POST")){
                httpMethod = HttpMethod.POST;
                requestEndPoint = postURI+"?access_token="+accessToken;
            }else{
                httpMethod = HttpMethod.PUT;
                requestEndPoint = getPutURI+"/"+String.valueOf(currency.get("_id")).trim()+"?access_token="+accessToken;
            }
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            entity = new HttpEntity<>(currency,headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(requestEndPoint, httpMethod,entity,JSONObject.class);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
