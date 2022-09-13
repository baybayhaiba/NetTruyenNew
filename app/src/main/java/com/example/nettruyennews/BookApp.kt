package com.example.nettruyennews

import android.app.Application
import android.content.Context
import android.provider.Settings
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor


@HiltAndroidApp
class BookApp : Application() {
    val sharePreferences: DataStore<Preferences> by preferencesDataStore(name = app)


    override fun onCreate() {
        super.onCreate()
        instance = this
        flutterEngineCache = FlutterEngineCache.getInstance()
        engines = FlutterEngineGroup(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        loadEngineCache()
    }


    private fun loadEngineCache() {
        val flutterEngine = FlutterEngine(this).apply {
            dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        }

        flutterEngineCache.put(fragmentFlutterMain, flutterEngine)

//        val dartEntrypoint =
//            DartExecutor.DartEntrypoint(
//                FlutterInjector.instance().flutterLoader().findAppBundlePath(), "")
//
//        val flutterEngine2: FlutterEngine = engines.createAndRunEngine(this, dartEntrypoint)
//
//        flutterEngineCache.put(fragmentFlutterMain, flutterEngine2)
    }

    companion object {
        @Volatile
        private var instance: BookApp? = null
        lateinit var flutterEngineCache: FlutterEngineCache
        lateinit var engines: FlutterEngineGroup

        const val app = "bookApp"
        const val fragmentFlutterMain = "flutter_main"
        const val method_channel = "flutter/MethodChannelDemo"
        const val protocol_parameters = "flutter_navigation"
        const val protocol_config = "flutter_config"



        fun getInstance(): BookApp = instance ?: synchronized(this) {
            instance = BookApp()
            instance!!
        }
    }

    val uuid: String by lazy {
        Settings.Secure.getString(instance?.contentResolver, Settings.Secure.ANDROID_ID)
    }
}