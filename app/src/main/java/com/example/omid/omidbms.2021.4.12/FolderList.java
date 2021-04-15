package com.example.omid.omidbms;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omid.omidbms.Retrofit.ApiClient;
import com.example.omid.omidbms.Retrofit.ApiInterface;
import com.example.omid.omidbms.Socket.SocketConnection;
import com.example.omid.omidbms.adapters.FoodListAdapter;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Group;
import com.example.omid.omidbms.structures.Items;
import com.example.omid.omidbms.structures.myResposeBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class FolderList extends AppCompatActivity {
    SQLiteHelper controller;
    static GridView gridView;
    static ArrayList<Group> list;
    static FoodListAdapter adapter = null;
    public static SQLiteHelper sqLiteHelper;
    ProgressDialog prgoDialog;
    static SharedPreferences sp;
    SharedPreferences user;
    SharedPreferences FIRSTRUN;
    static SharedPreferences pref;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper db;
    public static SharedPreferences mulsh;
    String token;

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("افزودن دسته بندی").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(FolderList.this, AddGroupFolder.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("همگام سازی با سرور").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                synceMysq();
                return false;

            }
        });
        menu.add("دریافت داده ها از سرور").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                getItems(sp.getString("username", null), FolderList.this);
                return false;

            }
        });
        menu.add("حذف داده ها").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                db = new SQLiteHelper(FolderList.this, "HomeDb.sqlite", null, 1);
                db.deleteDatabase();
                sp = getSharedPreferences("UserProfile", MODE_PRIVATE);
                String test = sp.getString("username", null);
                getDataFromDatabase(FolderList.this);
                return false;

            }
        });
        menu.add("خروج از اکانت").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                db = new SQLiteHelper(FolderList.this, "HomeDb.sqlite", null, 1);
                db.deleteDatabase();
                getQuite();
                return false;

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list_activity);
        gridView = findViewById(R.id.gridView);
        controller = new SQLiteHelper(this);
        sp = getSharedPreferences("UserProfile", MODE_PRIVATE);
        token = sp.getString("token", null);
        getDataFromDatabase(FolderList.this);
        mulsh = getSharedPreferences("multi", MODE_PRIVATE);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                Intent intent = new Intent(FolderList.this, HomeItem.class);
                try {
                    pref = getSharedPreferences("mypref", MODE_PRIVATE);
                    SocketConnection.connect(pref.getString("server", "192.168.1.16"), pref.getInt("port", 8080));
                } catch (Exception e) {

                }

                intent.putExtra("folder_name", list.get(position).getName());
                Log.i("zza", list.get(position).getId() + "");
                startActivity(intent);

            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(FolderList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
//

                            showDialogDelete(list.get(position).getId(), position);

                        }
                    }
                });

                dialog.show();
                return true;
            }
        });
    }

    public static void getDataFromDatabase(Context context) {

        sqLiteHelper = new SQLiteHelper(context, "HomeDb.sqlite", null, 1);


        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS Home(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, name_folder VARCHAR, image VARCHAR, isfolder INTEGER,out_number INTEGER,send INTEGER,timeDelay VARCHAR)");


        list = new ArrayList<>();
        // get all data from sqlite
        Cursor cursor = FolderList.sqLiteHelper.getData("SELECT * FROM Home WHERE isfolder==1");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            String image = cursor.getString(3);
            list.add(new Group(name, image, id));
            cursor.moveToNext();
        }
        adapter = new FoodListAdapter(context, R.layout.food_item, list);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    ImageView imageViewFood;


    private void showDialogDelete(final int idFood, final int index) {

        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(FolderList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                list.remove(index);
                adapter.notifyDataSetChanged();
            }

        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void synceMysq() {
        ArrayList listitem = controller.getAllUsers();
        Log.i("yuj", listitem.toString());

        final ProgressDialog prgDialog = new ProgressDialog(FolderList.this);
        prgDialog.setMessage("در حال ارسال اطلاعات به سرور");
        prgDialog.show();


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<myResposeBody> call = apiInterface.sendItems(listitem);
        call.enqueue(new Callback<myResposeBody>() {
            @Override
            public void onResponse(Call<myResposeBody> call, retrofit2.Response<myResposeBody> response) {
                Log.i("oyouo", "jhgjhgjg" + response.toString());
                prgDialog.dismiss();
            }

            @Override
            public void onFailure(Call<myResposeBody> call, Throwable t) {
                Log.i("oyouo", "jg" + t.getMessage());
                Log.i("ghjkl", t.getMessage());
                prgDialog.dismiss();
            }
        });

    }

    public void getItems(String username, Context context) {

        List<Items> ListItems = new ArrayList<>();
        String url = "https://leero.iran.liara.run/allitems";


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {

            postData.put("username", sp.getString("username", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.getString("items"));
                    SQLiteHelper sql = new SQLiteHelper(context);
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Items item = new Items();
                        jsonObject = jsonArray.getJSONObject(i);
                        try {
                            item.setName(jsonObject.getString("name"));
                            item.setImage(jsonObject.getString("image"));
                            item.setIsFolder(jsonObject.getString("isFolder").trim());
                            item.setName_folder(jsonObject.getString("name_folder"));
                            item.setOut_number(jsonObject.getString("out_number").trim());
                            item.setTimeDelay(jsonObject.getString("timeDelay"));
                        } catch (Exception E) {
                            Log.i("imagf", "dghgfh :" + E.getMessage());

                        }
//                        try {
//                            item.setName_multi(jsonObject.getString("nameMulti"));
//                            item.setMultiOutput(jsonObject.getString("outputs"));
//                        } catch (Exception E) {
//                            Log.i("AFRE", E.getMessage());
//                        }
                        ListItems.add(item);
                    }
                    DlAsynctask asynctask = new DlAsynctask(FolderList.this, ListItems);
                    asynctask.execute();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Log.i("responseLog3", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getQuite() {
        SharedPreferences FIRST = getSharedPreferences("FirstRun", MODE_PRIVATE);
        sp = getSharedPreferences("UserProfile", MODE_PRIVATE);
        sp.edit().clear().commit();
        FIRST.edit().clear().commit();
        Intent intent = new Intent(FolderList.this, login.class);
        startActivity(intent);


    }
}



