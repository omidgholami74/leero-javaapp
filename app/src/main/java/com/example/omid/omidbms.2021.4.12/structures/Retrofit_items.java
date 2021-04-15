package com.example.omid.omidbms.structures;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Blob;

public class Retrofit_items implements Serializable {

    @SerializedName("DayOff")
    private String DayOff;
    @SerializedName("DayOn")
    private String DayOn;
    @SerializedName("LightOff")
    private String LightOff;
    @SerializedName("lightON")
    private String lightON;

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("id_folder")
    private String id_folder;
    @SerializedName("image")
    private String image;
    @SerializedName("isFolder")
    private String isFolder;
    @SerializedName("out_number")
    private String out_number;
    @SerializedName("timeDelay")
    private String timeDelay;



    public String getDayOff() {
        return DayOff;
    }
    public void setDayOff(String dayOff) {
        DayOff = dayOff;
    }
    public String getDayOn() {
        return DayOn;
    }
    public void setDayOn(String dayOn) {
        DayOn = dayOn;
    }
    public String getLightOff() {
        return LightOff;
    }
    public void setLightOff(String lightOff) {
        LightOff = lightOff;
    }

    public String getLightON() {
        return lightON;
    }

    public void setLightON(String lightON) {
        this.lightON = lightON;
    }

    public String getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(String timeDelay) {
        this.timeDelay = timeDelay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_folder() {
        return id_folder;
    }

    public void setId_folder(String id_folder) {
        this.id_folder = id_folder;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public String getOut_number() {
        return out_number;
    }

    public void setOut_number(String out_number) {
        this.out_number = out_number;
    }

    public Retrofit_items(String id, String name, String id_folder, String image, String isFolder, String out_number) {
        this.id = id;
        this.name = name;
        this.id_folder = id_folder;
        this.image = image;
        this.isFolder = isFolder;
        this.out_number = out_number;
    }

}
