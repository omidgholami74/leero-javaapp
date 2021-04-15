package com.example.omid.omidbms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omid.omidbms.Socket.Recive;
import com.example.omid.omidbms.Socket.Send;
import com.example.omid.omidbms.Socket.SocketConnection;

public class calculate extends AppCompatActivity implements View.OnClickListener ,TestInterface {
  Button one,two,three,four,five,six,seven,eight,nine,zero,cSend,cRecive,multi,on,off,status;
  EditText edt_sender;
  SocketConnection socketConnection;
  TextView txt_reciver;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calculate);
    init();
    zero.setOnClickListener(this::onClick);
    one.setOnClickListener(this::onClick);
    two.setOnClickListener(this::onClick);
    three.setOnClickListener(this::onClick);
    four.setOnClickListener(this::onClick);
    five.setOnClickListener(this::onClick);
    six.setOnClickListener(this::onClick);
    seven.setOnClickListener(this::onClick);
    eight.setOnClickListener(this::onClick);
    nine.setOnClickListener(this::onClick);
    cRecive.setOnClickListener(this::onClick);
    cSend.setOnClickListener(this::onClick);
    on.setOnClickListener(this::onClick);
    off.setOnClickListener(this::onClick);
    status.setOnClickListener(this::onClick);
    multi.setOnClickListener(this::onClick);

  }
  public static void txtview(String txt){
//        txt_reciver.setText(txt);

  }

  public void init() {
    socketConnection = new SocketConnection(this,this);

    zero       = (Button)  findViewById(R.id.btn_0);
    one        = (Button)  findViewById(R.id.btn_1);
    two        = (Button)  findViewById(R.id.btn_2);
    three      = (Button)  findViewById(R.id.btn_3);
    four       = (Button)  findViewById(R.id.btn_4);
    five       = (Button)  findViewById(R.id.btn_5);
    six        = (Button)  findViewById(R.id.btn_6);
    seven      = (Button)  findViewById(R.id.btn_7);
    eight      = (Button)  findViewById(R.id.btn_8);
    nine       = (Button)  findViewById(R.id.btn_9);
    cSend      = (Button)  findViewById(R.id.btn_clearSend);
    cRecive    = (Button)  findViewById(R.id.btn_clearRecive);
    multi      = (Button)  findViewById(R.id.btn_multi);
    on         = (Button)  findViewById(R.id.btn_on);
    off        = (Button)  findViewById(R.id.btn_off);
    edt_sender = (EditText)findViewById(R.id.edt_writeMatn);
    txt_reciver= (TextView)findViewById(R.id.txt_recive_matn);
    status     = (Button) findViewById(R.id.btn_status);
    txt_reciver.setMovementMethod(new ScrollingMovementMethod());
    cSend.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        edt_sender.setText("");
        return false;
      }
    });
  }

  @Override
  public void onClick(View view) {

    if(view==zero){
      edt_sender.append("0");
    }
    if(view==one){
      edt_sender.append("1");
    }
    if(view==two){
      edt_sender.append("2");
    }
    if(view==three){
      edt_sender.append("3");
    }
    if(view==four){
      edt_sender.append("4");
    }
    if(view==five){
      edt_sender.append("5");
    }
    if(view==six){
      edt_sender.append("6");
    }
    if(view==seven){
      edt_sender.append("7");
    }
    if(view==eight){
      edt_sender.append("8");
    }
    if(view==nine){
      edt_sender.append("9");
    }
    if(view==cRecive){
      txt_reciver.setText("");
    }
    if(view==cSend){
      String str= edt_sender.getText().toString().trim();
      if(str.length()!=0){
        String omid= str.substring(0,str.length()-1);
        edt_sender.setText(omid);
      }
    }
    if(view==multi){
      edt_sender.append(",");
    }
    if(view==on){
      String numouts=edt_sender.getText().toString().trim();
      if(! numouts.isEmpty()){
        String[] numout=numouts.split(",");
        int[] result = new int[numout.length];
        String msg="";
        for(int i=0;i<result.length;i++){
          result[i]=Integer.parseInt(numout[i]);
          msg += "OUT" + result[i] + "ON" + "\r";
          if(Menu.sharedPreferences.getBoolean("by_sms",false)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Menu.sharedPreferences.getString("phoneModem",""), null, msg, null, null);
          }else {
            Send send = new Send(msg,result[i], calculate.this);
            new Thread(send).start();
          }
        }
        edt_sender.append("ON");

        edt_sender.setText("");
      }
    }
    if(view==off){

      String numouts=edt_sender.getText().toString().trim();
      if(! numouts.isEmpty()){
        edt_sender.append("OFF");
        String[] numout=numouts.split(",");
        int[] result = new int[numout.length];
        String msg="";
        for (int i =0;i<result.length;i++){
          result[i]=Integer.parseInt(numout[i]);
          msg += "OUT" + result[i] + "OFF" + "\r";
          if(Menu.sharedPreferences.getBoolean("by_sms",false)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Menu.sharedPreferences.getString("phoneModem",""), null, msg, null, null);
          }else {
            Send send = new Send(msg,result[i], calculate.this);
            new Thread(send).start();
          }
        }

      }
      edt_sender.setText("");
    }
    String[] ports =Menu.sharedPreferences.getString("port", "").split("#");
    if(view==status){
      try {
        for(int i=0;i<ports.length;i++){
          Recive recive = new Recive("GOZARESH",calculate.this);
          new Thread(recive).start();
        }
      }catch (Exception e){

      }

    }
  }

  @Override
  public void setViewInter(int position,String dama, String code) {

  }

  @Override
  public void setReciverMatn(String matn) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        txt_reciver.append(matn+"\n");
      }
    });


  }
}