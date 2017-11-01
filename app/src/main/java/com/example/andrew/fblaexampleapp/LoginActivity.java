package com.example.andrew.fblaexampleapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText _userName;
    private EditText _password;
    private Button _loginButton;
    String URL = "https://fblaexamplebackend.azurewebsites.net/api/account/CreateToken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _loginButton = findViewById(R.id.btn_Login);
        _userName = findViewById(R.id.edtxt_UserName);
        _password = findViewById(R.id.edtxt_Password);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = _userName.getText().toString();
                String password = _password.getText().toString();
                Login(userName,password);
            }
        });

    }


    public void Login(final String username, final String password){
        LoginRequest requestObj = new LoginRequest(username,password);
        final Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject(gson.toJson(requestObj));
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                object,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", gson.fromJson(response.toString(), String.class));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+ error);
                        Log.e("Data:",new String(error.networkResponse.data));
                    }
                }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    return params;
                }


            };
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Json error", e.toString());

        }


    }
}
