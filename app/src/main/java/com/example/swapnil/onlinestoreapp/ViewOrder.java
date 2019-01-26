package com.example.swapnil.onlinestoreapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class ViewOrder extends Fragment {

    SessionManagement session;
    String view_order_url = HomeActivity.BASE_URL+"/phpscripts/viewOrderMain.php";
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    List<OrderModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        viewOrder();
        return view;
    }

    public void viewOrder()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, view_order_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        OrderModel model = new OrderModel(
                                object.getString("Order_Id"),
                                object.getString("Delivery_Status"),
                                object.getString("Comments"),
                                object.getString("Total_Bill")
                        );
                        list.add(model);
                    }
                    adapter = new OrderAdapter(getContext(), list);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String> map = new HashMap<String, String>();
                session = new SessionManagement(getContext());
                HashMap<String, String> user = session.getUserDetails();
                String user_id = user.get(SessionManagement.KEY_NAME);
                map.put("user_id", user_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
