package com.example.omid.omidbms.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omid.omidbms.Menu;
import com.example.omid.omidbms.Socket.Send;
import com.example.omid.omidbms.R;
import com.example.omid.omidbms.Socket.SocketConnection;
import com.example.omid.omidbms.TestInterface;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.multi;

import java.util.List;

public class AdapterMulti extends  RecyclerView.Adapter<AdapterMulti.myViewHolder> {

    Activity context;
    List<multi> multi_list;
    SQLiteDatabase database;
    SQLiteHelper db;
    public AdapterMulti(List<multi> list, Activity context) {
        this.multi_list = list;
        this.context = context;
    }




    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_multi,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        db=new SQLiteHelper(context, "HomeDb.sqlite", null, 1);
        multi_list =db.getMulti();
        SocketConnection socketConnection =new SocketConnection(context);
        multi now = multi_list.get(i);
        myViewHolder.btnOn.setText(now.getName());
        myViewHolder.btnOff.setText(now.getName());



        myViewHolder.btnOn.setOnLongClickListener(new View.OnLongClickListener() {
            List<multi> multi_list=db.getMulti();
            @Override
            public boolean onLongClick(View v) {

              Dialog  thisDialog = new Dialog(context);
                thisDialog.setContentView(R.layout.dialog_lights);
                 EditText numouts = thisDialog.findViewById(R.id.edt_output_pop);
                 EditText name    = thisDialog.findViewById(R.id.edt_nameItem_pop);
                Button btn_apply  = thisDialog.findViewById(R.id.btn_applyItem_pop);
                Button btn_delte  = thisDialog.findViewById(R.id.btn_deleteItem_pop);
                Button btn_cancel = thisDialog.findViewById(R.id.btn_cancelItem_pop);
                thisDialog.show();
                numouts.setText(now.getOutput());
                name.setText(now.getName());
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thisDialog.dismiss();
                    }
                });
                btn_delte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.deleteMulti(now.getId());
                        thisDialog.dismiss();
                        myViewHolder.btnOff.setVisibility(View.GONE);
                        myViewHolder.btnOn.setVisibility(View.GONE);


                    }
                });
                btn_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String output=numouts.getText().toString().trim();
                        String nameString=name.getText().toString();
                        db.editMulti(nameString,output, now.getId());
                         thisDialog.cancel();
                        multi_list =db.getMulti();
                        notifyDataSetChanged();
                    }
                });
                return false;
            }

        });
        myViewHolder.btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (now.getOutput().equals("")) {
                    Toast.makeText(context,"لطفا شماره خروجی را وار د کنید با #",Toast.LENGTH_LONG).show();
                }else {
                    String[] numoutALARMonjoda = now.getOutput().split("#");
                    int[] result = new int[numoutALARMonjoda.length];
                    String msg="";
                    for (int i = 0; i < result.length; i++) {
                        try {
                            result[i] = Integer.parseInt(numoutALARMonjoda[i]);
                        }catch (Exception e){
                            Toast.makeText(context, "مقادیر درست وارد نشده است", Toast.LENGTH_SHORT).show();
                        }

                        Log.i("omidt", "" + result[i]);

                        msg = "OUT" + result[i] + "ON" + "\r";
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Menu.sharedPreferences.getBoolean("by_sms",false)){
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Menu.sharedPreferences.getString("phoneModem",""), null, msg, null, null);
                        }else {
                            Send send = new Send(msg,result[i], (TestInterface) context);
                            new Thread(send).start();
                        }

                    }

                }
            }
        });
        myViewHolder.btnOff.setOnLongClickListener(new View.OnLongClickListener() {
            List<multi> multi_list=db.getMulti();
            @Override
            public boolean onLongClick(View v) {
                Dialog  thisDialog = new Dialog(context);
                thisDialog.setContentView(R.layout.dialog_lights);
                EditText numouts = (EditText) thisDialog.findViewById(R.id.edt_output_pop);
                EditText name = (EditText) thisDialog.findViewById(R.id.edt_nameItem_pop);
                Button btn_apply = (Button) thisDialog.findViewById(R.id.btn_applyItem_pop);
                Button btn_delte = (Button) thisDialog.findViewById(R.id.btn_deleteItem_pop);
                Button btn_cancel = (Button) thisDialog.findViewById(R.id.btn_cancelItem_pop);
                thisDialog.show();
                numouts.setText(now.getOutput());
                name.setText(now.getName());
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thisDialog.dismiss();
                    }
                });
                btn_delte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.deleteMulti(now.getId());
                        thisDialog.dismiss();
                        myViewHolder.btnOff.setVisibility(View.GONE);
                        myViewHolder.btnOn.setVisibility(View.GONE);
                    }
                });
                btn_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String output=numouts.getText().toString().trim();
                        String nameString=name.getText().toString();
                        db.editMulti(nameString,output, now.getId());
                        thisDialog.cancel();
                        multi_list =db.getMulti();
                        notifyDataSetChanged();
                    }
                });
                return false;
            }

        });
        myViewHolder.btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (now.getOutput().equals("")) {
                    Toast.makeText(context,"لطفا شماره خروجی را وار د کنید با #",Toast.LENGTH_LONG).show();
                }else {
                    String[] numoutALARMonjoda = now.getOutput().split("#");
                    int[] result = new int[numoutALARMonjoda.length];
                    String msg="";
                    for (int i = 0; i < result.length; i++) {
                        try {
                            result[i] = Integer.parseInt(numoutALARMonjoda[i]);
                        }catch (Exception e){
                            Toast.makeText(context, "مقادیر درست وارد نشده است", Toast.LENGTH_SHORT).show();
                        }

                        Log.i("omidt", "" + result[i]);

                        msg = "OUT" + result[i] + "OFF" + "\r";
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Menu.sharedPreferences.getBoolean("by_sms",false)){
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Menu.sharedPreferences.getString("phoneModem",""), null, msg, null, null);
                        }else {
                            Send send = new Send(msg,result[i], (TestInterface) context);
                            new Thread(send).start();
                        }
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return multi_list.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder {
        public Button btnOn;
        public Button btnOff;
       public myViewHolder(@NonNull View itemView) {
           super(itemView);
           btnOn=itemView.findViewById(R.id.multi_btn_On);
           btnOff=itemView.findViewById(R.id.multi_btn_Off);
       }
   }


}
