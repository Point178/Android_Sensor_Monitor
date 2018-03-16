package com.point178.sensormonitor.connection;

import android.renderscript.ScriptGroup;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Point_178 on 2018/3/16.
 */

public class SocketConnection implements Serializable {
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public SocketConnection(){
        try {
            socket = new Socket("127.0.0.1", 12345);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return socket;
    }

    public void sendSensorValue(long time, String sensorTableName, int number, float[] value){
        try {
            // make up json
            JSONObject msgJson = new JSONObject();
            msgJson.put("type", "sensor value");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("time", time);
            jsonObject.put("table", sensorTableName);
            jsonObject.put("x", value[0]);
            if(number == 3) {
                jsonObject.put("y", value[1]);
                jsonObject.put("z", value[2]);
            }else{
                jsonObject.put("y", null);
                jsonObject.put("z", null);
            }
            msgJson.put("content", jsonObject);

            // send to server
            outputStream = socket.getOutputStream();
            outputStream.write(msgJson.toString().getBytes("utf-8"));
            outputStream.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
