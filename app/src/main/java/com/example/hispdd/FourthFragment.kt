package com.example.hispdd

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FourthFragment : Fragment(), SensorEventListener {

        private lateinit var sensorManager: SensorManager
        private lateinit var lightSensor: Sensor
        private lateinit var textViewLightLevel: TextView

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                // Inflate the layout for this fragment
                val view = inflater.inflate(R.layout.fragment_fourth, container, false)

                textViewLightLevel = view.findViewById(R.id.text_view_light_level)

                sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) as Sensor

                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)

                return view
        }

        @SuppressLint("SetTextI18n")
        override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                        // Get the new light level.
                        val lightLevel = event.values[0]

                        // Display the new light level in a text view.
                        textViewLightLevel.text = "Light level: $lightLevel"
                }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // Do something when the accuracy of the sensor changes.
        }

        override fun onResume() {
                super.onResume()
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        override fun onPause() {
                super.onPause()
                sensorManager.unregisterListener(this)
        }
}
