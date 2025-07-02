package com.vin.mycarrentalapp.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Intent
import com.vin.mycarrentalapp.R
import com.vin.mycarrentalapp.communication.CommunicationManager
import com.vin.mycarrentalapp.service.SpeedCheckService

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted){
            startSpeedChecking()
        }else{
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assume hardcoded user for this example
        configureCustomer("customer1")

        val startButton = findViewById<Button>(R.id.btn_start)
        startButton.setOnClickListener {
         checkLocationPermission()
        }
    }


    /**
     *  Method to check location permission
     */
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            startSpeedChecking()
        }
    }

    /**
     *   Method to start the speed check service
     */
    private fun startSpeedChecking(){
        val speedCheckServiceIntent = Intent(this,SpeedCheckService::class.java)
        startService(speedCheckServiceIntent)
    }

    /**
     * Method to configure speed for a customer - hardcoded for testing
     */
    fun configureCustomer(customerId: String) {
        SpeedCheckService.maxSpeedLimit = 90.0
        CommunicationManager.currentChannel = CommunicationManager.Channel.AWS
    }
}