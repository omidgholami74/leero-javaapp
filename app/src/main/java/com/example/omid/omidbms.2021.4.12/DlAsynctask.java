package com.example.omid.omidbms;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Spinner;

import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Items;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class DlAsynctask extends AsyncTask<Void, Integer, Void> {

    private final Context context;
    private final List<Items> items;
    SQLiteHelper sqLiteHelper;
    ProgressDialog progressDialog;

    public DlAsynctask(Context context, List<Items> items) {

        this.context = context;
        this.items = items;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("در حال دریافت اطلاعات");
        progressDialog.setMessage("در حال دریافت دادخ ها از سرور می باشد لطفا منتظر بمانید");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... lists) {
        sqLiteHelper = new SQLiteHelper(context);
        for (int i = 0; i < items.size(); i++) {
            Items item = items.get(i);
            try {

                String nameMulti = item.getName_multi();
                String outputs = item.getMultiOutput();
                if(nameMulti != null){

                    sqLiteHelper.insertMulti(nameMulti, outputs);
                }
                String name = item.getName();

                String imageUrl = "https://res.cloudinary.com/omidgh74/image/upload/Leero/"+item.getImage().replace("leero/","");
                Bitmap bitmap = Picasso.with(context).load(imageUrl).get();
                File extStorageDir=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                String imageName =imageUrl.substring(imageUrl.lastIndexOf("/")+1,imageUrl.length());
                 File image= new File(extStorageDir,imageName);
                FileOutputStream fos = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.flush();
                fos.close();

                String isFolder = item.getIsFolder();
                String name_folder = item.getName_folder();
                String out_number = item.getOut_number();
                String timeDelay = item.getTimeDelay();

                sqLiteHelper.insertData(name, imageName, Integer.parseInt(isFolder), name_folder, Integer.parseInt(out_number), 1, timeDelay);
            } catch (Exception e) {
                Log.i("AFRE","\n"+e.getMessage());

            }
            publishProgress((i*100)/items.size());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.hide();
        FolderList.getDataFromDatabase(context);


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }


}
