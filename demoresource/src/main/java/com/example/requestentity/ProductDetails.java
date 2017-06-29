package com.example.requestentity;

import com.example.productentity.Currency;
import com.example.productentity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by summit on 6/26/17.
 */

public class ProductDetails implements Serializable{


    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private String language;

    @JsonProperty
    private String writer;

    @JsonProperty
    private Date dateOfPublish;

    @JsonProperty
    private String companyName;

    @JsonProperty
    private Currency currency;


    public ProductDetails(){

    }

    public ProductDetails(String id, String name, String description, String language, String writer, Date dateOfPublish, String companyName, Currency currency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.language = language;
        this.writer = writer;
        this.dateOfPublish = dateOfPublish;
        this.companyName = companyName;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(Date dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
