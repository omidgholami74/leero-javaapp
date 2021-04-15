package com.example.omid.omidbms.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.omid.omidbms.FolderList;
import com.example.omid.omidbms.ImagePickerUtil;
import com.example.omid.omidbms.login;
import com.example.omid.omidbms.structures.multi;
import com.example.omid.omidbms.structures.temp_model;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.squareup.picasso.Picasso;

import org.bson.Document;
import org.json.JSONArray;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    SharedPreferences sp;
    SQLiteDatabase database;
Context mContext;
    public static final String DATABASE_NAME = "HomeDb.sqlite";
    public static final String TABLE_NAME = "multion";
    public static final String COL_DAY_OFF = "DayOff";
    public static final String COL_DAY_ON = "DayOn";
    public static final String COL_LIGH_OFF = "nightOff";
    public static final String COL_LIGHT_ON = "nightOn";
    public static final String COL_USERNAME = "username";
    public static final String Col_ip = "IP";
    public static final String Col_port = "port";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }

    public SQLiteHelper(Context context) {
        super(context, "HomeDb.sqlite", null, 1);
        mContext = context;
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    //---------------------------------------------------------TEMPRETURE---------------------------
    public List<temp_model> getTemp(){
        List<temp_model> temp = new ArrayList<>();
        multi mul = new multi();
       database =this.getWritableDatabase();
        Cursor cursor =database.rawQuery("SELECT * FROM Tempreture",null);
        while (cursor.moveToNext()){
            temp_model muli_item = new temp_model();
            muli_item.setId(cursor.getInt(0));
            muli_item.setName(cursor.getString(1));
            muli_item.setOut(cursor.getString(2));
            muli_item.setTemp(cursor.getString(3));
            muli_item.setImg(cursor.getBlob(4));
            temp.add(muli_item);
        }
        database.close();
        cursor.close();
        return temp;
    }


    public void deleteTemp(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("Tempreture","id" + " =?",new String[]{String.valueOf(id)});
        database.close();
    }

    public void insertTemp(String name, String output, byte[] img) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("out", output);
        contentValues.put("img",img);
        database.insert("Tempreture", null, contentValues);
        database.close();
    }
    public void insertdama(String dama, String code) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dama",dama);
        database.update("Tempreture",contentValues,"out=?",new String[]{code});
        database.close();
    }

    public void editTemp(String name,String outnumber,int id){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("outNumber", outnumber);
        database.update("Tempreture",contentValues,"id" + " =?",new String[]{String.valueOf(id)});
        database.close();
    }

    public ArrayList<multi> getTemps(){
        ArrayList<multi> list = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+"Tempreture",null);
        while (cursor.moveToNext()){
            multi item =new multi();
            item.setName(cursor.getString(1));
            list.add(item);
        }
        db.close();
        cursor.close();
        return list;
    }



    //------------------------------------------MULTI-----------------------------------------------------
    public List<multi> getMulti(){
        List<multi> temp = new ArrayList<>();
        multi mul = new multi();
        database =this.getWritableDatabase();
        Cursor cursor =database.rawQuery("SELECT * FROM Multi",null);
        while (cursor.moveToNext()){
            multi muli_item = new multi();
            muli_item.setId(cursor.getInt(0));
            muli_item.setName(cursor.getString(1));
            muli_item.setOutput(cursor.getString(2));
            temp.add(muli_item);
        }
        database.close();
        cursor.close();
        return temp;
    }
