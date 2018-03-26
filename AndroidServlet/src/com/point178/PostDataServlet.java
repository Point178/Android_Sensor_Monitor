package com.point178;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.*;

import net.sf.json.JSONObject;

/**
 * Created by 昕点陈 on 2018/3/25.
 */
public class PostDataServlet extends HttpServlet {
    private Connection conn;
    private Statement statement = null;

    public PostDataServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://*********/sensorMonitor";
        String user = "****";
        String passwd = "******";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, passwd);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer sb = new StringBuffer("");
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //将json字符串转换为json对象
            String acceptjson = URLDecoder.decode(sb.toString(), "utf-8");

            JSONObject json = JSONObject.fromObject(acceptjson);
            String information = json.getString("information");
            switch (information) {
                case "sensorvalue":
                    String tableName = json.getString("type");
                    PreparedStatement insertSensorValue = conn.prepareStatement(
                            "insert into "+tableName+"(`time`, x, y, z) VALUES(?, ?, ?, ?)");
                    insertSensorValue.setLong(1, json.getLong("time"));
                    insertSensorValue.setDouble(2, json.getDouble("x"));
                    if (json.getString("y") != null) {
                        insertSensorValue.setDouble(3, json.getDouble("y"));
                    }else{
                        insertSensorValue.setNull(3,Types.DOUBLE);
                    }
                    if (json.getString("z") != null) {
                        insertSensorValue.setDouble(4, json.getDouble("z"));
                    }else{
                        insertSensorValue.setNull(4, Types.DOUBLE);
                    }
                    insertSensorValue.execute();
                    break;
                case "startRecord":
                    PreparedStatement addStartRecord = conn.prepareStatement(
                            "insert into record_times(startTime, stopTime, content) VALUES (?, null, null)");
                    addStartRecord.setLong(1, json.getLong("startTime"));
                    addStartRecord.execute();
                    break;
                case "stopRecord":
                    PreparedStatement updateRecord = conn.prepareStatement(
                            "update record_times set stopTime=?, content=? where startTime=?");
                    updateRecord.setLong(1, json.getLong("stopTime"));
                    updateRecord.setString(2, json.getString("content"));
                    updateRecord.setLong(3, json.getLong("startTime"));
                    updateRecord.execute();
                    break;
                case "numberPress":
                    PreparedStatement getUUID = conn.prepareStatement(
                            "select `uuid` from `record_times` where `startTime`=?");
                    getUUID.setLong(1, Long.parseLong(json.getString("startTime")));
                    ResultSet rs = getUUID.executeQuery();
                    int uuid = -1;
                    if(rs.next()){
                        uuid = rs.getInt("uuid");
                    }
                    PreparedStatement numberPress = conn.prepareStatement(
                            "insert into `number_press`(`uuid`, `number`, `downTime`, `upTime`, `duration`) VALUES (?,?,?,?,?)");
                    numberPress.setInt(1, uuid);
                    numberPress.setInt(2, json.getInt("number"));
                    numberPress.setLong(3, json.getLong("downTime"));
                    numberPress.setLong(4, json.getLong("upTime"));
                    numberPress.setLong(5, json.getLong("duration"));
                    numberPress.execute();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Hello Servlet!");*/
        doPost(request, response);
    }
}