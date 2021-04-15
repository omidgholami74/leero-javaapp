package com.example.omid.omidbms.Socket;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.omid.omidbms.TestInterface;

import java.io.*;
import java.net.*;

import static android.content.Context.MODE_PRIVATE;
import static com.example.omid.omidbms.Socket.SocketConnection.getItem;
import static com.example.omid.omidbms.Socket.SocketConnection.getTemp;
import static com.example.omid.omidbms.Socket.SocketConnection.testTrim;

public class Send implements Runnable, TestInterface {
    private BufferedReader in;
    private PrintWriter out;
    Socket server;
    String message;
    TestInterface testInterface;
    SharedPreferences preferences;
    Context context;
    int port_index;
    int ebteda=0;
    int end=0;
    public Send(String message, int port_index, TestInterface testInterface) {
        this.testInterface=testInterface;
        this.message = message;
        this.port_index=port_index;
        this.context= (Context) testInterface;
    }
    @Override
    public void run() {
        preferences= context.getSharedPreferences("myprefs", MODE_PRIVATE);
        String ip = preferences.getString("server", "");
        String[] port = preferences.getString("port", "").split("#");
        String[] temp =preferences.getString("numtemp", "1").split("#");
        String[] releh =preferences.getString("numreleh", "8").split("#");
        if (ip.equals("") || port[0].length() == 0) {

        } else {
            try {
                int port_asli=0;
                for (int i = 0; i < port.length; i++) {
                         ebteda =Integer.parseInt(releh[i].split("-")[0]);
                         end  = Integer.parseInt(releh[i].split("-")[1]);
                        if(ebteda<=port_index && port_index<=end){
                            port_asli=i;
                            break;
                        }
                }
                Log.i("shahla",ip+":"+port[port_asli]+":"+message);
                server = new Socket(ip,Integer.parseInt(port[port_asli]));
                in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                out = new PrintWriter(server.getOutputStream());
                out.write(message + "\r");
                out.flush();
                in.close();
                out.close();
                server.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void setViewInter(int position, String code, String dama) {

    }

    @Override
    public void setReciverMatn(String matn) {

    }
}
