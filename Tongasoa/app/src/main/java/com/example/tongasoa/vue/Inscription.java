package com.example.tongasoa.vue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tongasoa.MyHome;
import com.example.tongasoa.R;
import com.example.tongasoa.modele.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Inscription extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inscription);

        TextView name = (TextView) findViewById(R.id.name);
        TextView firstname = (TextView) findViewById(R.id.firstname);
        TextView email = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);
        TextView passwordValid = (TextView) findViewById(R.id.passwordValid);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loadingSpinner.setVisibility(View.VISIBLE);
                    loginbtn.setEnabled(false);
                    Toast.makeText(Inscription.this, "inscription", Toast.LENGTH_SHORT).show();
                    user = new User();
                    user.setName(name.getText().toString());
                    user.setFirstName(firstname.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    if(TextUtils.isEmpty(name.getText().toString())){
                        name.setError("Name required");
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                        return;
                    }
                    if(TextUtils.isEmpty(firstname.getText().toString())){
                        firstname.setError("Firstname required");
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                        return;
                    }
                    if(TextUtils.isEmpty(email.getText().toString())|| !checkEmail(email.getText().toString())){
                        email.setError("Email required");
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                        return;
                    }
                    if(TextUtils.isEmpty(password.getText().toString())){
                        password.setError("Password required");
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                        return;
                    }
                    if(password.getText().toString().length()<=4){
                        password.setError("Password length to superior 4 ");
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                        return;
                    }

                    if(password.getText().toString().equals(passwordValid.getText().toString()) == false){
                        passwordValid.setError("Password different of valid password");
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                        return;
                    }
                    else {
                        register(user);
                        Log.e("api", "Inscription -- : " + user.getId());
                    }
                    new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingSpinner.setVisibility(View.GONE);
                        loginbtn.setEnabled(true);
                    }
                }, 7000);
            }
        });

        TextView signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inscription.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static boolean checkEmail(String email) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    private void register(User userin) {
        String url = "https://47cf-154-126-56-74.ngrok-free.app/user/inscription";
        Log.e("api", " Miditra ato  -- : ");
        JSONObject requestBody = new JSONObject();
        user = new User();
        try {
            requestBody.put("name", userin.getName());
            requestBody.put("firstName", userin.getFirstName());
            requestBody.put("email", userin.getEmail());
            requestBody.put("password", userin.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<User> allUser = new ArrayList<User>();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        try{
                            Gson gson = new Gson();
                            user = gson.fromJson(response.toString(), User.class);
                            Log.e("api", "Response Inscription -- : "+ response.toString());

                            if(user.getId() == null){
                                Toast.makeText(Inscription.this,response.getString("message") , Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent = new Intent(Inscription.this, MyHome.class);
                                startActivity(intent);
                                finish();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("api", "error -- : "+ e.getLocalizedMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

}
