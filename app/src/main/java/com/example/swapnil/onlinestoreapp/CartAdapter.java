package com.example.swapnil.onlinestoreapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    List<CartModel> listItems;
    Context context;
    String delete_from_cart = HomeActivity.BASE_URL+"/phpscripts/deletefromcart.php?cart_id=?";

    public CartAdapter(Context context, List<CartModel> listItems)
    {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout, viewGroup, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder cartHolder, int i) {

        CartModel model = listItems.get(i);

        Picasso.with(context).load("http://"+model.getImage_url()).into(cartHolder.imageView);
        cartHolder.textProd.setText(model.getProd_name());
        cartHolder.textWeight.setText("Unit of Product: "+model.getProd_unit());
        cartHolder.quantity.setText("Quantity: "+ Float.parseFloat(model.getProd_qty()));
        cartHolder.textPrice.setText("Price: "+(Integer.parseInt(model.getProd_price())*Float.parseFloat(model.getProd_qty())));

        cartHolder.deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = cartHolder.getAdapterPosition();
                deleteFromCart(pos);
            }
        });
    }

    public void deleteFromCart(final int position)
    {
        final CartModel model = listItems.get(position);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, delete_from_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg = null;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        msg = object.getString("message");
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                if (msg.equalsIgnoreCase("Success"))
                {
                    Toast.makeText(context, "Product deleted from cart successfully...", Toast.LENGTH_LONG).show();
                    listItems.remove(position);
                    notifyItemRemoved(position);
                }
                else {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String> map = new HashMap<String, String>();
                map.put("cart_id", model.getCart_id());
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

    public class CartHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textProd, textWeight, textPrice, quantity;
        Button deleteCart;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.cart_image);
            textProd = (TextView)itemView.findViewById(R.id.cart_name);
            textWeight = (TextView)itemView.findViewById(R.id.cart_weight);
            textPrice = (TextView)itemView.findViewById(R.id.cart_price);
            quantity = (TextView) itemView.findViewById(R.id.cart_qty);

            deleteCart = (Button)itemView.findViewById(R.id.cartDelete);

        }
    }
}
