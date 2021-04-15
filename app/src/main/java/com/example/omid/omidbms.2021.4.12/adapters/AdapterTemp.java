package com.example.omid.omidbms.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omid.omidbms.R;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.temp_model;
import com.tikou.mylibrary.UiSeeKBar;

import java.util.ArrayList;
import java.util.List;

public class AdapterTemp extends RecyclerView.Adapter<AdapterTemp.holderTemp> {
List<temp_model> list;
Context context;
SQLiteHelper db;


    public Bitmap ByteToBitmap(byte[] byteArray) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bmp;
    }
    public AdapterTemp(List<temp_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holderTemp onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_temp,viewGroup,false);
        return new holderTemp(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holderTemp holderTemp, int i) {
        db=new SQLiteHelper(context);
        list=db.getTemp();
        temp_model itemNow = list.get(i);
        holderTemp.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("آیا مایل به حذف آیتم می باشید");
                builder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("پاک کردن", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteTemp(itemNow.getId());
                        holderTemp.itemView.setVisibility(View.GONE);
                    }
                });
                builder.show();
            }
        });
        holderTemp.txt_name.setText(itemNow.getName());
        holderTemp.img_temp.setImageBitmap(ByteToBitmap(itemNow.getImg()));
        holderTemp.txt_temp.setText(itemNow.getTemp());
        holderTemp.btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        try {
            holderTemp.seeKBar.setProgress(Integer.parseInt(itemNow.getTemp()));
        }catch (Exception e){
            holderTemp.seeKBar.setProgress(0);
        }
//       try {
//           holderTemp.seeKBar.setNumTextSize();
//       }catch (Exception e){
//           holderTemp.seeKBar.setNumTextSize(2);
//       }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class   holderTemp extends RecyclerView.ViewHolder{
        TextView txt_temp;
        TextView txt_name;
        ImageView img_temp;
        UiSeeKBar seeKBar ;
        Button btn_send;
        public holderTemp(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txtName_temp);
            txt_temp=itemView.findViewById(R.id.txt_temp);
            img_temp=itemView.findViewById(R.id.img_temp);
            seeKBar=itemView.findViewById(R.id.temp_seekbar);
            btn_send =itemView.findViewById(R.id.send_temp);
        }
    }


}
