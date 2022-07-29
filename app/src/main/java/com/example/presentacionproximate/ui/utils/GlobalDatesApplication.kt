package com.example.presentacionproximate.ui.utils

import android.app.Application

class GlobalDatesApplication : Application() {

    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)

    }
}