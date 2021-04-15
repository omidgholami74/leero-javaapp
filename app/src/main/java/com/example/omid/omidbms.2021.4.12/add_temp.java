package com.example.omid.omidbms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.omid.omidbms.adapters.imgdaste_adapter;
import com.example.omid.omidbms.db.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class add_temp extends AppCompatActivity {
    EditText edt_name,edt_out;
    ImageView img_item,img_place;
    Button btn_add,btn_show_list;
    SQLiteHelper db;
    ArrayList<String[]> expertises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_temp);
        init();
        setGallary();


    }
    public void  setGallary(){

        expertises.add(new String[]{"p01"});
        expertises.add(new String[]{"p03"});
        expertises.add(new String[]{"p04"});
        expertises.add(new String[]{"p05"});
        expertises.add(new String[]{"p06"});
        expertises.add(new String[]{"p07"});
        expertises.add(new String[]{"p08"});
        expertises.add(new String[]{"p09"});
        expertises.add(new String[]{"p10"});
        expertises.add(new String[]{"p11"});
        expertises.add(new String[]{"p13"});
        expertises.add(new String[]{"p14"});
        expertises.add(new String[]{"p15"});
        expertises.add(new String[]{"p16"});
        expertises.add(new String[]{"p17"});
        expertises.add(new String[]{"p18"});
        expertises.add(new String[]{"p19"});
        expertises.add(new String[]{"p20"});
        expertises.add(new String[]{"p21"});
        expertises.add(new String[]{"p22"});
        expertises.add(new String[]{"p23"});
        expertises.add(new String[]{"p24"});
        expertises.add(new String[]{"p25"});
        expertises.add(new String[]{"p26"});
        expertises.add(new String[]{"p27"});
        expertises.add(new String[]{"p28"});
        expertises.add(new String[]{"p29"});
        expertises.add(new String[]{"p31"});
        expertises.add(new String[]{"p32"});
        expertises.add(new String[]{"p33"});
        expertises.add(new String[]{"p34"});
        expertises.add(new String[]{"p35"});
        expertises.add(new String[]{"p36"});
        expertises.add(new String[]{"p38"});
        expertises.add(new String[]{"p39"});
        expertises.add(new String[]{"p40"});
        expertises.add(new String[]{"p41"});
        expertises.add(new String[]{"p43"});
        expertises.add(new String[]{"p42"});
        expertises.add(new String[]{"p44"});
        expertises.add(new String[]{"p45"});
        expertises.add(new String[]{"p46"});
        expertises.add(new String[]{"p47"});
        expertises.add(new String[]{"p48"});
        expertises.add(new String[]{"p49"});
        expertises.add(new String[]{"p50"});
        expertises.add(new String[]{"p51"});
        expertises.add(new String[]{"p52"});
        expertises.add(new String[]{"p53"});
        expertises.add(new String[]{"p54"});
        expertises.add(new String[]{"p55"});
        expertises.add(new String[]{"p56"});
        expertises.add(new String[]{"p57"});
        expertises.add(new String[]{"p58"});
        expertises.add(new String[]{"p59"});
        expertises.add(new String[]{"p60"});
        expertises.add(new String[]{"p61"});
        expertises.add(new String[]{"p62"});
        expertises.add(new String[]{"p63"});
        expertises.add(new String[]{"p64"});
        expertises.add(new String[]{"p65"});
        expertises.add(new String[]{"p66"});
        expertises.add(new String[]{"p67"});
        expertises.add(new String[]{"p68"});
        expertises.add(new String[]{"p69"});
        expertises.add(new String[]{"p70"});
        expertises.add(new String[]{"p71"});
        expertises.add(new String[]{"p72"});
        expertises.add(new String[]{"p73"});
        expertises.add(new String[]{"p74"});
        expertises.add(new String[]{"p75"});
        expertises.add(new String[]{"p76"});
        expertises.add(new String[]{"p77"});
        expertises.add(new String[]{"p78"});
        expertises.add(new String[]{"p79"});
        expertises.add(new String[]{"p80"});
        expertises.add(new String[]{"p81"});
        DisplayMetrics displayMetrics = new DisplayMetrics();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycle_add_temp);
        imgdaste_adapter newsAdapter = new imgdaste_adapter(this,expertises,img_place);
        int span = AddHomeItem.Utility.calculatenNoOfColummns(getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this,span, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(newsAdapter);
    }

    private void init() {
        db=new SQLiteHelper(add_temp.this);
        expertises = new ArrayList<>();
        img_place = findViewById(R.id.img_temp_add) ;
        img_item =  findViewById(R.id.imgitem);
        edt_name=findViewById(R.id.edt_temp_add_name);
        edt_out=findViewById(R.id.edt_temp_add_out);
        btn_add=findViewById(R.id.btn_add_temp);
        btn_show_list=findViewById(R.id.btn_list_temp);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString =edt_name.getText().toString().trim();
                String namoutString =edt_out.getText().toString();
                db.insertTemp(nameString,"temp"+namoutString,imageViewToByte(img_place));
            }
        });
        btn_show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_temp.this,temp.class);
                startActivity(intent);
            }
        });
    }
    public  byte[] imageViewToByte(ImageView image) {
        byte[] byteArray = new byte[0];
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200,260, true);
            resized.compress(Bitmap.CompressFormat.PNG, 45,stream);
            byteArray = stream.toByteArray();
        }catch (Exception e){

        }


        return byteArray;
    }
}
