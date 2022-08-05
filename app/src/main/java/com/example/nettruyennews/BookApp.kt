package com.example.nettruyennews

import android.app.Application
import android.provider.Settings
import dagger.hilt.android.HiltAndroidApp
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor

@HiltAndroidApp
class BookApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        flutterEngineCache = FlutterEngineCache.getInstance()
        engines = FlutterEngineGroup(this)
        loadEngineCache()
    }


    private fun loadEngineCache() {
        ///fragment one
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())

//        val dartEntrypoint =
//            DartExecutor.DartEntrypoint(
//                FlutterInjector.instance().flutterLoader().findAppBundlePath(), "")
//
//        val flutterEngine2: FlutterEngine = engines.createAndRunEngine(this, dartEntrypoint)

        ///fragment two
        flutterEngineCache.put("fragment", flutterEngine)
//        flutterEngineCache.put("fragment2", flutterEngine2)
    }

    companion object {
        @Volatile
        private var instance: BookApp? = null
        lateinit var flutterEngineCache: FlutterEngineCache
        lateinit var engines: FlutterEngineGroup

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