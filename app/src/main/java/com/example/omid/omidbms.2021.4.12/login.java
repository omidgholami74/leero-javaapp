package com.example.omid.omidbms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omid.omidbms.App.AppController;
import com.example.omid.omidbms.App.a;
import com.example.omid.omidbms.Retrofit.ApiClient;
import com.example.omid.omidbms.Retrofit.ApiInterface;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Retrofit_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class login extends AppCompatActivity {
    EditText username, password;
    TextView txt_go_signUp;
    Button btn_login;
    SQLiteHelper sql;
    public static SharedPreferences sp, pref, mul;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        user = username.getText().toString().trim();
        pass = password.getText().toString().trim();


        txt_go_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().equals("")) {
                    Toast.makeText(login.this, "لطفا نام کاربری خود را وارد کنید", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().trim().equals("")) {
                    Toast.makeText(login.this, "لطفا رمز عبور خود را وارد کنید", Toast.LENGTH_SHORT).show();
                } else {

                    Login(username.getText().toString(), password.getText().toString());

                }
            }
        });
        sp = getSharedPreferences("UserProfile", MODE_PRIVATE);
        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        mul = getSharedPreferences("multi", MODE_PRIVATE);
        String test = sp.getString("username", null);

        final SharedPreferences start = getSharedPreferences("FirstRun", MODE_PRIVATE);
        final SharedPreferences.Editor editor = start.edit();
        Boolean isFirstRun = start.getBoolean("FIRSTRUN", true);

        if (isFirstRun == true) {
            SQLiteHelper sql = new SQLiteHelper(this);
            SharedPreferences.Editor editServer = pref.edit();
            SharedPreferences.Editor edituser = sp.edit();
            SharedPreferences.Editor edtmul = mul.edit();
            edtmul.putString("dayOn", "");
            edtmul.putString("dayOff", "");
            edtmul.putString("nightOn", "");
            edtmul.putString("nightOff", "");
            Log.i("gfngff", "jkjh");
            editServer.putString("server", "");
            editServer.putString("port", "");
            edituser.putString("id", "");
            edituser.putString("username", "");
            edituser.putString("name", "");
            edituser.putString("password", "");
            edituser.putString("phone", "");
            edituser.putString("pin", "");
            edituser.apply();
            editor.putBoolean("FIRSTRUN", false);
            editServer.commit();
            editor.commit();
            edtmul.commit();
        }
        try {

            if (!test.equals("")) {
                Intent intent = new Intent(login.this, Menu.class);
                startActivity(intent);
                finish();

                a.t(login.this, "خوش آمدید..");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void init() {
        username = (EditText) findViewById(R.id.edt_login_userName);
        password = (EditText) findViewById(R.id.edt_login_passWord);
        txt_go_signUp = (TextView) findViewById(R.id.txt_pleaseSignUp);
        btn_login = (Button) findViewById(R.id.btn_login_login);
        sql = new SQLiteHelper(login.this);
    }

    private void Login(String username, String password) {
        String url = "https://leero.iran.liara.run/signin";
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("لطفا صبر کنید ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {

            postData.put("username", username);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {
                String message;
                try {
                    message = response.getString("error");
                    Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
                    Log.i("responseLog1", message);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    String token;
                    try {
                        token = response.getString("token");
                        SharedPreferences.Editor ed = sp.edit();
                        Log.i("responseLog", token);
                        ed.putString("token", token);
                        ed.putString("username", username);
                        ed.commit();
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "خوش آمدید", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(login.this, Menu.class);
                        startActivity(intent);
                    } catch (JSONException ex) {
                        Log.i("responseLog2", "OMID");
                        ex.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("responseLog3", "OMID");
                Toast.makeText(login.this, "خطا در اتصال", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });


        requestQueue.add(jsonObjectRequest);

//        Response.Listener<String> listener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if (!response.equals("") && !response.equals("noFind")) {
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//                        Log.i("jghg",jsonObject.getString("username"));
//                        SharedPreferences.Editor ed = sp.edit();
//                        ed.putString("id", jsonObject.getString("id"));
//                        Log.i("jghg","hfghg");
//                        ed.putString("username", jsonObject.getString("username"));
//                        ed.putString("password", jsonObject.getString("password"));
//                        ed.putString("name", jsonObject.getString("name"));
//                        ed.commit();
//                    } catch (Exception e) {
//                        Log.i("jghg",e.getMessage());
//                    }
//
//                    Intent intent = new Intent(login.this, Menu.class);
//                    startActivity(intent);
//
//                    a.t(login.this, "خوش آمدید..");
//                    finish();
//                } else if (response.equals("noFind")) {
//                    a.t(login.this, "نام کاربری یا رمز عبور اشتباه است");
//                    progressDialog.dismiss();
//                }
//            }
//        };
//
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                a.l("eror", error.getMessage());
//                a.t(login.this, "خطا در اتصال به سرور");
//                progressDialog.dismiss();
//                finish();
//            }
//        };
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("UserName", username);
//                params.put("PassWord", password);
//                return params;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(login.this).add(request);

    }


}
