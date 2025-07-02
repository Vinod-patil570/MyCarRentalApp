package com.vin.mycarrentalapp.communication

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException

object AwsNotifier {
    private val client = OkHttpClient()

    fun sendSpeedAlert(speed: Double) {
        val json = JSONObject()
        json.put("speed", speed)
        json.put("timestamp", System.currentTimeMillis())

        val request = Request.Builder()
            .url("https://your-api-gateway.amazonaws.com/your-endpoint")
            .post(RequestBody.create("application/json".toMediaType(), json.toString()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                println("Alert sent to AWS")
            }
        })
    }
}