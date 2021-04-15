package com.example.omid.omidbms;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.omid.omidbms.Socket.Send;
import com.example.omid.omidbms.Socket.SocketConnection;
import com.example.omid.omidbms.adapters.AdapterTemp;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.temp_model;

import java.util.ArrayList;
import java.util.List;

public class temp extends AppCompatActivity implements TestInterface {
    FloatingActionButton fab;
    SQLiteHelper db;
    RecyclerView recyclerView;
     public static AdapterTemp adapterTemp;
    SocketConnection socketConnection;

    TestInterface testInterface;
    public static List<temp_model> list = new ArrayList<>();
    private static temp temp_u;

    public static temp Instance() {
        if (temp_u == null) temp_u = new temp();
        return temp_u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Log.e("qqqq", "onCreate: ");
        init();
        setView();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycle_temp);
        socketConnection= new SocketConnection(this,this);
        db = new SQLiteHelper(temp.this);
        list = db.getTemp();
        fab = findViewById(R.id.fab_temp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(temp.this, add_temp.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_temp, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("qqqqq", "" + item.getItemId());
//        if (Items.getItemId() == R.id.menu_add_temp) {
//            Intent intent = new Intent(this, add_temp.class);
//            startActivity(intent);
//            Drawable yourdrawable = Items.getIcon(); // change 0 with 1,2 ...
//            yourdrawable.mutate();
//            yourdrawable.setColorFilter(getResources().getColor(R.color.blue_900), PorterDuff.Mode.SRC_IN);
//            new CountDownTimer(1000, 1000) {
//                public void onFinish() {
//                    // When timer is finished
//                    // Execute your code here
//                    Drawable yourdrawable = Items.getIcon(); // change 0 with 1,2 ...
//                    yourdrawable.mutate();
//                    yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
//                }
//
//                public void onTick(long millisUntilFinished) {
//                    Drawable yourdrawable = Items.getIcon(); // change 0 with 1,2 ...
//                    yourdrawable.mutate();
//                    yourdrawable.setColorFilter(getResources().getColor(R.color.green_400), PorterDuff.Mode.SRC_IN);
//                }
//            }.start();
//        }socketConnection.sendMessage("TEMPERATURE");
        String[] ports = com.example.omid.omidbms.Menu.sharedPreferences.getString("port", "").split("#");
        if (item.getItemId() == R.id.menu_status_temp) {
            for(int i=0;i<ports.length;i++){
                Send send = new Send("TEMPERATURE",Integer.parseInt(ports[i]),this);
                new Thread(send).start();
            }



            Drawable yourdrawable = item.getIcon(); // change 0 with 1,2c  ...
            yourdrawable.mutate();
            yourdrawable.setColorFilter(getResources().getColor(R.color.blue_900), PorterDuff.Mode.SRC_IN);
            new CountDownTimer(1000, 1000) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    Drawable yourdrawable = item.getIcon(); // change 0 with 1,2 ...
                    yourdrawable.mutate();
                    yourdrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
                }

                public void onTick(long millisUntilFinished) {
                    Drawable yourdrawable = item.getIcon(); // change 0 with 1,2 ...
                    yourdrawable.mutate();
                    yourdrawable.setColorFilter(getResources().getColor(R.color.green_400), PorterDuff.Mode.SRC_IN);
                }
            }.start();

        }
        return super.onOptionsItemSelected(item);
    }


    public  void setView() {
        Log.e("qqqq", "run: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(temp.this));
        adapterTemp = new AdapterTemp(list, temp.this);
        recyclerView.setAdapter(adapterTemp);


    }


    @Override
    public void setViewInter(int position,String dama, String CODE) {
        Log.e("qqqEq", "run1: " + dama + " " + CODE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                db.insertdama(dama,CODE);
                setView();
            }
        });


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                //list = db.getTemp();
//                list.get(position).setTemp(dama);
//                recyclerView.setLayoutManager(new LinearLayoutManager(temp.this));
//                adapterTemp = new AdapterTemp(list, temp.this);
//                Log.e("qqqq", "run1: " + list.get(0).getOut() + " " + list.get(0).getTemp());
//                recyclerView.setAdapter(adapterTemp);
//                adapterTemp.notifyDataSetChanged();
//            }
//        });
    }
    public void updateValue() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                adapterTemp.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void setReciverMatn(String matn) {

    }
}