public void deleteMulti(int id){
    SQLiteDatabase database = getWritableDatabase();
    database.delete("Multi","id" + " =?",new String[]{String.valueOf(id)});
    database.close();
}

    public void insertMulti(String name,String output) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("outNumber", output);
        database.insert("Multi", null, contentValues);
        database.close();
    }
    public void editMulti(String name,String outnumber,int id){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("outNumber", outnumber);
        database.update("Multi",contentValues,"id" + " =?",new String[]{String.valueOf(id)});
        database.close();
    }
    public void editItem(String name,int outnumber,String time,int id){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("out_number", outnumber);
        contentValues.put("timeDelay", time);
        database.update("Home",contentValues,"Id" + " =?",new String[]{String.valueOf(id)});
        database.close();
    }
    public void insertData(String name,String image, int isFolder, String folder_name, int out_number, int send, String timeDelay) {
        SQLiteDatabase database = getWritableDatabase();
        String sql;

        sql = "INSERT INTO Home VALUES (NULL,?, ?, ?, ?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        if (isFolder == 0) {
            statement.bindString(2, folder_name);
        }
        statement.bindString(1, name);
        statement.bindString(3, image);
        statement.bindDouble(4, isFolder);
        statement.bindDouble(5, out_number);
        statement.bindDouble(6, send);
        statement.bindString(7, timeDelay);
        statement.executeInsert();

    }



    public void updateData(String name, byte[] image, int isFolder, int id, int out_number) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE Home SET name = ?, image = ?, isFolder = ?,out_number = ? WHERE id = ? ";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindDouble(0, (double) id);
        statement.bindString(1, name);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double) isFolder);
        statement.bindDouble(5, out_number);
        statement.execute();
        database.close();
    }

    public void deleteDataitem(int id) {

        Log.i("idtest", "omid");
        SQLiteDatabase database = getWritableDatabase();
        Log.i("idtest", "omid");
        String sql = "DELETE FROM Home WHERE isFolder=0 AND Id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }

    public void deleteData(int id) {

        SQLiteDatabase database = getWritableDatabase();
        Log.i("idtest", "omid");
        String sql = "DELETE FROM Home WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }

    Map config = new HashMap();
    String imageName;
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM Home";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Cursor cursorMulti = database.rawQuery("SELECT * FROM Multi ", null);

      try{

          config.put("cloud_name", "omidgh74");
          config.put("upload_preset", "leerobms");
          config.put("api_key", "369536571436892");
          config.put("api_secret", "PXogX8GC_vO22CrOnnb2RZpWNmI");
          MediaManager.init(mContext, config);

      }catch (Exception e){

      }

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("name_folder", cursor.getString(2));
                 imageName = cursor.getString(3);
                Log.i("yuyyiu",imageName);

                String imagePath=mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+"/"+imageName;
                uploadToCloudinary(imagePath);

                map.put("image", imageName);
                map.put("isFolder", cursor.getString(4));
                map.put("out_number", cursor.getString(5));
                map.put("timeDelay", cursor.getString(7));
                sp = mContext.getSharedPreferences("UserProfile", MODE_PRIVATE);
                map.put("username", sp.getString("username", null));
                map.put("phone", sp.getString("phone", null));
                Log.i("listItem",map+"");
                wordList.add(map);
            } while (cursor.moveToNext());

        }
        while (cursorMulti.moveToNext()){
            try {
                HashMap<String, String> map1 = new HashMap<String, String>();
//                map1.put("idSqliteMulti", String.valueOf(cursorMulti.getInt(0)));
                map1.put("nameMulti",cursorMulti.getString(1));
                map1.put("outputsMulti",cursorMulti.getString(2));
                map1.put("username", sp.getString("username", null));
                wordList.add(map1);
            }catch (Exception e){
                Log.i("ttttt", "getAllUsers: "+e.getMessage());
            }
        }
        return wordList;
    }
     public void uploadToCloudinary(String imagePath){
         MediaManager.get().upload(imagePath)

                 .option("use_filename", "true")
                 .option("unique_filename", "false")
                 .option("folder", "leero")
                 .option("public_id", imageName.substring(0,imageName.length() -4) )
                 .callback(new UploadCallback() {

             @Override
             public void onStart(String requestId) {
                 Log.i("shoro", "start");
             }

             @Override
             public void onProgress(String requestId, long bytes, long totalBytes) {
                 Log.i("uploading", "Uploading… ");
             }

             @Override
             public void onSuccess(String requestId, Map resultData) {
                 Log.i("onSuccess", "image URL: " + resultData.get("public_id").toString()+"."+resultData.get("format").toString());
//                 String image=resultData.get("public_id").toString()+"."+resultData.get("format").toString();
                 Toast.makeText(mContext,"در حال آپلود عکس ها می باشد",Toast.LENGTH_LONG).show();

             }

             @Override
             public void onError(String requestId, ErrorInfo error) {
                 Log.i("error", error.getDescription());
             }

             @Override
             public void onReschedule(String requestId, ErrorInfo error) {
                 Log.i("Reshedule ", error.getDescription());
             }

         }).dispatch();

     }




    /**
     * Compose JSON out of SQLite records
     *
     * @return
     */

    public JSONArray composeJSONfromSQLite() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM Home where send = '" + "no" + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("id_folder", cursor.getString(2));
                byte[] image = cursor.getBlob(3);
                map.put("image", Base64.encodeToString(image, Base64.DEFAULT));
                map.put("isFolder", cursor.getString(4));
                map.put("out_number", cursor.getString(5));
                wordList.add(map);
            } while (cursor.moveToNext());

        }
        database.close();
        // Gson gson = new GsonBuilder().create();
        //Use GSON to serializeN Array List to JSO
        JSONArray jsonArray = new JSONArray(wordList);

        return jsonArray;
    }
    public ArrayList<multi> getItems(){
        ArrayList<multi> list = new ArrayList<>();
       SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+"Multi",null);
        while (cursor.moveToNext()){
            multi item =new multi();
            item.setName(cursor.getString(1));
            list.add(item);
        }
        db.close();
        cursor.close();
        return list;
    }
    public void setVisited(int id,int i) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("send", i);
        database.update("Home", contentValues, "Id" + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Multi "+"("+"id" +" INTEGER PRIMARY KEY AUTOINCREMENT,"+"name"+" TEXT,"+"outNumber"+" TEXT ) ");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Tempreture ( id INTEGER PRIMARY KEY AUTOINCREMENT,name  TEXT,out TEXT,dama TEXT,img BLOB ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        mContext.deleteDatabase("HomeDb.sqlite");
    }
}

