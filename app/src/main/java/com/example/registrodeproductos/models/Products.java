package com.example.registrodeproductos.models;

import javax.annotation.meta.Exclusive;

public class Products {

    public String getID(){
        return id;
    }

    public void setID(String id){
        this.id = id;
    }

    @Exclusive
    private String id;

    private String nameProducts, descriptionProducts, brandProducts, priceProducts;

    public Products(){

    }

    public Products(String nameProducts, String descriptionProducts, String brandProducts, String priceProducts) {
        this.nameProducts = nameProducts;
        this.descriptionProducts = descriptionProducts;
        this.brandProducts = brandProducts;
        this.priceProducts = priceProducts;
    }


    public String getNameProducts() {
        return nameProducts;
    }

    public String getDescriptionProducts() {
        return descriptionProducts;
    }

    public String getBrandProducts() {
        return brandProducts;
    }

    public String getPriceProducts() {
        return priceProducts;
    }
}

//</Programer: Daniel>
