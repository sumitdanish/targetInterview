package com.example.productentity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by summit on 6/26/17.
 */

public class Currency {




    @JsonIgnore
    private String _id;

    private double value;

    private String currencyType;

    public Currency(){

    }
    public Currency(String _id, double value, String currencyType){
        this._id = _id;
        this.value = value;
        this.currencyType = currencyType;
    }

    public Currency(double value, String currencyType) {
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
