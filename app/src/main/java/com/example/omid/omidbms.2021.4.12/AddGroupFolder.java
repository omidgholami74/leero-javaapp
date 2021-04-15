package com.example.omid.omidbms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.omid.omidbms.adapters.imgdaste_adapter;
import com.example.omid.omidbms.db.SQLiteHelper;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AddGroupFolder extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 999;
    EditText edtName;
    Button btnChoose, btnAdd, btnList;
    ImageView imageView;
    ImageView imgcat;

    public static class spanSize {
        public static int columnsOfNumber(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpwidth = displayMetrics.widthPixels / displayMetrics.density;
            int numofColumns = (int) dpwidth / 122;
            return numofColumns;
        }
    }

    public  String imageViewToPath(ImageView image) {
        String imageName = "";
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 260, true);
            File extStorageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageName="file_"+Math.round(Math.random()*10000000)+".png";
            File imageFile= new File(extStorageDir,imageName);
            FileOutputStream fos = new FileOutputStream(imageFile);
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

        setContentView(R.layout.activity_dastebandi);
        imageView = (ImageView) findViewById(R.id.imageViewF);
        imgcat = (ImageView) findViewById(R.id.imgcat);
        ArrayList<String[]> expertises = new ArrayList<>();
        expertises.add(new String[]{"pic01"});
        expertises.add(new String[]{"pic02"});
        expertises.add(new String[]{"pic03"});
        expertises.add(new String[]{"pic04"});
        expertises.add(new String[]{"pic06"});
        expertises.add(new String[]{"pic07"});
        expertises.add(new String[]{"pic08"});
        expertises.add(new String[]{"pic09"});
        expertises.add(new String[]{"pic10"});
        expertises.add(new String[]{"pic11"});
        expertises.add(new String[]{"pic12"});
        expertises.add(new String[]{"pic13"});
        expertises.add(new String[]{"pic14"});
        expertises.add(new String[]{"pic15"});
        expertises.add(new String[]{"pic16"});
        expertises.add(new String[]{"pic17"});
        expertises.add(new String[]{"p12"});
        expertises.add(new String[]{"p37"});

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        imgdaste_adapter newsAdapter = new imgdaste_adapter(this, expertises, imageView);
        int span = spanSize.columnsOfNumber(getApplicationContext());

        recyclerView.setLayoutManager(new GridLayoutManager(this, span, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);

        init();
        sqLiteHelper = new SQLiteHelper(this, "HomeDb.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Home(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, id_folder INTEGER, image BLOB, isfolder INTEGER,out_number INTEGER  UNIQUE,send INTEGER,timeDelay VARCHAR)");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddGroupFolder.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtName.getText().toString().trim().equals("")) {
                    String message = "لطفا نام دسته بندی را وارد کنید";
                    Toast.makeText(AddGroupFolder.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    sqLiteHelper.insertData(
                            edtName.getText().toString().trim(),
                            imageViewToPath(imageView),
                            1,
                            "",
                            0, 0, "");
                    Toast.makeText(getApplicationContext(), "با موفقیت  افزوده شد", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroupFolder.this, FolderList.class);
                startActivity(intent);
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
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        edtName = findViewById(R.id.edtName);
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        imageView = findViewById(R.id.imageViewF);
    }


}