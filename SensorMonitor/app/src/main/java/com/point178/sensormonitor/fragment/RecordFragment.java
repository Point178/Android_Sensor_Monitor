package com.point178.sensormonitor.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.point178.sensormonitor.view.KeyboardView;
import com.point178.sensormonitor.activity.MainActivity;
import com.point178.sensormonitor.R;
import com.point178.sensormonitor.attribute.SensorAttr;
import com.point178.sensormonitor.database.SensorBaseHelper;
import com.point178.sensormonitor.database.SensorDBSchema;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * Created by 昕点陈 on 2018/2/6.
 */
public class RecordFragment extends Fragment implements KeyboardView.OnNumberClickListener {
    private Button mStart;
    private Button mStop;
    private TextView mTextView;
    private KeyboardView mKeyboardView;

    private boolean isStart;
    private SensorAttr sensor;
    private SQLiteDatabase mDatabase;
    private Handler mHandler;
    private String uuidString;
    private long startTime;
    private long stopTime;
    private SensorManager mSensorManager;
    private SensorEventListener listener;

    public RecordFragment() {
        isStart = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @Description: initialize record fragment and set sensor Attribute value
     * @Param: [sensor]
     * @return: RecordFragment
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public static RecordFragment newInstance(SensorAttr sensor) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putSerializable("sensor", sensor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.record_fragment, container, false);
        if (getArguments() != null) {
            this.sensor = (SensorAttr) getArguments().getSerializable("sensor");
        }

        mDatabase = new SensorBaseHelper((getContext().getApplicationContext())).getWritableDatabase();
        mStart = (Button) v.findViewById(R.id.start_record);
        mStop = (Button) v.findViewById(R.id.stop_record);
        mTextView = (TextView) v.findViewById(R.id.text);
        mKeyboardView = (KeyboardView) v.findViewById(R.id.keyboard);

        //initialize
        mStart.setClickable(true);
        mStop.setClickable(false);
        mTextView.setText("");
        mKeyboardView.setOnNumberClickListener(this);
        new SubThread().start();

        //监听传感器的值
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                long time = System.currentTimeMillis();
                ContentValues values = new ContentValues();
                values.put("time", time);
                int number = 0;
                String tableName = "";

                //check sensor type
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        number = SensorDBSchema.AccelerometerValueTable.NUMBER;
                        tableName = SensorDBSchema.AccelerometerValueTable.NAME;
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        number = SensorDBSchema.GyroscopeValueTable.NUMBER;
                        tableName = SensorDBSchema.GyroscopeValueTable.NAME;
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        number = SensorDBSchema.MagnetometerValueTable.NUMBER;
                        tableName = SensorDBSchema.MagnetometerValueTable.NAME;
                        break;
                    case Sensor.TYPE_LIGHT:
                        number = SensorDBSchema.AmbientLightValueTable.NUMBER;
                        tableName = SensorDBSchema.AmbientLightValueTable.NAME;
                        break;
                    case Sensor.TYPE_ROTATION_VECTOR:
                        number = SensorDBSchema.RotationVectorValueTable.NUMBER;
                        tableName = SensorDBSchema.RotationVectorValueTable.NAME;
                        break;
                    case Sensor.TYPE_PROXIMITY:
                        number = SensorDBSchema.ProximityValueTable.NUMBER;
                        tableName = SensorDBSchema.ProximityValueTable.NAME;
                        break;
                    case Sensor.TYPE_PRESSURE:
                        number = SensorDBSchema.PressureValueTable.NUMBER;
                        tableName = SensorDBSchema.PressureValueTable.NAME;
                        break;
                }

