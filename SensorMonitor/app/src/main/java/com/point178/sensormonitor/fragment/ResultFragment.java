package com.point178.sensormonitor.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.point178.sensormonitor.R;
import com.point178.sensormonitor.database.SensorBaseHelper;
import com.point178.sensormonitor.database.SensorDBSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 昕点陈 on 2018/2/6.
 */
public class ResultFragment extends Fragment {
    private SQLiteDatabase mDatabase;
    private RecyclerView mRecordTimeRecyclerView;
    private RecordTimeAdapter mRecordTimeAdapter;
    private RecyclerView mNumberPressRecyclerView;
    private NumberPressAdapter mNumberPressAdapter;
    private RecyclerView mSensorValueRecyclerView;
    private SensorValueAdapter mSensorValueAdapter;

    private RadioButton mAccelerometerButton;
    private RadioButton mGyroscopeButton;
    private RadioButton mMagnetometerButton;
    private RadioButton mAmbientLightButton;
    private RadioButton mRotationVectorButton;
    private RadioButton mProximityButton;
    private RadioButton mBarometerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.result_fragment, container, false);
        mDatabase = new SensorBaseHelper((getContext().getApplicationContext())).getWritableDatabase();

        mAccelerometerButton = (RadioButton) v.findViewById(R.id.accelerometerButton);
        mGyroscopeButton = (RadioButton) v.findViewById(R.id.gyroscopeButton);
        mMagnetometerButton = (RadioButton) v.findViewById(R.id.magnetometerButton);
        mAmbientLightButton = (RadioButton) v.findViewById(R.id.ambientlightButton);
        mRotationVectorButton = (RadioButton) v.findViewById(R.id.rotationvectorButton);
        mProximityButton = (RadioButton) v.findViewById(R.id.proximityButton);
        mBarometerButton = (RadioButton) v.findViewById(R.id.barometerButton);
        mAccelerometerButton.setChecked(true); // set default check

        // set recycler view
        mRecordTimeRecyclerView = (RecyclerView) v.findViewById(R.id.record_time_recyclerview);
        mRecordTimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNumberPressRecyclerView = (RecyclerView) v.findViewById(R.id.number_press_recyclerview);
        mNumberPressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSensorValueRecyclerView = (RecyclerView) v.findViewById(R.id.sensorvalue_recyclerview);
        mSensorValueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set display list and adapter to recycler view
        List<RecordTime> recordTimes = getRecordTimeList();
        mRecordTimeAdapter = new RecordTimeAdapter(recordTimes);
        mRecordTimeRecyclerView.setAdapter(mRecordTimeAdapter);

        List<NumberPress> numberPresses = getNumberPressList();
        mNumberPressAdapter = new NumberPressAdapter(numberPresses);
        mNumberPressRecyclerView.setAdapter(mNumberPressAdapter);

        List<SensorValue> sensorValues = getSensorValues(SensorDBSchema.AccelerometerValueTable.NAME, SensorDBSchema.AccelerometerValueTable.NUMBER);
        mSensorValueAdapter = new SensorValueAdapter(sensorValues);
        mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);

        //radio button check listener
        mAccelerometerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mAccelerometerButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.AccelerometerValueTable.NAME, SensorDBSchema.AccelerometerValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });

        mGyroscopeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mGyroscopeButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.GyroscopeValueTable.NAME, SensorDBSchema.GyroscopeValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });

        mMagnetometerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mMagnetometerButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.MagnetometerValueTable.NAME, SensorDBSchema.MagnetometerValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });

        mAmbientLightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mAmbientLightButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.AmbientLightValueTable.NAME, SensorDBSchema.AmbientLightValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });

        mRotationVectorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mRotationVectorButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.RotationVectorValueTable.NAME, SensorDBSchema.RotationVectorValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });

        mProximityButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mProximityButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.ProximityValueTable.NAME, SensorDBSchema.ProximityValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });

        mBarometerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uncheck(mBarometerButton);
                    List<SensorValue> sensorValues = new ArrayList<SensorValue>();
                    sensorValues = getSensorValues(SensorDBSchema.PressureValueTable.NAME, SensorDBSchema.PressureValueTable.NUMBER);
                    mSensorValueAdapter = new SensorValueAdapter(sensorValues);
                    mSensorValueRecyclerView.setAdapter(mSensorValueAdapter);
                }
            }
        });
        return v;
    }

    /**
     * @Description: search record time from database
     * @Param: []
     * @return: List<RecordTime>
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public List<RecordTime> getRecordTimeList() {
        String sql = "select * from " + SensorDBSchema.RecordTimeTable.NAME;
        List<RecordTime> list = new ArrayList<RecordTime>();
        RecordTime recordTime;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            recordTime = new RecordTime(cursor.getString(cursor.getColumnIndex(SensorDBSchema.RecordTimeTable.Cols.UUID)),
                    cursor.getString(cursor.getColumnIndex(SensorDBSchema.RecordTimeTable.Cols.START)),
                    cursor.getString(cursor.getColumnIndex(SensorDBSchema.RecordTimeTable.Cols.STOP)));
            list.add(recordTime);
        }
        cursor.close();
        return list;
    }

    /**
     * @Description: search number press record from database
     * @Param: []
     * @return: List<NumberPress>
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public List<NumberPress> getNumberPressList() {
        String sql = "select * from " + SensorDBSchema.NumberPressTable.NAME;
        List<NumberPress> list = new ArrayList<>();
        NumberPress numberPress;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            numberPress = new NumberPress(cursor.getString(cursor.getColumnIndex(SensorDBSchema.NumberPressTable.Cols.TIMEID)),
                    cursor.getString(cursor.getColumnIndex(SensorDBSchema.NumberPressTable.Cols.NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SensorDBSchema.NumberPressTable.Cols.DOWN_TIME)),
                    cursor.getString(cursor.getColumnIndex(SensorDBSchema.NumberPressTable.Cols.UP_TIME)),
                    cursor.getString(cursor.getColumnIndex(SensorDBSchema.NumberPressTable.Cols.DURATION)));
            list.add(numberPress);
        }
        cursor.close();
        return list;
    }

    /**
     * @Description: search sensor value from database
     * @Param: [name, value]
     * @return: List<SensorValue>
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    public List<SensorValue> getSensorValues(String name, int value) {
        List<SensorValue> list = new ArrayList<>();
        SensorValue sensorValue;
        String sql = "select * from " + name;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        //3 value, fill "" if value.length less than 3
        switch (value) {
            case 1:
                while (cursor.moveToNext()) {
                    sensorValue = new SensorValue(cursor.getString(cursor.getColumnIndex("time")),
                            cursor.getString(cursor.getColumnIndex("x")), "", "");
                    list.add(sensorValue);
                }
                break;
            case 2:
                while (cursor.moveToNext()) {
                    sensorValue = new SensorValue(cursor.getString(cursor.getColumnIndex("time")),
                            cursor.getString(cursor.getColumnIndex("x")),
                            cursor.getString(cursor.getColumnIndex("y")), "");
                    list.add(sensorValue);
                }
                break;
            case 3:
                while (cursor.moveToNext()) {
                    sensorValue = new SensorValue(cursor.getString(cursor.getColumnIndex("time")),
                            cursor.getString(cursor.getColumnIndex("x")),
                            cursor.getString(cursor.getColumnIndex("y")),
                            cursor.getString(cursor.getColumnIndex("z")));
                    list.add(sensorValue);
                }
                break;
        }
        cursor.close();
        return list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    private class NumberPressHolder extends RecyclerView.ViewHolder {
        private TextView mNumberPressTimeID;
        private TextView mNumberPressNumber;
        private TextView mNumberPressDown;
        private TextView mNumberPressUp;
        private TextView mNumberPressDuration;
        private NumberPress mNumberPress;

        public NumberPressHolder(View itemView) {
            super(itemView);
            mNumberPressTimeID = (TextView) itemView.findViewById(R.id.number_press_timeid);
            mNumberPressNumber = (TextView) itemView.findViewById(R.id.number_press_number);
            mNumberPressDown = (TextView) itemView.findViewById(R.id.number_press_downtime);
            mNumberPressUp = (TextView) itemView.findViewById(R.id.number_press_uptime);
            mNumberPressDuration = (TextView) itemView.findViewById(R.id.number_press_duration);
        }

        public void bindNumberPress(NumberPress numberPress) {
            mNumberPress = numberPress;
            mNumberPressTimeID.setText(mNumberPress.getTimeID());
            mNumberPressNumber.setText(mNumberPress.getNumber());
            mNumberPressDown.setText(mNumberPress.getDown());
            mNumberPressUp.setText(mNumberPress.getUp());
            mNumberPressDuration.setText(mNumberPress.getDuration());
        }
    }

    private class RecordTimeHolder extends RecyclerView.ViewHolder {
        private TextView mRecordTimeUUID;
        private TextView mRecordTimeStart;
        private TextView mRecordTimeStop;
        private RecordTime mRecordTime;

        public RecordTimeHolder(View itemView) {
            super(itemView);
            mRecordTimeUUID = (TextView) itemView.findViewById(R.id.record_time_uuid);
            mRecordTimeStart = (TextView) itemView.findViewById(R.id.record_time_start);
            mRecordTimeStop = (TextView) itemView.findViewById(R.id.record_time_stop);
        }

        public void bindRecordTime(RecordTime recordTime) {
            mRecordTime = recordTime;
            mRecordTimeUUID.setText(mRecordTime.getUUID());
            mRecordTimeStart.setText(mRecordTime.getStart());
            mRecordTimeStop.setText(mRecordTime.getStop());
        }
    }

    private class SensorValueHolder extends RecyclerView.ViewHolder {
        private TextView mSensorValueTime;
        private TextView mSensorValueX;
        private TextView mSensorValueY;
        private TextView mSensorValueZ;
        private SensorValue mSensorValue;

        public SensorValueHolder(View itemView) {
            super(itemView);
            mSensorValueTime = (TextView) itemView.findViewById(R.id.sensorvalue_time);
            mSensorValueX = (TextView) itemView.findViewById(R.id.sensorvalue_x);
            mSensorValueY = (TextView) itemView.findViewById(R.id.sensorvalue_y);
            mSensorValueZ = (TextView) itemView.findViewById(R.id.sensorvalue_z);
        }

        public void bindSensorValue(SensorValue sensorValue) {
            mSensorValue = sensorValue;
            mSensorValueTime.setText(mSensorValue.getTime());
            mSensorValueX.setText(mSensorValue.getX());
            mSensorValueY.setText(mSensorValue.getY());
            mSensorValueZ.setText(mSensorValue.getZ());
        }
    }

    private class NumberPressAdapter extends RecyclerView.Adapter<NumberPressHolder> {
        private List<NumberPress> mNumberPresses;

        public NumberPressAdapter(List<NumberPress> mNumberPresses) {
            this.mNumberPresses = mNumberPresses;
        }

        @Override
        public NumberPressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_numberpress, parent, false);
            return new NumberPressHolder(view);
        }

        @Override
        public void onBindViewHolder(NumberPressHolder holder, int position) {
            NumberPress numberPress = mNumberPresses.get(position);
            holder.bindNumberPress(numberPress);
        }

        @Override
        public int getItemCount() {
            return mNumberPresses.size();
        }
    }

    private class RecordTimeAdapter extends RecyclerView.Adapter<RecordTimeHolder> {
        private List<RecordTime> mRecordTimes;

        public RecordTimeAdapter(List<RecordTime> mRecordTime) {
            mRecordTimes = mRecordTime;
        }

        @Override
        public RecordTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_recordtime, parent, false);
            return new RecordTimeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecordTimeHolder holder, int position) {
            RecordTime recordTime = mRecordTimes.get(position);
            holder.bindRecordTime(recordTime);
        }

        @Override
        public int getItemCount() {
            return mRecordTimes.size();
        }
    }

    private class SensorValueAdapter extends RecyclerView.Adapter<SensorValueHolder> {
        private List<SensorValue> mSensorValues;

        public SensorValueAdapter(List<SensorValue> mSensorValues) {
            this.mSensorValues = mSensorValues;
        }

        @Override
        public SensorValueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_sensorvalue, parent, false);
            return new SensorValueHolder(view);
        }

        @Override
        public void onBindViewHolder(SensorValueHolder holder, int position) {
            SensorValue sensorValue = mSensorValues.get(position);
            holder.bindSensorValue(sensorValue);
        }

        @Override
        public int getItemCount() {
            return mSensorValues.size();
        }
    }

    private class NumberPress {
        private String timeID;
        private String number;
        private String down;
        private String up;
        private String duration;

        NumberPress(String timeID, String number, String down, String up, String duration) {
            this.timeID = timeID;
            this.number = number;
            this.down = down;
            this.up = up;
            this.duration = duration;
        }

        public String getTimeID() {
            return this.timeID;
        }

        public String getNumber() {
            return this.number;
        }

        public String getDown() {
            return this.down;
        }

        public String getUp() {
            return this.up;
        }

        public String getDuration() {
            return this.duration;
        }
    }

    private class RecordTime {
        private String UUID;
        private String start;
        private String stop;

        RecordTime(String uuid, String start, String stop) {
            this.UUID = uuid;
            this.start = start;
            this.stop = stop;
        }

        public String getUUID() {
            return this.UUID;
        }

        public String getStart() {
            return this.start;
        }

        public String getStop() {
            return this.stop;
        }
    }

    private class SensorValue {
        private String time;
        private String X;
        private String Y;
        private String Z;

        SensorValue(String time, String X, String Y, String Z) {
            this.time = time;
            this.X = X;
            this.Y = Y;
            this.Z = Z;
        }

        public String getTime() {
            return this.time;
        }

        public String getX() {
            return this.X;
        }

        public String getY() {
            return this.Y;
        }

        public String getZ() {
            return this.Z;
        }
    }

    /**
     * @Description: single choice for buttons
     * @Param: [mButton]
     * @return: void
     * @Author: 昕点陈
     * @Date: 2018/2/11
     */
    private void uncheck(RadioButton mButton) {
        mAccelerometerButton.setChecked(false);
        mGyroscopeButton.setChecked(false);
        mMagnetometerButton.setChecked(false);
        mAmbientLightButton.setChecked(false);
        mRotationVectorButton.setChecked(false);
        mProximityButton.setChecked(false);
        mBarometerButton.setChecked(false);
        mButton.setChecked(true);
    }
}
