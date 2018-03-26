package com.point178.sensormonitor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.point178.sensormonitor.database.SensorDBSchema.NumberPressTable;
import com.point178.sensormonitor.database.SensorDBSchema.RecordTimeTable;

/**
 * Created by 昕点陈 on 2018/2/8.
 */
public class SensorBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "sensorBase.db";

    public SensorBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SensorDBSchema.AccelerometerValueTable.NAME + "( " +
                SensorDBSchema.AccelerometerValueTable.Cols.TIME + ", " +
                SensorDBSchema.AccelerometerValueTable.Cols.X + ", " +
                SensorDBSchema.AccelerometerValueTable.Cols.Y + ", " +
                SensorDBSchema.AccelerometerValueTable.Cols.Z + ")");

        db.execSQL("create table " + SensorDBSchema.AmbientLightValueTable.NAME + "( " +
                SensorDBSchema.AmbientLightValueTable.Cols.TIME + ", " +
                SensorDBSchema.AmbientLightValueTable.Cols.X + ")");

        db.execSQL("create table " + SensorDBSchema.PressureValueTable.NAME + "( " +
                SensorDBSchema.PressureValueTable.Cols.TIME + ", " +
                SensorDBSchema.PressureValueTable.Cols.X + ")");

        db.execSQL("create table " + SensorDBSchema.GyroscopeValueTable.NAME + "( " +
                SensorDBSchema.GyroscopeValueTable.Cols.TIME + ", " +
                SensorDBSchema.GyroscopeValueTable.Cols.X + ", " +
                SensorDBSchema.GyroscopeValueTable.Cols.Y + ", " +
                SensorDBSchema.GyroscopeValueTable.Cols.Z + ")");

        db.execSQL("create table " + SensorDBSchema.MagnetometerValueTable.NAME + "( " +
                SensorDBSchema.MagnetometerValueTable.Cols.TIME + ", " +
                SensorDBSchema.MagnetometerValueTable.Cols.X + ", " +
                SensorDBSchema.MagnetometerValueTable.Cols.Y + ", " +
                SensorDBSchema.MagnetometerValueTable.Cols.Z + ")");

        db.execSQL("create table " + SensorDBSchema.ProximityValueTable.NAME + "( " +
                SensorDBSchema.ProximityValueTable.Cols.TIME + ", " +
                SensorDBSchema.ProximityValueTable.Cols.X + ")");

        db.execSQL("create table " + SensorDBSchema.RotationVectorValueTable.NAME + "( " +
                SensorDBSchema.RotationVectorValueTable.Cols.TIME + ", " +
                SensorDBSchema.RotationVectorValueTable.Cols.X + ", " +
                SensorDBSchema.RotationVectorValueTable.Cols.Y + ", " +
                SensorDBSchema.RotationVectorValueTable.Cols.Z + ")");

        db.execSQL("create table " + NumberPressTable.NAME + "(" +
                NumberPressTable.Cols.TIMEID + ", " +
                NumberPressTable.Cols.NUMBER + ", " +
                NumberPressTable.Cols.DOWN_TIME + ", " +
                NumberPressTable.Cols.UP_TIME + ", " +
                NumberPressTable.Cols.DURATION + ")");
        db.execSQL("create table " + RecordTimeTable.NAME + "(" +
                RecordTimeTable.Cols.UUID + " integer primary key autoincrement, " +
                RecordTimeTable.Cols.START + ", " +
                RecordTimeTable.Cols.STOP + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
