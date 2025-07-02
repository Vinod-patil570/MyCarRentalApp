package com.vin.mycarrentalapp.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import com.vin.mycarrentalapp.communication.CommunicationManager

import kotlin.math.roundToInt

class SpeedCheckService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    companion object {
        var maxSpeedLimit = 80.0  // default max speed
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create().apply {
            interval = 3000
            fastestInterval = 2000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location: Location = result.lastLocation ?: return
                val speed = location.speed * 3.6 // m/s to km/h

                if (speed > maxSpeedLimit) {
                    Toast.makeText(applicationContext, "Speed exceeded: ${speed.roundToInt()} km/h", Toast.LENGTH_SHORT).show()
                    CommunicationManager.sendAlert(speed)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun onBind(intent: Intent?): IBinder? = null
}