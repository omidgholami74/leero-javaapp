package com.example.omid.omidbms.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omid.omidbms.AddHomeItem;
import com.example.omid.omidbms.FolderList;
import com.example.omid.omidbms.HomeItem;
import com.example.omid.omidbms.Socket.Send;
import com.example.omid.omidbms.R;
import com.example.omid.omidbms.Socket.SocketConn;
import com.example.omid.omidbms.Socket.SocketConnection;
import com.example.omid.omidbms.TestInterface;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Home;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AdapterHomeItems extends RecyclerView.Adapter<AdapterHomeItems.viewholder> {
    private Button mSpeak;
    private boolean isSpeakButtonLongPressed = false;

    Activity context;
    List<Home> list;
    SQLiteHelper sqLiteHelper;
    ArrayList<Home> listdelete;
    Dialog dialog;
    int portIndex;
    AddHomeItem addHomeItem;
    SocketConn socketCon;
    AdapterHomeItems adapter = null;
    private String status = "";
    SQLiteHelper db;
    SQLiteDatabase database;
    int TimeDelay = 0;
    SharedPreferences preferences;

    public AdapterHomeItems(Activity context, List<Home> list) {
        this.context = context;
        this.list = list;
    }

    public String getTimeDelay(int i) {
        db = new SQLiteHelper(context, "HomeDb.sqlite", null, 1);
        database = db.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT timeDelay FROM Home WHERE Id = " + "'" + list.get(i).getId() + "'", null);
        String timeDelay = "";
        while (cursor.moveToNext()) {
            timeDelay = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return timeDelay;

    }


    public AdapterHomeItems(Activity context, int food_item, ArrayList<Home> listdelete) {

    }
    public AdapterHomeItems(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //nokte: viewGrup context mide . niazi nist context begiri az tarighe constructor
        //viewGroup.getContext();
        View group = context.getLayoutInflater().inflate(R.layout.food_item, null);
        return new viewholder(group);
    }

    SocketConnection socketConnection = new SocketConnection(context);

    @Override
    public void onBindViewHolder(@NonNull final viewholder viewholder, final int i) {
        preferences=context.getSharedPreferences("myprefs", MODE_PRIVATE);
        socketCon= new SocketConn(context);
        viewholder.name.setText(list.get(i).getName());
        String photoPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + list.get(i).getImage();
        Bitmap bitmap1 = BitmapFactory.decodeFile(photoPath);
        viewholder.imageView.setImageBitmap(bitmap1);
        if (list.get(i).isOn()) viewholder.name.setBackgroundColor(Color.GREEN);
        else viewholder.name.setBackgroundColor(Color.RED);
        viewholder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                Dialog dialog1Item = new Dialog(context);
                dialog1Item.setContentView(R.layout.dialog_items);
                Button btn_apply = dialog1Item.findViewById(R.id.btn_applyItem);
                Button btn_cancel = dialog1Item.findViewById(R.id.btn_cancelItem);
                Button btn_delete = dialog1Item.findViewById(R.id.btn_deleteItem);
                EditText edt_outnumber = dialog1Item.findViewById(R.id.edt_outnumber);
                EditText edt_nameItem = dialog1Item.findViewById(R.id.edt_nameItem);
                EditText edt_time = dialog1Item.findViewById(R.id.edt_timeItem);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogDelete(i);
                        dialog1Item.dismiss();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1Item.dismiss();
                    }
                });
                db = new SQLiteHelper(context, "HomeDb.sqlite", null, 1);
                database = db.getReadableDatabase();
                Cursor cursor = database.rawQuery("SELECT Id FROM Home WHERE out_number ", null);
                Log.i("qqqq", "onLongClick: " + list.get(i).getId());
                String Time = getTimeDelay(i);
                String outNumber = String.valueOf(list.get(i).getPort());
                String name = list.get(i).getName();
                edt_nameItem.setText(name);
                edt_outnumber.setText(outNumber);
                edt_time.setText(Time);
                btn_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String time1 = edt_time.getText().toString();
                        String outNumber1 = edt_outnumber.getText().toString();
                        String name1 = edt_nameItem.getText().toString();
                        try {
                            db.editItem(name1, Integer.parseInt(outNumber1), time1, list.get(i).getId());
                            db.close();
                        } catch (Exception e) {

                        }

                        dialog1Item.dismiss();

                        del();
                        HomeItem.Instance().iniRec();
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog1Item.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Bitmap ByteToBitmap(byte[] byteArray) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bmp;
    }



    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView name;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_food);
            name = itemView.findViewById(R.id.txtName);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int portItem =list.get(getAdapterPosition()).getPort();
            Log.i("shahla",""+portItem);
            if (!getTimeDelay(getAdapterPosition()).equals("")) {
                TimeDelay = Integer.parseInt(getTimeDelay(getAdapterPosition()));
                Toast.makeText(context, "آیتم در حالت لحظه ای قرار گرفت", Toast.LENGTH_SHORT).show();
                if(preferences.getBoolean("by_sms",false)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(preferences.getString("phoneModem",""), null, "OUT" + portItem + "ON", null, null);
                }else {
                    Send send = new Send("OUT" + portItem + "ON",portItem, (TestInterface) context);
                    new Thread(send).start();
                }
                new CountDownTimer(TimeDelay * 1000, 1000) {
                    public void onFinish() {
                        // When timer is finished
                        // Execute your code here
                        if(preferences.getBoolean("by_sms",false)){
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(preferences.getString("phoneModem",""), null, "OUT" + portItem + "OFF", null, null);
                        }else {
                            Send send = new Send("OUT" + portItem + "OFF",portItem, (TestInterface) context);
                            new Thread(send).start();
                        }
                        new CountDownTimer(100, 1000) {
                            public void onFinish() {
                                // When timer is finished
                                // Execute your code here
                                if(preferences.getBoolean("by_sms",false)){
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(preferences.getString("phoneModem",""), null, "OUT" + portItem + "OFF", null, null);
                                }else {
                                    Send send = new Send("OUT" + portItem + "OFF",portItem, (TestInterface) context);
                                    new Thread(send).start();
                                }
                                name.setBackgroundColor(Color.RED);
                            }

                            public void onTick(long millisUntilFinished) {
                            }
                        }.start();
                        name.setBackgroundColor(Color.RED);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                        name.setBackgroundColor(Color.YELLOW);
                    }
                }.start();

            } else {
                switch (v.getId()) {
                    case R.id.img_food:
                        list.get(getAdapterPosition()).setOn(!list.get(getAdapterPosition()).isOn());
                        String msg = "";
                        String on_off = "";

                        if (list.get(getAdapterPosition()).isOn()) {
                            on_off = "ON";
                            name.setBackgroundColor(Color.GREEN);
                        } else {
                            on_off = "OFF";
                            name.setBackgroundColor(Color.RED);
                        }
                        int outNumber = list.get(getAdapterPosition()).getPort();
                        if(preferences.getBoolean("by_sms",false)){
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(preferences.getString("phoneModem",""), null, "OUT" + portItem + on_off, null, null);
                        }else {
                            Send send = new Send("OUT" + portItem + on_off,portItem, (TestInterface) context);
                            new Thread(send).start();
                        }
                        break;
                }
            }
        }
    }

    public int getItem(int strnum) {
        for (int position = 0; position < list.size(); position++)
            if (list.get(position).getPort() == strnum)
                return position;
        return 0;
    }

    public interface OnItemClickListener {

        void onClicked(int position);

    }

    public void del() {
        sqLiteHelper = new SQLiteHelper(context, "HomeDb.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Home(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, id_folder INTEGER, image BLOB, isfolder INTEGER,out_number INTEGER)");
        listdelete = new ArrayList<>();
        Cursor cursor = FolderList.sqLiteHelper.getData("SELECT * FROM Home WHERE isfolder==0");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int port = Integer.parseInt(cursor.getString(5));
            String name = cursor.getString(1);
            String image = cursor.getString(3);
            listdelete.add(new Home(id, name, image, port));
            cursor.moveToNext();
            adapter = new AdapterHomeItems(context, R.layout.food_item, listdelete);
        }
    }

    private void showDialogDelete(final int index) {
        del();
        final android.support.v7.app.AlertDialog.Builder dialogDelete = new android.support.v7.app.AlertDialog.Builder(context);
        final int idFood = list.get(index).getId();
        dialogDelete.setMessage("آیا مایل به جذف آیتم می باشید");
        dialogDelete.setPositiveButton("قبول", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Log.i("omiftr", "" + listdelete.size());
                    Log.i("omiftr", "" + idFood);
                    try {
                        sqLiteHelper.deleteDataitem(idFood);
                        Toast.makeText(context, "با موفقیت حذف گردید", Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException e) {
                        Toast.makeText(context, "برای حذف ابتدا باید دکمه مثبت را زده و دوباره به اینجا برگردید", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                listdelete.remove(index);
                HomeItem.Instance().iniRec();
                adapter.notifyDataSetChanged();
            }

        });
        dialogDelete.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

}