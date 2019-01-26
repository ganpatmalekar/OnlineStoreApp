package com.example.swapnil.onlinestoreapp;

import org.json.JSONArray;

import java.util.Date;

public class OrderModel {
    private String order_id;
    private String delivery_status;
    private String comments;
    private String price;

    public OrderModel(String order_id, String delivery_status, String comments, String price)
    {
        this.order_id = order_id;
        this.delivery_status = delivery_status;
        this.comments = comments;
        this.price = price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public String getComments() {
        return comments;
    }

    public String getPrice() {
        return price;
    }
}
