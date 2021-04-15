package com.example.omid.omidbms;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omid.omidbms.db.SQLiteHelper;

import java.io.File;

public class setting extends AppCompatActivity {
   static SharedPreferences  pref;
    Button btn_save;
    EditText edt_server;
    EditText edt_port,edt_temp,edt_phone,edt_releh;
    RadioGroup radioGroup;
    RadioButton rdo_net,rdo_sms;
    TextView txt_phone;
    public static final int REQUEST_CODE_PERMISSIONS = 2;
    SQLiteHelper db;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        radioGroup = findViewById(R.id.radio_type_send);
        rdo_sms=findViewById(R.id.radio_sms);
        pref = getSharedPreferences("myprefs",MODE_PRIVATE);
        btn_save =(Button) findViewById(R.id.btn_save);
        edt_server = (EditText) findViewById(R.id.edt_server);
        edt_port = (EditText) findViewById(R.id.edt_port);
        edt_temp =findViewById(R.id.edt_temp_tedad);
        edt_phone=findViewById(R.id.edt_phone_Set);
        rdo_net =findViewById(R.id.radio_net);
        txt_phone=findViewById(R.id.txt_phone_set);
        edt_releh=findViewById(R.id.edt_releh_tedad);
        String serverShare ;
        String PORT;
        PORT =pref.getString("port","");
      String  releh =pref.getString("numreleh","");
        boolean type_send =pref.getBoolean("by_sms",false);
        if(type_send){
            radioGroup.check(rdo_sms.getId());
            edt_phone.setText(pref.getString("phoneModem",""));
            edt_phone.setVisibility(View.VISIBLE);
            txt_phone.setVisibility(View.VISIBLE);
        }else {
            radioGroup.check(rdo_net.getId());
        }
        edt_port.setText(PORT);
        edt_releh.setText(releh);
        serverShare=pref.getString("server","192.168.16.254");
        edt_server.setText(serverShare);
        edt_temp.setText(pref.getString("numtemp","1"));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radio_sms){
                    txt_phone.setVisibility(View.VISIBLE);
                    edt_phone.setVisibility(View.VISIBLE);
                }else {
                    txt_phone.setVisibility(View.GONE);
                    edt_phone.setVisibility(View.GONE);
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = pref.edit();
                String server= edt_server.getText().toString().trim();
                String port= edt_port.getText().toString().trim();
                String releh= edt_releh.getText().toString().trim();
                String phone =edt_phone.getText().toString();
                Toast.makeText(setting.this,"ذخیره شد",Toast.LENGTH_LONG).show();
                int id_selected=radioGroup.getCheckedRadioButtonId();
                radioButton =findViewById(id_selected);
                if(!edt_phone.getText().toString().equals("") && radioButton.getText().toString().equals("پیامک") ){
                    editor.putBoolean("by_sms",true);
                    editor.putString("phoneModem",phone);
                   Log.i("erfww","omid");
                }else if(radioButton.getText().toString().equals("شبکه")){
                    editor.putBoolean("by_sms",false);
                }
                editor.putString("server",server);
                editor.putString("port",port.trim());
                editor.putString("numtemp",edt_temp.getText().toString());
                editor.putString("numreleh",edt_releh.getText().toString());
                editor.commit();
                Toast.makeText(setting.this,"ذخیره شد",Toast.LENGTH_LONG).show();
               edt_port.setText(port);

               editor.apply();
            }
        });


    }

}
