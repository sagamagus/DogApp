package com.sagamagus.dogapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DogApp)
            modules(appModule)
        }
    }
}