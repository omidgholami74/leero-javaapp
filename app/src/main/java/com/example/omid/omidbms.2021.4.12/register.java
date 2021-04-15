package com.example.omid.omidbms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    Button btn_register;
    EditText edt_reapeat_password, edt_name, edt_userName, edt_password, edt_phone,edt_ip,edt_port;
    ProgressDialog progressDialog;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString().trim();
                String username = edt_userName.getText().toString().trim();
                String pass = edt_password.getText().toString().trim();
                String pass_reap = edt_reapeat_password.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                String ip = edt_ip.getText().toString().trim();
                String port = edt_port.getText().toString().trim();
                if (username.equals("")) {
                    a.t(register.this, "لطفا نام کاربری خود را وارد کنید");
                } else if (pass.equals("")) {
                    a.t(register.this, "لطفا پسورد خود را وارد کنید");
                } else if (pass_reap.equals("")) {
                    a.t(register.this, "لطفا پسورد خود را مجددا وارد کنید");
                } else if (!pass.equals(pass_reap)) {
                    a.t(register.this, "پسوردهایتان مشابه نمی باشد");
                } else if (phone.equals("")) {
                    a.t(register.this, "لطفا شماره همراه خود را وارد کنید");
                } else {

                    register(name, username, pass,ip,port, phone);
                }
            }
        });
    }

    private void init() {
        btn_register = (Button) findViewById(R.id.btn_signUp);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_password = (EditText) findViewById(R.id.edt_signUp_passWord);
        edt_reapeat_password = (EditText) findViewById(R.id.edt_rapeat_password);
        edt_userName = (EditText) findViewById(R.id.edt_signUp_username);
        edt_phone = findViewById(R.id.reg_edt_phone);
        edt_ip = findViewById(R.id.reg_edt_ip);
        edt_port = findViewById(R.id.reg_edt_port);


    }

    private void register(final String name, final String userName, final String passWord, String ip,String port,String phone) {

        String url = "https://leero.iran.liara.run/signup";

        final ProgressDialog progressDialog = new ProgressDialog(register.this);
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", name);
            postData.put("username", userName);
            postData.put("password", passWord);
            postData.put("ip_static", ip);
            postData.put("port",port);
            postData.put("phone", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String message;
                try {
                   message = response.getString("message");
                    Toast.makeText(register.this,message,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    Log.i("regierror", String.valueOf(e));
                    Toast.makeText(register.this,"با خطا مواجه شده است",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);


    }
}
