package com.example.swapnil.onlinestoreapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class CustRegistration extends AppCompatActivity {

    private EditText user, pass, email, name, mob, address;
    private Button register;
    private final String url_register_customer = HomeActivity.BASE_URL+"/phpscripts/registerCustomer.php";

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_registration);

        session = new SessionManagement(this);

        user = (EditText)findViewById(R.id.userid);
        pass = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        name = (EditText)findViewById(R.id.name_user);
        mob = (EditText)findViewById(R.id.mobile);
        address = (EditText)findViewById(R.id.address);

        register = (Button)findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord(view);
            }
        });
    }

    public void saveRecord(View view)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_register_customer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg = null;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        msg = object.getString("message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (msg.equals("User Added"))
                {
                    Toast.makeText(getApplicationContext(), "Registration success...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", user.getText().toString());
                map.put("pass", pass.getText().toString());
                map.put("email", email.getText().toString());
                map.put("name", name.getText().toString());
                map.put("mob", mob.getText().toString());
                map.put("add", address.getText().toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
