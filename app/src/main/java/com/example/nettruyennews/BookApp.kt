package com.example.nettruyennews

import android.app.Application
import android.provider.Settings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @Volatile
        private var instance: BookApp? = null

        public fun getInstance(): BookApp {
            if (instance == null) {
                instance = BookApp()
            }

            return instance!!
        }
    }

    val UUID_DEVICE: String by lazy {
        Settings.Secure.getString(instance?.contentResolver, Settings.Secure.ANDROID_ID)
    }
}