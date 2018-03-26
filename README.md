# Android_Sensor_Monitor
Get android pin according to sensor value
<br>
<br>
update in 2018/03/26<br>
<br>
* Content:<br>
  * Android Application<br>
  * Android Servlet<br>
  * Database<br>
  * Classifier (Tensorflow - CNN)<br>
<br>
## [Android_Sensor_Monitor](https://github.com/Point178/Android_Sensor_Monitor/tree/master/SensorMonitor)
An Android application that can listen to mobile sensor's value.It records user's input and sensor value and send data to server for storing in mysql database.<br>
<br>
The sensor that can be choose:<br>
* accelerometer</br>
* gyroscope<br>
* magnetomer<br>
* light<br>
* rotation_vector<br>
* proximity<br>
* pressure<br>
<br>
User guide:<br>
1. User can choose different sensors that need to be listen to in `SettingFragment`<br>
2. In `RecordFragment`, User press `start` to start listening.At this time, user can press any number in keyboard, the number and the press down time, up time, duration will be recorded.When listening, if sensor's value changes, it will be recorded, too.After recording, user can press `stop` to end listening.All data will be sent to server immediately.<br>
3. In `ResultFragment`, user could have check the data from database.But because of the limit of sqlite, all data will not store in local sqlite anymore, it will show empty now.<br>
<br>
## [Android Servlet](https://github.com/Point178/Android_Sensor_Monitor/tree/master/AndroidServlet)
This servlet will be released in the server.<br>
It contains only one [servlet](https://github.com/Point178/Android_Sensor_Monitor/blob/master/AndroidServlet/src/com/point178/PostDataServlet.java), it receives the data from android application and store data in sensorMonitor mysql database in server.<br>
<br>
## Database
 * record_times
 Field          Type        Null     Key    Default   Extra<br>
 uuid          int<11>       NO      PRI     NULL     auto_increment<br>
 startTime   mediumText      YES             NULL<br>
 stopTime    mediumText      YES             NULL<br>
 content     varchar<100>    YES             NULL<br>
 <br>
 * number_press
 Field          Type        Null     Key    Default   Extra<br>
 uuid          int<11>      YES              NULL<br>
 number        int<11>      YES              NULL<br>
 downTime    mediumText     YES              NULL<br>
 upTime      mediumText     YES              NULL<br>
 duration    mediumText     YES              NULL<br>
 <br>
 * sensor_name_value
 Field          Type        Null     Key    Default   Extra<br>
 time        mediumText     YES              NULL<br>
 x             double       YES              NULL<br>
 y             double       YES              NULL                (optional according to sensor attributes)<br>
 z             double       YES              NULL<br>            (optional according to sensor attributes)<br>
 <br>
 <br>
# Classifier (Tensorflow - CNN)
working on it now(smile)<br>
