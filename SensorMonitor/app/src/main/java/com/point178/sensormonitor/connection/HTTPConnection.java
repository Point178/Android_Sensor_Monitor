package com.point178.sensormonitor.connection;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Point_178 on 2018/3/20.
 */

public class HTTPConnection {
    public static String BASE_URL = "http://localhost:8080/AndroidServlet/";

    public static void sendSensorValue(String type, long time, float[] value) {
        try {
            JSONObject sensorvalue = new JSONObject();
            sensorvalue.put("type", type);
            sensorvalue.put("time", time);
            sensorvalue.put("x", value[0]);
            if(value.length == 3){
                sensorvalue.put("y", value[1]);
                sensorvalue.put("z", value[2]);
            }else{
                sensorvalue.put("y", null);
                sensorvalue.put("z", null);
            }
            String sendJson = sensorvalue.toString();

            String path = "SensorValue";
            URL url = new URL(BASE_URL+path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",  "application/json");
            connection.connect();

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(sendJson);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
