package com.example.controller;

import com.example.Utils.ConverObjectToJSON;
import com.example.entity.Currency;
import com.example.service.ProductPriceService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by summit on 6/26/17.
 */

@RestController
@RequestMapping("product/v1")
public class ProductPriceController {

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private ConverObjectToJSON converObjectToJSON;

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Currency currency){
        JSONObject responseJSON = new JSONObject();
        try{
            return new ResponseEntity<JSONObject>(productPriceService.save(currency),HttpStatus.OK);
        }catch (Exception ex){
            responseJSON.put("error",ex.getMessage());
            return new ResponseEntity<JSONObject>(responseJSON, HttpStatus.MULTI_STATUS);
        }
    }


    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value = "price/{id}",method = {RequestMethod.GET,RequestMethod.PUT})
    public ResponseEntity<JSONObject> price(@PathVariable("id") String pid, @RequestBody Optional<Currency> currency, HttpServletRequest request){
        JSONObject responseJSON = new JSONObject();
        try{
            if(request.getMethod().equalsIgnoreCase("GET")) {
                responseJSON.put("data", productPriceService.findPriceById(pid));
                return new ResponseEntity<JSONObject>(responseJSON, HttpStatus.OK);
            }else{
                if(currency.isPresent()) {
                    responseJSON.put("data", productPriceService.updateCurrency(pid, currency.get()));
                    return new ResponseEntity<JSONObject>(responseJSON, HttpStatus.OK);
                }else{
                    responseJSON.put("Error", "You can not update product without details...");
                    return new ResponseEntity<JSONObject>(responseJSON, HttpStatus.MULTI_STATUS);
                }
            }
        }catch (Exception ex){
            responseJSON.put("error",ex.getMessage());
            return new ResponseEntity<JSONObject>(responseJSON, HttpStatus.MULTI_STATUS);
        }
    }
}
