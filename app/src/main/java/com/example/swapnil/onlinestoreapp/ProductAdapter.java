package com.example.swapnil.onlinestoreapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ProductModel> listItems;
    Context context;
    String url_add_to_cart = HomeActivity.BASE_URL+"/phpscripts/addToCart.php";
    SessionManagement session;

    private ArrayAdapter adapter;
    String[] qty = {"1", "2", "3", "4", "5", "6", "7", "8"};
    String[] kilo = {"0.5", "1.0", "2.0", "5.0"};
    String[] ltr = {"0.5", "1.0", "2.0"};

    public ProductAdapter(Context context, List<ProductModel> listItems)
    {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final ProductModel model = listItems.get(position);

        String prod_unit = model.getProd_weight();
        if (prod_unit.equalsIgnoreCase("qty"))
        {
            adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, qty);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.quantity.setAdapter(adapter);
        }
        if (prod_unit.equalsIgnoreCase("kg"))
        {
            adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, kilo);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.quantity.setAdapter(adapter);
        }
        if (prod_unit.equalsIgnoreCase("ltr")){
            adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, ltr);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.quantity.setAdapter(adapter);
        }

        Picasso.with(context).load("http://"+model.getImage_url()).into(viewHolder.imageView);
        viewHolder.textProd.setText(model.getProd_name());
        viewHolder.textWeight.setText("Unit of Product: "+model.getProd_weight());
        viewHolder.textPrice.setText("Price: "+model.getProd_price());

        viewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolder.getAdapterPosition();
                String str = viewHolder.quantity.getSelectedItem().toString();
                addToCart(pos, str);
                //Toast.makeText(context, "Product ID: "+model.getProd_id(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addToCart(final int position, final String quantity)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add_to_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg= null;
                //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        msg = object.getString("message");
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                if (msg.equalsIgnoreCase("Product Added"))
                {
                    Toast.makeText(context, "Product added to cart successfully...", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
           protected Map<String, String> getParams()
           {
               ProductModel model = listItems.get(position);
               session = new SessionManagement(context);
               HashMap<String, String> user = session.getUserDetails();
               String user_id = user.get(SessionManagement.KEY_NAME);

               Map<String, String> map = new HashMap<String, String>();
               map.put("userId", user_id);
               map.put("product_Id", String.valueOf(model.getProd_id()));
               map.put("quantity", quantity);
               map.put("product_Name", model.getProd_name());
               map.put("unit_of_Product", model.getProd_weight());
               map.put("price", model.getProd_price());
               return map;
           }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textProd, textWeight, textPrice;
        Spinner quantity;
        Button cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.prod_image);
            textProd = (TextView)itemView.findViewById(R.id.prod_name);
            textWeight = (TextView)itemView.findViewById(R.id.prod_weight);
            textPrice = (TextView)itemView.findViewById(R.id.prod_price);
            quantity = (Spinner)itemView.findViewById(R.id.prod_qty);

            cart = (Button)itemView.findViewById(R.id.cart);

        }
    }
}
