package com.example.omid.omidbms.Socket;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.omid.omidbms.TestInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.omid.omidbms.Socket.SocketConnection.getItem;
import static com.example.omid.omidbms.Socket.SocketConnection.getTemp;
import static com.example.omid.omidbms.Socket.SocketConnection.testTrim;

public class Recive implements Runnable, TestInterface {
    private ArrayList<Integer> port;
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
    public Recive(String message,TestInterface testInterface) {
        this.message=message;
        this.testInterface=  testInterface;
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
                int temp_org=0;
                for (int i = 0; i < port.length; i++) {

                        ebteda =Integer.parseInt(releh[i].split("-")[0]);
                        end  = Integer.parseInt(releh[i].split("-")[1]);
                        Log.i("shahla",ip+":"+port[i]+":"+message);
                    server = new Socket(ip,Integer.parseInt(port[i]));
                    in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    out = new PrintWriter(server.getOutputStream());
                    out.write(message + "\r");
                    out.flush();

                    while (true) {
                        if (message.equals("GOZARESH")) {
                            String num = null;
                            String serverResponse = in.readLine();
                            if (!serverResponse.equals("")) {
                                Log.i("mysocket", serverResponse);
                                testInterface.setReciverMatn(serverResponse);
                                num = testTrim(serverResponse);
                                Log.i("qqqq", "testtrim:" + num);
                                getItem(Integer.parseInt(num), serverResponse);
                                Log.i("temp", "hgfhgffg" + serverResponse);

                            }


                            if (serverResponse == null || Integer.parseInt(num)>=end || Integer.parseInt(num)<ebteda ) {
                                break;
                            }

                        } else {

                            String code=null;
                            String serverResponse = in.readLine();
                            if (!serverResponse.equals("")) {
                                Log.i("mysocket", serverResponse);
                                getTemp(serverResponse);
                                int start_code = serverResponse.indexOf("p");
                                int end_code = serverResponse.indexOf("@");

                                code = serverResponse.substring(start_code + 1, end_code);
                                Log.i("temp56", "temp::" + serverResponse);
                            }
                            if (serverResponse == null || Integer.parseInt(code) ==temp_org+Integer.parseInt(temp[i])+1) {
                                temp_org+=Integer.parseInt(temp[i]);
                                break;
                            }
                        }


                    }
                }
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
