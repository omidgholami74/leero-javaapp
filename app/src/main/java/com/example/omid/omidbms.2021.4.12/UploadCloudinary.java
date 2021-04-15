package com.example.omid.omidbms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Items;

import java.util.List;
import java.util.Map;


public class UploadCloudinary extends AsyncTask<Void,Integer,String> {

    private final Context context;
    private final String imagePath;
    SQLiteHelper sqLiteHelper;
    ProgressDialog progressDialog;

    public UploadCloudinary(Context context,String imagePath) {

        this.context = context;
        this.imagePath = imagePath;
    }

    @Override
    protected String doInBackground(Void... voids) {

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
