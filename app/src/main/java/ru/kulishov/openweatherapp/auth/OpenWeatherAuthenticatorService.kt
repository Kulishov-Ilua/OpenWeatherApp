package ru.kulishov.openweatherapp.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder

class OpenWeatherAuthenticatorService : Service() {
    private lateinit var authenticator: OpenWeatherAuthenticator

    override fun onCreate() {
        authenticator = OpenWeatherAuthenticator(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        return authenticator.iBinder
    }
}
