package com.saviour.yasharth.saviour;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.widget.Toast;

import java.lang.UnsupportedOperationException;

public class ShakeListener implements SensorEventListener
{
    private static final int FORCE_THRESHOLD =0;    //10000  Values according to  a research paper.
    private static final int TIME_THRESHOLD = 0;//75
    private static final int SHAKE_TIMEOUT = 0;//500
    private static final int SHAKE_DURATION = 0;//150
    private static final int SHAKE_COUNT = 0;   //1

    private SensorManager mSensorMgr;
    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private long mLastTime;
    private OnShakeListener mShakeListener;
    private Context mContext;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;
    private Sensor mAccelerator;

    @Override
    public void onSensorChanged(SensorEvent event) {
       //Toast.makeText(this,"Im in On Sensor Changed",Toast.LENGTH_LONG).show();

        if (event.sensor != mAccelerator) return;
        long now = System.currentTimeMillis();

      if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0;
        }

        if ((now - mLastTime) > TIME_THRESHOLD) {
            long diff = now - mLastTime;
            float speed = Math.abs(event.values[0] + event.values[1] + event.values[2] - mLastX - mLastY - mLastZ) / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now;
                    mShakeCount = 0;
                    if (mShakeListener != null) {
                        mShakeListener.onShake();
                    }
                }
                mLastForce = now;
            }
            mLastTime = now;
            mLastX = event.values[0];
            mLastY = event.values[1];
            mLastZ = event.values[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnShakeListener
    {
        public void onShake();
    }

    public ShakeListener(Context context)
    {
        mContext = context;
        resume();
    }

    public void setOnShakeListener(OnShakeListener listener)
    {
        mShakeListener = listener;
    }

    public void resume() {
        mSensorMgr = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerator=mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mSensorMgr == null) {
            throw new UnsupportedOperationException("Sensors not supported");
        }
        boolean supported = mSensorMgr.registerListener(this, mAccelerator, SensorManager.SENSOR_DELAY_GAME);
        if (!supported) {
            mSensorMgr.unregisterListener(this,mAccelerator);
            throw new UnsupportedOperationException("Accelerometer not supported");
        }
    }

    public void pause() {
        if (mSensorMgr != null) {
            mSensorMgr.unregisterListener(this, mAccelerator);
            mSensorMgr = null;
        }
    }

    public void onAccuracyChanged(int sensor, int accuracy) { }

}