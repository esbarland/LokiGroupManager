package com.example.lokigroupmanager.HardwareEvents;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeEvent implements SensorEventListener {

    public interface OnShakeListener {
        void onShake();
    }

    private OnShakeListener listener;

    /***
     * Start the ShakeEvent
     * @param listener define the behaviour when a shake is detected
     */
    public void setOnShakeListener(OnShakeListener listener) {
        this.listener = listener;
    }

    /***
     * Stop the ShakeEvent
     */
    public void removeOnShakeListener() { sm.unregisterListener(this); }

    // Boolean to check if it is the first update of the values
    boolean firstUpdate = true;
    // Boolean to check if there is a shake
    boolean shake = false;

    // Minimum shake strength to detect the shake
    private static float SHAKE_THRESHOLD = 4f; // A TESTER SUR D'AUTRES TELEPHONES
    // Time limiter between every sensor update
    private static final long SHAKE_UPDATE_LIMITER = 1000l;
    // Last update time
    private long lastUpdate = System.currentTimeMillis();

    private float x, y, z;
    private float last_x, last_y, last_z;

    SensorManager sm;
    Sensor accelerometer;

    /***
     * ShakeEvent constructor
     * Build and register the sensor
     * @param sm SensorManager from the activity to initialize the sensor
     */
    public ShakeEvent(SensorManager sm){
        this.sm = sm;
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /***
     * Event that is called when a changed is detected on the sensor
     * @param event event type called by the sensor
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        if(curTime - lastUpdate > SHAKE_UPDATE_LIMITER) {
            updateValues(event.values[0], event.values[1], event.values[2]);
            if ((!shake) && isAccelerationChanged()) {
                shake = true;
            } else if ((shake) && isAccelerationChanged()) {
                listener.onShake();
            } else /*if ((shake) && !isAccelerationChanged())*/ {
                shake = false;
            }
            lastUpdate = curTime;
        }
    }

    /***
     * Verify if the acceleration has changed by comparing previous values and current values
     * @return true or false
     */
    private boolean isAccelerationChanged() {
        float dX = Math.abs(last_x - x);
        float dY = Math.abs(last_y - y);
        float dZ = Math.abs(last_z - z);
        return  (dX > SHAKE_THRESHOLD && dY > SHAKE_THRESHOLD) ||
                (dX > SHAKE_THRESHOLD && dZ > SHAKE_THRESHOLD) ||
                (dY > SHAKE_THRESHOLD && dZ > SHAKE_THRESHOLD);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /***
     * Update the values
     * @param xNew new X value detected
     * @param yNew new Y value detected
     * @param zNew new Z value detected
     */
    public void updateValues(float xNew, float yNew, float zNew){
        if(firstUpdate){
            last_x = xNew;
            last_y = yNew;
            last_z = zNew;
            firstUpdate = false;
        }
        else {
            last_x = x;
            last_y = y;
            last_z = z;
        }
        x = xNew;
        y = yNew;
        z = zNew;
    }
}
