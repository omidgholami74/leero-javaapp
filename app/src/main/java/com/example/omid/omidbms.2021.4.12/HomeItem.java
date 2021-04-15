package com.example.omid.omidbms;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.omid.omidbms.Socket.Recive;
import com.example.omid.omidbms.Socket.Send;
import com.example.omid.omidbms.Socket.SocketConnection;
import com.example.omid.omidbms.adapters.AdapterHomeItems;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Home;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.omid.omidbms.Socket.SocketConnection.socket;

public class HomeItem extends AppCompatActivity implements TestInterface{
    public static final String EXTRA_MESSAGE = "space.mzero.tcpz.MESSAGE";
    public static String msg = "default";
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    FloatingActionButton statusAction;
    public  static SharedPreferences pref;
    Dialog dialog;
    ArrayList<Send> sockets;
    ArrayList<Integer> port;
    private static HomeItem homeItem;
    public static SQLiteHelper sqLiteHelper;
    public static List<Home> list = new ArrayList<>();
    static String time;
    static int timeInt;

    SocketConnection socketConnection;
TestInterface testInterface;

    public static HomeItem Instance() {
        if (homeItem == null) homeItem = new HomeItem();
        return homeItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dexter.withActivity(HomeItem.this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if(response!=null && response.isPermanentlyDenied()){
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        sockets = new ArrayList<>();
        port = new ArrayList<>();
        homeItem = this;
        actionButton = findViewById(R.id.config);
        statusAction = findViewById(R.id.status);

        socketConnection = new SocketConnection(this);
        recyclerView = findViewById(R.id.rec);
        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        iniRec();
        String[] ports =pref.getString("port", "").split("#");
        statusAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for(int i=0;i<ports.length;i++){
                        Recive recive = new Recive("GOZARESH",HomeItem.this);
                        new Thread(recive).start();
                    }
                }catch (Exception E){

                }

            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeItem.this, AddHomeItem.class);
                intent.putExtra("folder_name", getIntent().getStringExtra("folder_name"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_socket, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("qqqqq", "" + item.getItemId());
        if (item.getItemId() == R.id.menu_item_socket) {

            try {
                for (int i = 0; i < 3; i++) {
                    SocketConnection.connect(pref.getString("server", "192.168.1.16"), pref.getInt("port", 8080));
                }
            } catch (Exception e) {

            }
            Drawable yourdrawable = item.getIcon(); // change 0 with 1,2 ...
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
        if (item.getItemId() == R.id.menu_isConnect) {
            try {
                if (socket.isClosed() || socket == null) {
                    Toast.makeText(HomeItem.this, "اتصال برقرار نشده است", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeItem.this, "به دستگاه متصل می باشد", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception E) {
                Toast.makeText(HomeItem.this, "اتصال برقرار نشده است", Toast.LENGTH_SHORT).show();
            }
            Drawable yourdrawable = item.getIcon(); // change 0 with 1,2 ...
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

    public void iniRec() {
        sqLiteHelper = new SQLiteHelper(this, "HomeDb.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Home(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR , name_folder VARCHAR,image VARCHAR, isfolder INTEGER,out_number INTEGER  UNIQUE,send INTEGER,timeDelay VARCHAR,port INTEGER)");
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM Home WHERE isfolder==0 AND name_folder==" + "'" + getIntent().getStringExtra("folder_name") + "'");
        // Log.i("xxz",cursor.getCount()+"");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(3);
            int port = Integer.parseInt(cursor.getString(5));

            list.add(new Home(id, name, image, port));
            Log.i("listsize", "" + list.size());//ok

        }


        iniRecyclerview();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void iniRecyclerview() {
        final int span = AddHomeItem.Utility.calculatenNoOfColummns(getApplicationContext());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdapterHomeItems adapterHomeItems = new AdapterHomeItems(HomeItem.this, list);
                recyclerView.setLayoutManager(new GridLayoutManager(HomeItem.this, span));
                recyclerView.setAdapter(adapterHomeItems);

            }
        });

    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void setViewInter(int position, String code, String dama) {

    }

    @Override
    public void setReciverMatn(String matn) {

    }
}
