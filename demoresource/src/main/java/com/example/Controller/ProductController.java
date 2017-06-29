package com.example.Controller;

import com.example.requestentity.ProductDetails;
import com.example.service.ProductService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * Created by summit on 6/24/17.
 */

@RestController
@RequestMapping(value = "product/v1")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody ProductDetails productDetails,@RequestParam("access_token") String accessToken){
        JSONObject jsonObject = new JSONObject();
        System.out.println(productDetails.getCompanyName());
        try{
            jsonObject = productService.save(productDetails,accessToken);
            return new ResponseEntity<JSONObject>(jsonObject,HttpStatus.OK);
        }catch (Exception ex){
            jsonObject.put("Error",ex.getMessage());
           return new ResponseEntity<JSONObject>(jsonObject,HttpStatus.MULTI_STATUS);
        }
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value = "price/{pid}",method = {RequestMethod.GET,RequestMethod.PUT})
    public ResponseEntity<JSONObject> productDetails(@PathVariable ("pid") String pid, @RequestParam("access_token") String accessToken, @RequestBody Optional<JSONObject> productDetails, HttpServletRequest request){
        JSONObject responseJSON = new JSONObject();
        try{
            if(request.getMethod().equalsIgnoreCase("GET") && !productDetails.isPresent()){
                return new ResponseEntity<JSONObject>(productService.productDetail(pid,accessToken),HttpStatus.OK);
            }else{
                if(productDetails.isPresent()){

                    return new ResponseEntity<JSONObject>(productService.updateProduct(productDetails.get(),accessToken),HttpStatus.OK);
                }else{
                    responseJSON.put("Warning","You can not update product without details...");
                    return new ResponseEntity<JSONObject>(responseJSON,HttpStatus.MULTI_STATUS);
                }
            }
        }catch (Exception ex){
            responseJSON.put("Error",ex.getMessage());
            return new ResponseEntity<JSONObject>(responseJSON,HttpStatus.MULTI_STATUS);

        }
    }



}
