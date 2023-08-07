package com.example.tongasoa.vue;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tongasoa.MyHome;
import com.example.tongasoa.R;
import com.example.tongasoa.modele.User;
import com.example.tongasoa.utils.Constante;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class Login extends AppCompatActivity {
    private User user;
    ProgressBar loadingSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TextView email = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loadingSpinner.setVisibility(View.VISIBLE);
                    loginbtn.setEnabled(false);

                    if(TextUtils.isEmpty(email.getText().toString()) || !checkEmail(email.getText().toString())){
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
                    else {
                        verifyLogin(email.getText().toString(), password.getText().toString());
                        Log.e("api", "Login -- : " + user.getId());
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
                Intent intent = new Intent(Login.this, Inscription.class);
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
    private void getData() {
        String api = Constante.BASE_URL+"user";
        RequestQueue queue = Volley.newRequestQueue(this);
        ArrayList<User> allUser = new ArrayList<User>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int i =0 ; i< array.length(); i++){
                                JSONObject simpleObject = array.getJSONObject(i);
                                User u = new User(
                                        simpleObject.getString("id"),
                                        simpleObject.getString("name"),
                                        simpleObject.getString("firstName"),
                                        simpleObject.getString("email"),
                                        simpleObject.getString("password")
                                );
                                allUser.add(u);
                            }
                            for(int i = 0;i< allUser.size();i++){
                                User u = allUser.get(i);
                                Log.e("list User", "user"+ u.getEmail());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("api", "onResponse: "+ response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }
    private void verifyLogin(String email, String password) {
        String url = Constante.BASE_URL + "user/login";
        Log.e("api", " Miditra ato  -- : "+ email+ " - "+ password);
        JSONObject requestBody = new JSONObject();
        user = new User();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
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
                            Log.e("api", "Response -- : "+ user.getId());
                            if(user.getId() == null){
                                Toast.makeText(Login.this,"Error! please verify your email or password", Toast.LENGTH_SHORT).show();

                            }else{

                                // Obtenez une référence aux SharedPreferences
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);

                                // Éditez les SharedPreferences pour ajouter le token
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", response.toString());
                                editor.apply();
                                invalidateOptionsMenu();

                                Intent intent = new Intent(Login.this, MyHome.class);
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
