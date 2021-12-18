package com.example.nettruyennews

import android.app.Application
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
}