package com.point178.sensormonitor.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.point178.sensormonitor.R;
import com.point178.sensormonitor.attribute.SensorAttr;
import com.point178.sensormonitor.database.SensorBaseHelper;
import com.point178.sensormonitor.fragment.RecordFragment;
import com.point178.sensormonitor.fragment.ResultFragment;
import com.point178.sensormonitor.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {
    private BottomNavigationView mNavigationView;
    private SensorAttr sensor;
    private SQLiteDatabase mDatabase;
    private boolean isStart;

    /**
     * @Description: set sensor attribute(open or close)
     * @Param: [sensor]
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public void setSensor(SensorAttr sensor) {
        this.sensor = sensor;
    }

    /**
     * @Description: set isStart, can't change fragment when isStart = true
     * @Param: [isStart]
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public SensorAttr getSensor() {
        return this.sensor;
    }

    public MainActivity() {
        sensor = new SensorAttr();
        isStart = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mDatabase = new SensorBaseHelper(getApplicationContext()).getWritableDatabase();

        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //initialize fragment
        SettingFragment settingFragment = SettingFragment.newInstance(sensor);
        if (fragment == null) {
            fm.beginTransaction().add(R.id.fragment_container, settingFragment).commit();
            fm.beginTransaction().addToBackStack(null).commit();
        }

        //navigation bar choose fragment
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (isStart) {
                            Toast.makeText(MainActivity.this, "Can't switch page while recording sensor values", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        switch (item.getTitle().toString()) {
                            case "setting":
                                SettingFragment settingFragment1 = SettingFragment.newInstance(sensor);
                                fm.beginTransaction().replace(R.id.fragment_container, settingFragment1).commit();
                                break;
                            case "record":
                                RecordFragment recordFragment = RecordFragment.newInstance(sensor);
                                fm.beginTransaction().replace(R.id.fragment_container, recordFragment).commit();
                                break;
                            case "result":
                                ResultFragment resultFragment = ResultFragment.newInstance();
                                fm.beginTransaction().replace(R.id.fragment_container, resultFragment).commit();
                                break;
                        }
                        return true;
                    }
                });
    }
}