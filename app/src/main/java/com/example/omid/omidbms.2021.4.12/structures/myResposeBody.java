package com.example.omid.omidbms.structures;

import java.io.Serializable;

public class myResposeBody implements Serializable {


    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}