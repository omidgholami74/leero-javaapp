package com.example.omid.omidbms.structures;

public class Items {

    private String DayOff;
    private String DayOn;
    private String LightOff;
    private String lightON;
    private String id;
    private String name_multi;
    private String name;
    private String name_folder;
    private String image;
    private String isFolder;
    private String out_number;
    private String timeDelay;
    private String MultiOutput;


    public String getMultiOutput() {
        return MultiOutput;
    }

    public void setMultiOutput(String multiOutput) {
        MultiOutput = multiOutput;
    }

    public String getName_multi() {
        return name_multi;
    }

    public void setName_multi(String name_multi) {
        this.name_multi = name_multi;
    }

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

    public String getName_folder() {
        return name_folder;
    }

    public void setName_folder(String name_folder) {
        this.name_folder = name_folder;
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

    public String getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(String timeDelay) {
        this.timeDelay = timeDelay;
    }
}
