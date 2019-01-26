package com.example.swapnil.onlinestoreapp;

public class ProductModel {
    private String image_url;
    private String prod_name;
    private String prod_weight;
    private String prod_price;
    private int prod_id;


    public ProductModel(String image_url, String prod_name, String prod_weight, String prod_price, int prod_id)
    {
        this.image_url = image_url;
        this.prod_name = prod_name;
        this.prod_weight = prod_weight;
        this.prod_price = prod_price;
        this.prod_id = prod_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getProd_weight() {
        return prod_weight;
    }

    public String getProd_price() {
        return prod_price;
    }

    public int getProd_id() {
        return prod_id;
    }
}
