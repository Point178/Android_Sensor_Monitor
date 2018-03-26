# Android_Sensor_Monitor
Get android pin according to sensor value   
update in 2018/03/26  
    
- - - 
### Content:
  * Android Application  
  * Android Servlet  
  * Database  
  * Classifier (Tensorflow - CNN)  
- - -
  
### [Android_Sensor_Monitor](https://github.com/Point178/Android_Sensor_Monitor/tree/master/SensorMonitor)
An Android application that can listen to mobile sensor's value.It records user's input and sensor value and send data to server for storing in mysql database.  
  
The sensor that can be choose:  
- Accelerometer  
- Gyroscope  
- Magnetomer  
- Light  
- Rotation_vector  
- Proximity  
- Pressure  
  
User guide:  
1. User can choose different sensors that need to be listen to in `SettingFragment`  
2. In `RecordFragment`, User press `start` to start listening.At this time, user can press any number in keyboard, the number and the press down time, up time, duration will be recorded.When listening, if sensor's value changes, it will be recorded, too.After recording, user can press `stop` to end listening.All data will be sent to server immediately.  
3. In `ResultFragment`, user could have check the data from database.But because of the limit of sqlite, all data will not store in local sqlite anymore, it will show empty now.  
  
- - -  
## [Android Servlet](https://github.com/Point178/Android_Sensor_Monitor/tree/master/AndroidServlet)
This servlet will be released in the server.  
It contains only one [servlet](https://github.com/Point178/Android_Sensor_Monitor/blob/master/AndroidServlet/src/com/point178/PostDataServlet.java), it receives the data from android application and store data in `sensorMonitor mysql database` in server.  
- - -  
## Database
 * record_times  
 uuid int<11> PRI NULL auto_increment  
 startTime mediumText NULL  
 stopTime mediumText NULL  
 content varchar<100> NULL  
  
 * number_press  
 uuid          int<11>                NULL  
 number        int<11>                    NULL  
 downTime    mediumText                   NULL  
 upTime      mediumText                   NULL  
 duration    mediumText                   NULL  
   
 * [sensor_name]_value  
 time        mediumText                   NULL  
 x             double                     NULL  
 y             double                     NULL                (optional according to sensor attributes)  
 z             double                     NULL            (optional according to sensor attributes)  
  
- - -  
## Classifier (Tensorflow - CNN)  
working on it now(smile)  
