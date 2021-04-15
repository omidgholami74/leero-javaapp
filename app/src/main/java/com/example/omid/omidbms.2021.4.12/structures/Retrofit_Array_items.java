package com.example.omid.omidbms.structures;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Retrofit_Array_items implements Serializable {
    @SerializedName("array")
    private ArrayList<Retrofit_items> itemsArrayList = new ArrayList<>();

    public ArrayList<Retrofit_items> getItemsArrayList() {
        return itemsArrayList;
    }

    public void setItemsArrayList(ArrayList<Retrofit_items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }
}
