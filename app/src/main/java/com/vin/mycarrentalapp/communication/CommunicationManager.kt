package com.vin.mycarrentalapp.communication

object CommunicationManager {
    enum class Channel { FIREBASE, AWS }

    var currentChannel: Channel = Channel.FIREBASE

    fun sendAlert(speed: Double) {
        when (currentChannel) {
            Channel.FIREBASE -> FirebaseNotifier.sendSpeedAlert(speed)
            Channel.AWS -> AwsNotifier.sendSpeedAlert(speed)
        }
    }
}