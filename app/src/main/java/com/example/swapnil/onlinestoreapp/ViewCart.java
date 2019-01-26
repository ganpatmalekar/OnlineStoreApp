package com.example.swapnil.onlinestoreapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCart extends Fragment {

    private String url_view_cart = HomeActivity.BASE_URL+"/phpscripts/viewCart.php?user_id=";
    private String url_order_from_customer = HomeActivity.BASE_URL+"/phpscripts/orderFormCustomer.php";
    private RecyclerView recyclerView ;
    private RecyclerView.Adapter adapter;
    private List<CartModel> listItems;
    private Button order_place;
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_cart, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.view_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        order_place = (Button)view.findViewById(R.id.order_place);
        order_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_order_from_customer, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String  msg = null;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i<jsonArray.length(); i++)
                            {
                                JSONObject object  = jsonArray.getJSONObject(i);
                                msg = object.getString("message");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        if (msg.equalsIgnoreCase("Order successful"))
                        {
                            order_place.setEnabled(false);
                            Toast.makeText(getContext(), "You have placed your order successfully", Toast.LENGTH_LONG).show();
                            listItems.clear();
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> user = session.getUserDetails();
                        String user_id = user.get(SessionManagement.KEY_NAME);

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("user_id", user_id);
                        return map;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        session = new SessionManagement(getContext());
        listItems = new ArrayList<>();

        viewCart();
        return view;
    }

    public void viewCart()
    {
        HashMap<String, String> user = session.getUserDetails();
        String user_id = user.get(SessionManagement.KEY_NAME);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_view_cart+user_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Toast.makeText(getContext(), "Response: "+response, Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        CartModel model = new CartModel(
                                object.getString("Image_Path"),
                                object.getString("Product_Name"),
                                object.getString("Unit_of_Product"),
                                object.getString("Quantity"),
                                object.getString("Price"),
                                object.getString("Cart_Id")
                        );
                        listItems.add(model);
                    }
                    adapter = new CartAdapter(getContext(), listItems);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
