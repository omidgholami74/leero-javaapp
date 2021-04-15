package com.example.omid.omidbms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.omid.omidbms.adapters.imgdaste_adapter;
import com.example.omid.omidbms.db.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AddHomeItem extends AppCompatActivity {
    SharedPreferences shareftime;
    Button btnAdd;
    EditText name;
    Button btnListItem;
    EditText edt_port;
    ImageView img;
    CheckBox chek;
    EditText edtmoment;

    public static  SQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 634;
    ImageView imgitembala;
    ImageView imgitem;
    public static class Utility{
        public static int calculatenNoOfColummns(Context context){
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpwidth = displayMetrics.widthPixels/displayMetrics.density;
            int noOfColumns =(int) (dpwidth/122);
            return  noOfColumns;
        }
    }
    public  String imageViewToPath(ImageView image) {
        String imageName = "";
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            //ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 260, true);
           // resized.compress(Bitmap.CompressFormat.PNG, 45, stream);
            File extStorageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageName="file_"+Math.round(Math.random()*10000000)+".png";
            File imageFile= new File(extStorageDir,imageName);
            FileOutputStream fos = new FileOutputStream(imageFile);
           // bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            resized.compress(Bitmap.CompressFormat.PNG, 45, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {

        }
        return imageName;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_items);
       imgitembala = (ImageView) findViewById(R.id.imageView1) ;
        imgitem = (ImageView) findViewById(R.id.imgitem);
        ArrayList<String[]> expertises = new ArrayList<>();
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
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycleview1);
        imgdaste_adapter newsAdapter = new imgdaste_adapter(this,expertises,imgitembala);
        int span =Utility.calculatenNoOfColummns(getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this,span,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(newsAdapter);
        sqLiteHelper = new SQLiteHelper(AddHomeItem.this, "HomeDb.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Home(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, id_folder INTEGER, image BLOB, isfolder INTEGER,out_number INTEGER  UNIQUE,timeDelay INTEGER ,IP VARCHAR(40))");
        name = findViewById(R.id.name);
        btnAdd = findViewById(R.id.btnAddI);
        btnListItem = findViewById(R.id.btnListI);
        edt_port = findViewById(R.id.edtnumber);
        chek = findViewById(R.id.chek);
        edtmoment = findViewById(R.id.edtmoment);
        img = findViewById(R.id.imageView1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddHomeItem.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        final String folder_name=getIntent().getStringExtra("folder_name");
        btnListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
     chek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(chek.isChecked()){
                 edtmoment.setVisibility(View.VISIBLE);
             }else {
                 edtmoment.setVisibility(View.INVISIBLE);
                 edtmoment.setText("");
             }
         }
     });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper = new SQLiteHelper(AddHomeItem.this, "HomeDb.sqlite", null, 1);
                String timeDelay = edtmoment.getText().toString();
                sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Home(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, id_folder INTEGER, image BLOB, isfolder INTEGER,out_number INTEGER UNIQUE,send INTEGER,timeDelay VARCHAR)");
                try {
                    sqLiteHelper.insertData(
                            name.getText().toString().trim(),
                            imageViewToPath(img),
                            0,
                            folder_name,
                            Integer.parseInt(edt_port.getText().toString()),0,timeDelay);

                    Toast.makeText(getApplicationContext(), "آیتم اضافه شد", Toast.LENGTH_SHORT).show();
                    HomeItem.Instance().iniRec();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("ddz",e.getMessage());
                }
                edt_port.setText("");
                name.setText("");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
//    public  void shareftime (String key ,int time){
//         shareftime = getApplicationContext().getSharedPreferences("mytimes",0);
//        SharedPreferences.Editor timepref= shareftime.edit();
//        timepref.putInt(key,time);
//        timepref.apply();
//
//    }


}
