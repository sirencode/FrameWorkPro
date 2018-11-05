package com.syh.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

public class ScreenSwitchUtils {
    private static ScreenSwitchUtils mInstance;

    private static Object lock = new Object();
    private OrientationSensorListener listener;
    private SensorManager sm;
    private Sensor sensor;
    private Activity mActivity;
    private static final int ORIENTATION_SUCCESS = 1;
    private static final int angle = 60; // 角度差，和时间差共同控制了灵敏度
    private static final int lastTime = 0;
    private static final int gap = 1000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ORIENTATION_SUCCESS) {
                int orientation = msg.arg1;
                if (orientation > angle && orientation < (180 - angle) && orientationNE(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                } else if (orientation > (180 - angle) && orientation < (270 - angle) && orientationNE(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT)) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                } else if (orientation > (270 - angle) && orientation < (360 - angle) && orientationNE(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if ((orientation > (360 - angle) && orientation < 360 && orientationNE(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)) || (orientation > 0 && orientation < 45 && orientationNE(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT))) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        }
    };

    private boolean orientationNE(int orientation) {
        return mActivity.getResources().getConfiguration().orientation != orientation;
    }

    /**
     * 控制两次切换之间的时间差
     * @return
     */
    private boolean checkTime() {
        return System.currentTimeMillis() - lastTime >= gap;
    }

    public ScreenSwitchUtils(Context context) {
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener(handler);
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public static ScreenSwitchUtils newInstance(Context context) {
        if (mInstance == null) {
            synchronized (lock) {
                if (mInstance == null) {
                    mInstance = new ScreenSwitchUtils(context);
                }
            }
        }
        return mInstance;
    }


    public class OrientationSensorListener implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public static final int ORIENTATION_UNKNOWN = -1;

        private Handler rotateHandler;


        public OrientationSensorListener(Handler handler) {
            rotateHandler = handler;
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
            // TODO Auto-generated method stub

        }

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z * Z) {
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }

            if (rotateHandler != null) {
                rotateHandler.obtainMessage(ORIENTATION_SUCCESS, orientation, 0).sendToTarget();
            }

        }

    }

    /**
     * 开始监听
     */
    public void start(Activity activity) {
        mActivity = activity;
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * 停止监听
     */
    public void stop() {
        sm.unregisterListener(listener);
    }
}
