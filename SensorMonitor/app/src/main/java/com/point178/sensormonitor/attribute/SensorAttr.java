package com.point178.sensormonitor.attribute;

import android.widget.Switch;

import java.io.Serializable;

/**
 * Created by 昕点陈 on 2018/2/6.
 */
public class SensorAttr implements Serializable {
    private boolean accelerometer;
    private boolean gyroscope;
    private boolean magnetometer;
    private boolean ambientLight;
    private boolean rotationVector;
    private boolean proximity;
    private boolean barometer;

    /**
     * @Description: record whether record this kind of sensor or not
     * @Param: []
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public SensorAttr() {
        accelerometer = false;
        gyroscope = false;
        magnetometer = false;
        ambientLight = false;
        rotationVector = false;
        proximity = false;
        barometer = false;
    }

    public void setAccelerometer(boolean state) {
        this.accelerometer = state;
    }

    public boolean getAccelerometer() {
        return this.accelerometer;
    }

    public void setGyroscope(boolean state) {
        this.gyroscope = state;
    }

    public boolean getGyroscope() {
        return this.gyroscope;
    }

    public void setMagnetometer(boolean state) {
        this.magnetometer = state;
    }

    public boolean getMagnetometer() {
        return this.magnetometer;
    }

    public void setAmbientLight(boolean state) {
        this.ambientLight = state;
    }

    public boolean getAmbientLight() {
        return this.ambientLight;
    }

    public void setRotationVector(boolean state) {
        this.rotationVector = state;
    }

    public boolean getRotationVector() {
        return this.rotationVector;
    }

    public void setProximity(boolean state) {
        this.proximity = state;
    }

    public boolean getProximity() {
        return this.proximity;
    }

    public void setBarometer(boolean state) {
        this.barometer = state;
    }

    public boolean getBarometer() {
        return this.barometer;
    }
}