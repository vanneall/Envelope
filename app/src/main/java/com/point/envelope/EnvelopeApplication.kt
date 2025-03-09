package com.point.envelope

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class EnvelopeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            Timber.tag("Uncaught exception").e(e.stackTraceToString())
        }
    }
}