                //write in database
                /*if (number == 1) {
                    values.put("x", event.values[0]);
                } else if (number == 3) {
                    values.put("x", event.values[0]);
                    values.put("y", event.values[1]);
                    values.put("z", event.values[2]);
                }
                mDatabase.insert(tableName, null, values);*/
                //write to server
                Message sensorMsg = new Message();
                sensorMsg.what = 0;
                try {
                    JSONObject sensorvalue = new JSONObject();
                    sensorvalue.put("information", "sensorvalue");
                    sensorvalue.put("type", tableName);
                    sensorvalue.put("x", event.values[0]);
                    if (event.values.length == 3) {
                        sensorvalue.put("y", event.values[1]);
                        sensorvalue.put("z", event.values[2]);
                    } else {
                        sensorvalue.put("y", null);
                        sensorvalue.put("z", null);
                    }
                    sensorvalue.put("time", time);
                    sensorMsg.obj = sensorvalue.toString();
                    mHandler.sendMessage(sensorMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        mStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sensor = ((MainActivity) getActivity()).getSensor();
                ((MainActivity) getActivity()).setStart(true);
                isStart = true;
                mTextView.setText("");
                mStart.setClickable(false);
                mStop.setClickable(true);

                //record startTime
                startTime = System.currentTimeMillis();
                //insertRecordTime();
                //getUUIDstring();

                //write to server
                try {
                    JSONObject startRecord = new JSONObject();
                    startRecord.put("information", "startRecord");
                    startRecord.put("startTime", startTime);
                    Message startRecordMsg = new Message();
                    startRecordMsg.what = 2;
                    startRecordMsg.obj = startRecord.toString();
                    mHandler.sendMessage(startRecordMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //start listen sensor
                sensorListen();
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //stop listen sensor
                sensorCancel();

                //record stoptime
                stopTime = System.currentTimeMillis();
                //updateRecordTime();

                //write to server
                try {
                    JSONObject stopRecord = new JSONObject();
                    stopRecord.put("information", "stopRecord");
                    stopRecord.put("startTime", startTime);
                    stopRecord.put("stopTime", stopTime);
                    stopRecord.put("content", mTextView.getText());
                    Message stopRecordMsg = new Message();
                    stopRecordMsg.what = 3;
                    stopRecordMsg.obj = stopRecord.toString();
                    mHandler.sendMessage(stopRecordMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //set default
                isStart = false;
                ((MainActivity) getActivity()).setStart(false);
                mStart.setClickable(true);
                mStop.setClickable(false);
                startTime = stopTime = 0;
                uuidString = null;
            }
        });

        return v;
    }

    /**
     * @Description: get record time ContentValues
     * @Param: []
     * @return: ContentValues
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private ContentValues getRecordTimeContentValues() {
        ContentValues values = new ContentValues();
        if (uuidString != null) {
            values.put(SensorDBSchema.RecordTimeTable.Cols.UUID, uuidString);
            values.put(SensorDBSchema.RecordTimeTable.Cols.STOP, stopTime);
        }
        values.put(SensorDBSchema.RecordTimeTable.Cols.START, startTime);
        return values;
    }

    /**
     * @Description: record startTime and uuid
     * @Param: []
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private void insertRecordTime() {
        ContentValues values = getRecordTimeContentValues();
        mDatabase.insert(SensorDBSchema.RecordTimeTable.NAME, null, values);
    }

    /**
     * @Description: record stop time
     * @Param: []
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private void updateRecordTime() {
        ContentValues values = getRecordTimeContentValues();
        mDatabase.update(SensorDBSchema.RecordTimeTable.NAME, values, SensorDBSchema.RecordTimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    /**
     * @Description: get uuid and set to uuidString
     * @Param: []
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private void getUUIDstring() {
        Cursor cursor = mDatabase.query(
                SensorDBSchema.RecordTimeTable.NAME,
                null,
                SensorDBSchema.RecordTimeTable.Cols.START + "=" + startTime,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            uuidString = cursor.getString(cursor.getColumnIndex(SensorDBSchema.RecordTimeTable.Cols.UUID));
        }
        cursor.close();
    }

    /**
     * @Description: get number press content values
     * @Param: [number, downTime, upTime]
     * @return: ContentValues
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private ContentValues getNumberPressContentValues(String number, long downTime, long upTime) {
        ContentValues values = new ContentValues();
        values.put(SensorDBSchema.NumberPressTable.Cols.TIMEID, uuidString);
        values.put(SensorDBSchema.NumberPressTable.Cols.NUMBER, number);
        values.put(SensorDBSchema.NumberPressTable.Cols.DOWN_TIME, downTime);
        values.put(SensorDBSchema.NumberPressTable.Cols.UP_TIME, upTime);
        values.put(SensorDBSchema.NumberPressTable.Cols.DURATION, upTime - downTime);
        return values;
    }

    /**
     * @Description: record number/down time/up time into database when user press number
     * @Param: [number, downTime, upTime]
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private void addNumberPress(String number, long downTime, long upTime) {
        ContentValues values = getNumberPressContentValues(number, downTime, upTime);
        mDatabase.insert(SensorDBSchema.NumberPressTable.NAME, null, values);
    }

    /**
     * @Description: get user input from KeyboardView, change TextView's display and record user's input into database
     * @Param: [number, downTime, upTime]
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    @Override
    public void onNumberReturn(String number, long downTime, long upTime) {
        if (isStart) {
            mTextView.setText(mTextView.getText() + number);
            //addNumberPress(number, downTime, upTime);

            //write to server
            try {
                JSONObject numberpress = new JSONObject();
                numberpress.put("information", "numberPress");
                numberpress.put("number", number);
                numberpress.put("startTime", startTime);
                numberpress.put("downTime", downTime);
                numberpress.put("upTime", upTime);
                numberpress.put("duration", upTime - downTime);
                Message numberPressMsg = new Message();
                numberPressMsg.what = 1;
                numberPressMsg.obj = numberpress.toString();
                mHandler.sendMessage(numberPressMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: unregister listen when stop listening sensor
     * @Param: []
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public void sensorCancel() {
        mSensorManager.unregisterListener(listener);
    }

    /**
     * @Description: register sensor listener according to user's choice when start recording
     * @Param: []
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public void sensorListen() {
        if (sensor.getAccelerometer()) {
            Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (null != accelerometerSensor) {
                mSensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        if (sensor.getGyroscope()) {
            Sensor gyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if (null != gyroscopeSensor) {
                mSensorManager.registerListener(listener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        if (sensor.getMagnetometer()) {
            Sensor magnetometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            if (null != magnetometerSensor) {
                mSensorManager.registerListener(listener, magnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        if (sensor.getAmbientLight()) {
            Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (null != lightSensor) {
                mSensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        if (sensor.getRotationVector()) {
            Sensor rotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            if (null != rotationVectorSensor) {
                mSensorManager.registerListener(listener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        if (sensor.getProximity()) {
            Sensor proximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            if (null != proximitySensor) {
                mSensorManager.registerListener(listener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        if (sensor.getBarometer()) {
            Sensor pressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            if (null != pressureSensor) {
                mSensorManager.registerListener(listener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    class SubThread extends Thread {
        public void run() {
            Looper.prepare();
            try {
                String BASEURL = "http://198.181.43.116:8080/AndroidServlet/PostData";
                final URL url = new URL(BASEURL);
                mHandler = new Handler(){
                    public void handleMessage(Message msg){
                        try {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoInput(true);
                            connection.setDoOutput(true);

                            connection.setRequestProperty("Content-Type", "application/json");
                            connection.setRequestProperty("Charset", "UTF-8");
                            connection.connect();

                            DataOutputStream outputStream =new DataOutputStream(connection.getOutputStream());
                            outputStream.writeBytes(URLEncoder.encode(msg.obj.toString(), "utf-8"));
                            outputStream.flush();
                            outputStream.close();

                            if(connection.getResponseCode() == 200){
                                connection.disconnect();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
            }
            Looper.loop();
        }
    }
}