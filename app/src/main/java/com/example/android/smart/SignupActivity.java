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

public class SignupActivity extends AppCompatActivity {
    EditText name, email, passwordd;
    Button signupBtn;
    ImageButton imgb1;
    TextView signin_txt;
    String url = "http://silptech.com.np/phpscripts/registerRoutine.php";
    RequestQueue requestQueue;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage("Getting you registered. Please wait...");
        dialog.setCancelable(false);

        name = findViewById(R.id.full_name);
        email = findViewById(R.id.email_address);
        passwordd = findViewById(R.id.password);
        signupBtn = findViewById(R.id.btn_signup);
        imgb1 = findViewById(R.id.back);
        signin_txt = findViewById(R.id.signin_txt);


        requestQueue = Volley.newRequestQueue(SignupActivity.this);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            int success = obj1.getInt("success");
                            if (success == 1) {
                                dialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Sign up successful.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Sign up failed.", Toast.LENGTH_LONG).show();

                            }
                        } catch (Exception e) {
                            dialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Internal error.", Toast.LENGTH_LONG).show();

                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(SignupActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> myMap = new HashMap<>();
                        myMap.put("username", name.getText().toString());
                        //myMap.put("email",email.getText().toString());
                        myMap.put("password", passwordd.getText().toString());
                        return myMap;
                    }


                };
                requestQueue.add(sr);

            }
        });


        signin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        imgb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, SplashActivity.class);
                startActivity(i);
            }
        });


    }
}