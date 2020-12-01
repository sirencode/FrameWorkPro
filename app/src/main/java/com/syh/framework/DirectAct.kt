package com.syh.framework

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.syh.framework.util.BaseTimerActivity
import com.syh.framework.util.net.NetworkMonitorManager
import com.syh.framework.util.net.enums.NetworkState
import com.syh.framework.util.net.interfaces.NetworkMonitor
import kotlinx.android.synthetic.main.activity_direction.*

/**
 * Created by shenyonghe on 2020/4/9.
 */
class DirectAct : BaseTimerActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkMonitorManager.getInstance().register(this)
        setContentView(R.layout.activity_direction)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }

    override fun onResume() {
        super.onResume()
        sensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL,
                    SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
            tv_X.text = event.values[0].toString()
            tv_Y.text = event.values[1].toString()
            tv_Z.text = event.values[2].toString()
        }
    }

    @NetworkMonitor
    fun netWorkStateChange(networkState: NetworkState) {
        Log.i("onNetWorkStateChange", "onNetWorkStateChange  networkState = $networkState")
        when (networkState) {
            NetworkState.NONE -> {
                Toast.makeText(applicationContext, "暂无网络", Toast.LENGTH_SHORT).show()
            }
            NetworkState.WIFI -> {
                Toast.makeText(applicationContext, "WIFI网络", Toast.LENGTH_SHORT).show()
            }
            NetworkState.CELLULAR -> {
                Toast.makeText(applicationContext, "蜂窝网络", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkMonitorManager.getInstance().unregister(this)
    }

    override fun onTimerSecond() {
        println(this.javaClass.simpleName + ":" + System.currentTimeMillis())
    }

}