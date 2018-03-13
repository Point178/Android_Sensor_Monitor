package com.point178.sensormonitor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.point178.sensormonitor.activity.MainActivity;
import com.point178.sensormonitor.R;
import com.point178.sensormonitor.attribute.SensorAttr;

/**
 * Created by 昕点陈 on 2018/2/6.
 */
public class SettingFragment extends Fragment {
    private Switch accelerometer;
    private Switch gyroscope;
    private Switch magnetometer;
    private Switch ambientLight;
    private Switch rotationVector;
    private Switch proximity;
    private Switch barometer;
    private SensorAttr sensor;

    /**
     * @Description: initialize setting fragment and set sensorAttribute for display
     * @Param: [sensor]
     * @return: SettingFragment
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public static SettingFragment newInstance(SensorAttr sensor) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putSerializable("sensor", sensor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_fragment, container, false);
        accelerometer = (Switch) v.findViewById(R.id.accelerometer);
        gyroscope = (Switch) v.findViewById(R.id.gyroscope);
        magnetometer = (Switch) v.findViewById(R.id.magnetometer);
        ambientLight = (Switch) v.findViewById(R.id.ambientlight);
        rotationVector = (Switch) v.findViewById(R.id.rotationvector);
        proximity = (Switch) v.findViewById(R.id.proximity);
        barometer = (Switch) v.findViewById(R.id.barometer);

        if (getArguments() != null) {
            sensor = (SensorAttr) getArguments().getSerializable("sensor");
        }

        // set default display according to SensorAttribute
        accelerometer.setChecked(sensor.getAccelerometer());
        gyroscope.setChecked(sensor.getGyroscope());
        magnetometer.setChecked(sensor.getMagnetometer());
        ambientLight.setChecked(sensor.getAmbientLight());
        rotationVector.setChecked(sensor.getRotationVector());
        proximity.setChecked(sensor.getProximity());
        barometer.setChecked(sensor.getBarometer());

        //set switch change listener
        barometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setBarometer(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });

        proximity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setProximity(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });

        rotationVector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setRotationVector(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });

        ambientLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setAmbientLight(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });

        magnetometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setMagnetometer(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });

        gyroscope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setGyroscope(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });

        accelerometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sensor.setAccelerometer(isChecked);
                ((MainActivity) getActivity()).setSensor(sensor);
            }
        });
        return v;
    }
}