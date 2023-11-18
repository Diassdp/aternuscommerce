package com.aplikasi.aternuscommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText fullnameEditText, usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private TextView debug;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameEditText = findViewById(R.id.fullnameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        debug = findViewById(R.id.debug);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = fullnameEditText.getText().toString().trim();
                final String username = usernameEditText.getText().toString().trim();
                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();
                final String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                Connection connection = new Connection();
                String url = connection.getUrlSetup()+"Register.php";

                if (password.equals(confirmPassword)) {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");

                                        if (success) {
                                            Intent intent = new Intent(Register.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            String errorMessage = jsonResponse.getString("message");
                                            Toast.makeText(Register.this, errorMessage, Toast.LENGTH_SHORT).show();
                                            debug.setText(errorMessage);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(Register.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                                        debug.setText("Error parsing JSON");
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            debug.setText("Error: " + error.getMessage());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("fullname", fullname);
                            params.put("username", username);
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                } else {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
