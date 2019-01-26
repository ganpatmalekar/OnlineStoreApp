package com.example.swapnil.onlinestoreapp;

public class CartModel {
    private String image_url;
    private String prod_name;
    private String prod_unit;
    private String prod_qty;
    private String prod_price;
    private String cart_id;

    public CartModel(String image_url, String prod_name, String prod_unit, String prod_qty, String prod_price, String cart_id)
    {
        this.image_url = image_url;
        this.prod_name = prod_name;
        this.prod_unit = prod_unit;
        this.prod_qty = prod_qty;
        this.prod_price = prod_price;
        this.cart_id = cart_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getProd_unit() {
        return prod_unit;
    }

    public String getProd_price() {
        return prod_price;
    }

    public String getProd_qty() {
        return prod_qty;
    }

    public String getCart_id() {
        return cart_id;
    }
}
