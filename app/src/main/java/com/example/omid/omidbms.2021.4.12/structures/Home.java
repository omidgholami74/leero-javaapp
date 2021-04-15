package com.example.omid.omidbms.structures;

public class Home {
    private int id;
    private int port;
    private int folder_id;
    private String name;
    private String image;
    private boolean isOn=false;

    public Home(int id,String name, String image, int port) {
        this.port = port;
        this.name = name;
        this.image = image;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(int folder_id) {
        this.folder_id = folder_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}

