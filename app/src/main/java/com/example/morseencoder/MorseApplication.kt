package com.example.morseencoder

import android.app.Application
import timber.log.Timber

// Application class to allow app-wide use of Timber logging
class MorseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}