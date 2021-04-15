package com.example.omid.omidbms.Socket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.omid.omidbms.Menu;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;

public class SocketConn {
    static Socket socket;
    Context context;
    public SocketConn(Context context) {
        this.context = context;
    }



    public  void  connnn(String message,int port){


        if(Menu.sharedPreferences.getBoolean("by_sms",false)){

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Menu.sharedPreferences.getString("phoneModem",""), null, message, null, null);
        }else {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String[] potsend= Menu.sharedPreferences.getString("port", "").split("#");
                        socket = new Socket(Menu.sharedPreferences.getString("server", "78.38.40.229"), Integer.parseInt(potsend[port]));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.write(message + "\r");
                        out.close();
                        socket.close();
                    }catch (Exception e){
                        Log.i("piuy",e.getMessage());
                    }
                }
            });

        }

    }

}

