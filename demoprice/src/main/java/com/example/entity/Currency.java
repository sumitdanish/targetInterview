package com.example.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by summit on 6/26/17.
 */

@Document(collection = "currency")
public class Currency {




    @Field
    private String _id;

    @Field
    private double value;

    @Field
    private String currencyType;

    public Currency(){

    }
    public Currency(String _id,double value,String currencyType){
        this._id = _id;
        this.value = value;
        this.currencyType = currencyType;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
