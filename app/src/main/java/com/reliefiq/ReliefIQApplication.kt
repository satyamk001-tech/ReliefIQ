package com.reliefiq

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReliefIQApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization code here
    }
}
