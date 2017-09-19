package com.app.rakez.a3mw;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.rakez.a3mw.admin.AdminProjects;
import com.app.rakez.a3mw.datastore.Constants;
import com.app.rakez.a3mw.designer.DeProjects;
import com.app.rakez.a3mw.projects.Projects;
import com.app.rakez.a3mw.receiver.UpdateReceiver;
import com.app.rakez.a3mw.services.DataExchange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    ProgressDialog pDialog;
    //ImageView animM1, animM2, animM3, animW1;
    ProgressDialog progressDialog;
    //Animation animFadeIn1,animFadeIn2,animFadeIn3;
    //Animation animationM1, animationM2, animationM3, animationW1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter iF  = new IntentFilter();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
        if(pref.contains("email") && pref.contains("role")){
            String position = pref.getString("role", "");
            if(position.equals("design_section")){
                Intent in = new Intent(MainActivity.this, DeProjects.class);
                startActivity(in);
                finish();
            }else if(position.equals("production")){
                Intent in = new Intent(MainActivity.this, Projects.class);
                startActivity(in);
                finish();
            }else{

            }
        }
            initialize();


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void initialize(){
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        /*animM1 = (ImageView) findViewById(R.id.m1);
        animM2 = (ImageView) findViewById(R.id.m2);
        animM3 = (ImageView) findViewById(R.id.m3);
        animW1 = (ImageView) findViewById(R.id.w);


        animFadeIn1 = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        animFadeIn2 = AnimationUtils.loadAnimation(this,R.anim.fade_in2);
        animFadeIn3 = AnimationUtils.loadAnimation(this,R.anim.fade_in3);
        animationM1 = AnimationUtils.loadAnimation(this,R.anim.m1_effect);
        animationM2 = AnimationUtils.loadAnimation(this,R.anim.m2_effect);
        animationM3 = AnimationUtils.loadAnimation(this,R.anim.m3_effect);
        animationW1 = AnimationUtils.loadAnimation(this,R.anim.w_effect);
        _emailText.startAnimation(animFadeIn1);
        _passwordText.startAnimation(animFadeIn2);
        _loginButton.startAnimation(animFadeIn3);
        animM1.startAnimation(animationM1);
        animM2.startAnimation(animationM2);
        animM3.startAnimation(animationM3);
        animW1.startAnimation(animationW1);*/
    }

    public void login() throws JSONException {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        attempLogin(email,password);
        // TODO: Implement your own authentication logic here.


    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        pDialog.dismiss();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
        String position = pref.getString("role", "");
        if(position.equals("design_section") || position.equals("production")){
            doRefresh();
        }else if(position.equals("director")){
            Intent in = new Intent(MainActivity.this, AdminProjects.class);
            startActivity(in);
            finish();
        }

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    public void attempLogin(final String email, final String password){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.POST,Constants.BASE_URL+Constants.LOGIN_URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject data = response;
                try {
                    if (data.getString("status").equals("success")) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("id",data.getString("id"));
                        editor.putString("email",data.getString("email"));
                        editor.putString("name",data.getString("name"));
                        editor.putString("role",data.getString("role"));
                        editor.putString("token",data.getString("toekn"));
                        editor.commit();
                        onLoginSuccess();
                    }else{
                        pDialog.dismiss();
                        onLoginFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, response.toString());
                pDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
                onLoginFailed();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrReq);
    }

    public void doRefresh(){
        Intent intent = new Intent(this, DataExchange.class);
        Log.d("Service Log","onActivity Called");
        startService(intent);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Under Sync");
        progressDialog.setMessage("Please Wait!!! Data is being Updated");
        progressDialog.show();
        Log.d("Service Log","onActivity2 Called");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressDialog.dismiss();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("userinfo", 0);
            String position = pref.getString("role", "");
            if(position.equals("design_section")){
                Intent in = new Intent(MainActivity.this, DeProjects.class);
                startActivity(in);
                finish();
            }else if(position.equals("production")){
                Intent in = new Intent(MainActivity.this, Projects.class);
                startActivity(in);
                finish();
            }

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(
                DataExchange.BROADCAST_ACTION));
    }

}