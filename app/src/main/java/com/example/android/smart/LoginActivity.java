package com.example.android.smart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView signupTxt;
    Button loginBtn;
    ImageButton img;
    String url = "http://silptech.com.np/phpscripts/loginRoutine.php";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging you in... Please wait...");
        progressDialog.setCancelable(false);

        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.btn_login);
        signupTxt = findViewById(R.id.signup_txt);

        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            int success = obj1.getInt("success");
                            if(success == 1){
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(in);
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch(Exception e){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Internal error.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Error in connection.", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> myMap = new HashMap<>();
                        myMap.put("username", username.getText().toString());
                        myMap.put("password", password.getText().toString());
                        return myMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}









