package com.vin.mycarrentalapp.communication

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseNotifier {
    fun sendSpeedAlert(speed: Double) {
        val db = Firebase.firestore
        val alert = hashMapOf(
            "speed" to speed,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("alerts").add(alert)
    }
}