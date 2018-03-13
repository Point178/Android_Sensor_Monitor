package com.point178.sensormonitor.database;

/**
 * Created by 昕点陈 on 2018/2/8.
 */
public class SensorDBSchema {
    public static final class RecordTimeTable {
        public static final String NAME = "record_times";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String START = "start";
            public static final String STOP = "stop";
        }
    }

    public static final class AccelerometerValueTable {
        public static final String NAME = "accelerometer_value";
        public static final int NUMBER = 3;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
            public static final String Y = "y";
            public static final String Z = "z";
        }
    }

    public static final class GyroscopeValueTable {
        public static final String NAME = "gyroscope_value";
        public static final int NUMBER = 3;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
            public static final String Y = "y";
            public static final String Z = "z";
        }
    }

    public static final class MagnetometerValueTable {
        public static final String NAME = "magnetometer_value";
        public static final int NUMBER = 3;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
            public static final String Y = "y";
            public static final String Z = "z";
        }
    }

    public static final class AmbientLightValueTable {
        public static final String NAME = "ambient_light_value";
        public static final int NUMBER = 1;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
        }
    }

    public static final class RotationVectorValueTable {
        public static final String NAME = "rotation_vector_value";
        public static final int NUMBER = 3;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
            public static final String Y = "y";
            public static final String Z = "z";
        }
    }

    public static final class ProximityValueTable {
        public static final String NAME = "proximity_value";
        public static final int NUMBER = 1;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
        }
    }

    public static final class PressureValueTable {
        public static final String NAME = "pressure_value";
        public static final int NUMBER = 1;

        public static final class Cols {
            public static final String TIME = "time";
            public static final String X = "x";
        }
    }

    public static final class NumberPressTable {
        public static final String NAME = "number_press";

        public static final class Cols {
            public static final String TIMEID = "time_id";
            public static final String NUMBER = "number";
            public static final String DOWN_TIME = "down_time";
            public static final String UP_TIME = "up_time";
            public static final String DURATION = "duration";
        }
    }
}