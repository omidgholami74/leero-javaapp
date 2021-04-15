package com.example.omid.omidbms.Retrofit;

import com.example.omid.omidbms.structures.Retrofit_Array_items;
import com.example.omid.omidbms.structures.Retrofit_items;
import com.example.omid.omidbms.structures.myResposeBody;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("getItems.php")
    Call<List<Retrofit_items>> getItems(@Field("username") String username );




    @Headers("Content-Type: application/json")
    @POST("insertitems")
    Call<myResposeBody> sendItems(@Body ArrayList list);

}
