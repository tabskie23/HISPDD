package com.example.hispdd

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*


class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var tvTemperature: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvHeatIndex: TextView
    private lateinit var tvpHValue: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTemperature = view.findViewById(R.id.tv_temperature) // Initialize tvTemperature variable
        tvHumidity = view.findViewById(R.id.tv_humidity)
        tvHeatIndex = view.findViewById(R.id.tv_heatIndex)
        tvpHValue = view.findViewById(R.id.tv_phValue)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://hispdd-8fc1d-default-rtdb.asia-southeast1.firebasedatabase.app")
        val temperatureRef: DatabaseReference = database.getReference("temperature")
        val humidityRef: DatabaseReference = database.getReference("humidity")
        val heatIndexRef: DatabaseReference = database.getReference("heatindex")
        val pHvalueRef: DatabaseReference = database.getReference("phValue")

        val temperatureListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temperature = dataSnapshot.getValue(Float::class.java)
                if (temperature != null) {
                    Log.d(TAG, "Temperature is: $temperature")
                    tvTemperature.text = "$temperature"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }

        val humidityListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val humidity = dataSnapshot.getValue(Float::class.java)
                if (humidity != null) {
                    Log.d(TAG, "Humidity is: $humidity")
                    tvHumidity.text = "$humidity"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }

        val heatIndexListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val heatIndex = dataSnapshot.getValue(Float::class.java)
                if(heatIndex != null) {
                    Log.d(TAG, "Heat Index is: $heatIndex")
                    tvHeatIndex.text = "$heatIndex"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }

        val phValueListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val phValue = dataSnapshot.getValue(Float::class.java)
                if(phValue != null) {
                    Log.d(TAG, "pH Water level is:  $phValue")
                    tvpHValue.text = "$phValue"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }

        temperatureRef.addValueEventListener(temperatureListener)
        humidityRef.addValueEventListener(humidityListener)
        heatIndexRef.addValueEventListener(heatIndexListener)
        pHvalueRef.addValueEventListener(phValueListener)
    }
}