package com.example.omid.omidbms;

import android.app.Dialog;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omid.omidbms.Socket.SocketConnection;
import com.example.omid.omidbms.adapters.AdapterMulti;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.multi;

import java.util.ArrayList;
import java.util.List;

import static com.example.omid.omidbms.Socket.SocketConnection.socket;

public class multi_ACTIVITY extends AppCompatActivity implements TestInterface {
    RecyclerView recyclerView;
    List<multi> list = new ArrayList<>();
    AdapterMulti adapterMulti;
    Button alarmOn,alarmOff;
    FloatingActionButton fab_multi;
    SharedPreferences pref;
    SQLiteHelper db;
    SocketConnection socketConnection;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_socket,menu);




        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("qqqqq",""+item.getItemId());
        if(item.getItemId()==R.id.menu_item_socket) {

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
        if(item.getItemId()==R.id.menu_isConnect)
        {
            try {
                if (socket.isClosed() || socket == null) {
                    Toast.makeText(multi_ACTIVITY.this, "اتصال برقرار نشده است", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(multi_ACTIVITY.this, "به دستگاه متصل می باشد", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception E){
                Toast.makeText(multi_ACTIVITY.this, "اتصال برقرار نشده است", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
      init();
        setView();
              fab_multi.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Dialog dialog = new Dialog(multi_ACTIVITY.this);
                      dialog.setContentView(R.layout.dialog_multi);
                      EditText numouts=dialog.findViewById(R.id.edt_outnumbers_multi);
                      EditText name=dialog.findViewById(R.id.edt_name_multi_setting);
                      Button btn_cancel=dialog.findViewById(R.id.btn_cancelItem_multi);
                      Button btn_delete=dialog.findViewById(R.id.btn_deleteItem_multi);
                      Button btn_apply=dialog.findViewById(R.id.btn_applyItem_multi);


                      btn_cancel.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              dialog.dismiss();
                          }
                      });
                      btn_apply.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              String nameString =name.getText().toString().trim();
                              String namoutString =numouts.getText().toString();
                              db.insertMulti(nameString,namoutString);
                              dialog.dismiss();
                              setView();
                          }
                      });
                      dialog.show();
                  }
              });



    }
private void init(){
        socketConnection=new SocketConnection(this);
    fab_multi = findViewById(R.id.fab_multi);
    pref=getSharedPreferences("multi",MODE_PRIVATE);
    db = new SQLiteHelper(this, "HomeDb.sqlite", null, 1);
    adapterMulti =new AdapterMulti(list,this);
    recyclerView =findViewById(R.id.recycle_multi);
    recyclerView.setLayoutManager(new LinearLayoutManager(multi_ACTIVITY.this));
    alarmOff=findViewById(R.id.multi_Alarm_off);
    alarmOn=findViewById(R.id.multi_alaram_on);
    alarmOn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           socketConnection.sendMessage("ALARMON");
        }
    });
    alarmOff.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            socketConnection.sendMessage("ALARMOFF");
        }
    });
}
    private void setView() {

        adapterMulti =new AdapterMulti(db.getItems(),multi_ACTIVITY.this);
        adapterMulti.notifyDataSetChanged();
        recyclerView.setAdapter(adapterMulti);
    }


    @Override
    public void setViewInter(int position, String code, String dama) {

    }

    @Override
    public void setReciverMatn(String matn) {

    }
}
