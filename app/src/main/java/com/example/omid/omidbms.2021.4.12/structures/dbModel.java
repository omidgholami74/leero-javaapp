package com.example.omid.omidbms.structures;

public class dbModel {

    private String id;
    private String name;
    private  String image;
    private String isFolder;

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

    public String getId_folder() {
        return id_folder;
    }

    public void setId_folder(String id_folder) {
        this.id_folder = id_folder;
    }

    public String getOut_number() {
        return out_number;
    }

    public void setOut_number(String out_number) {
        this.out_number = out_number;
    }

    private String id_folder;
    private String out_number;

}
