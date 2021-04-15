package com.example.omid.omidbms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.omid.omidbms.db.SQLiteHelper;


public class Menu extends AppCompatActivity {
    ImageView img_remote;
    ImageView img_temp;
    ImageView img_mus;
    ImageView img_ir;
    ImageView img_set;
    ImageView  img_sec;
    SQLiteHelper sql;
   public static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcom);
        init();

     img_sec.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(Menu.this, multi_ACTIVITY.class);
             startActivity(intent);
         }
     });
        img_remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, FolderList.class);
                startActivity(intent);
            }
        });
        img_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, setting.class);
                startActivity(intent);
            }
        });
        img_set.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Menu.this,calculate.class);
                startActivity(intent);
                return false;
            }
        });
        img_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, temp.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        //img_ir = (ImageButton) findViewById(R.id.img_ir);
       // img_mus = (ImageView   ) findViewById(R.id.img_mus);
        img_sec = (ImageView) findViewById(R.id.img_sec);
        img_set = (ImageView) findViewById(R.id.img_set);
        img_temp = (ImageView) findViewById(R.id.img_temp);
        img_remote = (ImageView) findViewById(R.id.img_remote);
        sql           = new SQLiteHelper(Menu.this);
        sharedPreferences =getSharedPreferences("myprefs", MODE_PRIVATE);
    }

}
