@file:Suppress("DEPRECATION")

package com.codility.accelerometer

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var color = false
    var whip = 0

    override fun onAccuracyChanged(s: Sensor?, i: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //Encuentra al sensor
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private fun getAccelerometer(event: SensorEvent) {
        // Movement
        val xVal = event.values[0]
        //val yVal = event.values[1]
        //val zVal = event.values[2]
        tvXAxiz.text = "X Value: ".plus(xVal.toString())
        //tvYAxis.text = "Y Value: ".plus(yVal.toString())
        //tvZAxis.text = "Z Value: ".plus(zVal.toString())

        //val accelerationSquareRoot = (xVal * xVal + yVal * yVal + zVal * zVal) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH)

        /*if (accelerationSquareRoot >= 3) {
            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT).show()
            if (color) {
                relative.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                relative.setBackgroundColor(Color.YELLOW)
            }
            color = !color
        }*/

        if (xVal<-5 && whip == 0){
            whip++
            relative.setBackgroundColor(Color.CYAN)
        }else if (xVal>5 && whip==1){
            relative.setBackgroundColor(Color.YELLOW)
            whip++
        }else if (xVal<5 && xVal>-5){
            relative.setBackgroundColor(Color.GREEN)
        }

        if (whip == 2){
            whip = 0
        }

    }
    //Reanudar la actividad después de dejarla abierta
    //Retraso para obtener los datos
    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }
    //Desactivando sensores que no se ocupan.
    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}