package com.example.omid.omidbms.Socket;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;

import com.example.omid.omidbms.App.a;
import com.example.omid.omidbms.HomeItem;
import com.example.omid.omidbms.TestInterface;
import com.example.omid.omidbms.adapters.AdapterHomeItems;
import com.example.omid.omidbms.adapters.AdapterTemp;
import com.example.omid.omidbms.db.SQLiteHelper;
import com.example.omid.omidbms.structures.Home;
import com.example.omid.omidbms.structures.temp_model;
import com.example.omid.omidbms.temp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnection {
    private static TestInterface testInterface;
    List<temp_model> list_temp = new ArrayList<>();
    public static Socket socket;
    private static InputStream inputStream;
    private static PrintWriter pw;
    Activity context;
    temp tp;
    static String tempreture;

    public SocketConnection(Activity context, TestInterface test) {
        this.context = context;
        testInterface = test;
    }

    public SocketConnection(Activity context) {
        this.context = context;
    }


    public static void connect(final String address, final int port) {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress inetAddress = InetAddress.getByName(address);

                    socket = new Socket(inetAddress, port);
                    inputStream = socket.getInputStream();
                    pw = new PrintWriter(socket.getOutputStream());
                    Reader gh = new InputStreamReader(inputStream);
                    BufferedReader readerout = new BufferedReader(gh);
                    Log.i("zamani", "omid");
                    String read;

                    while ((read = readerout.readLine()) != null) {
                        System.out.print(read);
                        Log.i("zamani", read);
                        if (!read.equals("")) {
                            String num = testTrim(read);
                            Log.i("qqqq", "testtrim:" + num);
                            getItem(Integer.parseInt(num), read);
                            Log.i("temp", "hgfhgffg" + read);
                            //testInterface.setReciverMatn(read);
                            getTemp(read);

                        }
                    }

                    socket.close();

                } catch (UnknownHostException e) {
                    Log.e("SocketConnection", e.getMessage());
                } catch (IOException e) {

                    Log.e("SocketConnectionIO", e.getMessage());
                }
            }
        });

    }


    public static void getTemp(String t) {
        Log.i("mohammad",t);
        int position;
        int start_code = t.indexOf("&");
        int end_code = t.indexOf("@");
        int start_temp = t.indexOf("+");
        int end_temp = t.length();
        String code = "";

        String dama = "";
        try {
            code = t.substring(start_code + 1, end_code);
            dama = t.substring(start_temp + 1, end_temp);
        } catch (Exception e) {

        }

        Log.i("aliiii", code);
        Log.i("mohammad",code+":" +dama);

        Log.i("temp", "list : " + temp.list.size());
        for (position = 0; position < temp.list.size(); position++) {
            Log.i("temp", "fbgn" + temp.list.get(position).getOut());
            if (temp.list.get(position).getOut().equals(code)) {
                testInterface.setViewInter(position,dama,code);

            }
        }
    }


    public static int getItem(int numout, String read) {
        int position;
        for (position = 0; position < HomeItem.list.size(); position++) {
            Log.i("dastork", "" + position);
            if (HomeItem.list.get(position).getPort() != numout) {
                continue;
            } else {
                try {
                    if (read.contains("ON")) {
                        HomeItem.list.get(position).setOn(true);

                    } else {
                        HomeItem.list.get(position).setOn(false);
                        Log.i("omidoff", "" + HomeItem.list.get(position));
                    }
                } catch (Exception e) {

                }


                HomeItem.Instance().iniRecyclerview();
            }
        }
        return 0;
    }

    public static void sendMessage(final String message) {
//        connect(Menu.sharedPreferences.getString("server", "80.40.247.193"), Menu.sharedPreferences.getInt("port", 8080));
        final String msg = message + "\r";
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.i("omidfg", msg);
                try {
                    pw.write(msg);
                    pw.flush();
                } catch (Exception E) {
                }

                Looper.loop();
            }
        });
    }

    public static void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
                pw.close();
            } catch (IOException ignored) {

            }
        }
    }

    static String s = "-1";

    public static String testTrim(String str) {
        try {
            int startIndex = str.indexOf("*");
            int endIndex = str.indexOf("#");

            if (str.contains("*") && str.contains("#")) {
                s = str.substring(startIndex + 1, endIndex);
                Log.i("trimTest", s);
            } else {
            }

        } catch (Exception E) {

        }


        return s;
    }

    public interface recived {
        void onrecive(String test);
    }


}