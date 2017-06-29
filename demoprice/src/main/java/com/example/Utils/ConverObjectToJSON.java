package com.example.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by summit on 6/26/17.
 */

@Component
public class ConverObjectToJSON<T> {


    public ConverObjectToJSON(){

    }


    public String convertObjectToJSON(T object) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }



}
