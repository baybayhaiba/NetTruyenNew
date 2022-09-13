package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nettruyennews.BookApp
import com.example.nettruyennews.R
import com.example.nettruyennews.extension.getUrlNettruyen
import com.example.nettruyennews.extension.url
import com.example.nettruyennews.util.Constant
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        BookApp.getInstance().sharePreferences.data.map {
            Log.d(Constant.TAG, "onCreateView: ${it[Constant.USE_FIRST]} --- ${it[Constant.TEST]}")
        }

        lifecycleScope.launch {

            val urlFuture = async(Dispatchers.IO) {
                Firebase.url()
            }



//
//            launch(Dispatchers.IO) {
//                BookApp.getInstance().sharePreferences.edit { preferences ->
//                    preferences[Constant.USE_FIRST] = true
//                    preferences.get()
//                    preferences[Constant.TEST] = "huy pham"
//                    Log.d(Constant.TAG, "onCreateView 1: ${preferences[Constant.USE_FIRST]}")
//                    delay(5000)
//
//                    preferences[Constant.USE_FIRST] = false
//                    preferences[Constant.TEST] = "huy pham 1123123"
//                    Log.d(Constant.TAG, "onCreateView 2: ${preferences[Constant.USE_FIRST]}")
//                    delay(5000)
//
//                    preferences[Constant.USE_FIRST] = true
//                    preferences[Constant.TEST] = "huy pham99999"
//                    Log.d(Constant.TAG, "onCreateView 3: ${preferences[Constant.USE_FIRST]}")
//
//                    delay(5000)
//
//                    preferences[Constant.USE_FIRST] = false
//                    preferences[Constant.TEST] = "huy comp!!"
//                    Log.d(Constant.TAG, "onCreateView 4: ${preferences[Constant.USE_FIRST]}")
//                }
//            }



            Constant.URL_ORIGINAL = urlFuture.await() ?: Constant.URL_ORIGINAL_CUSTOM


                val action =
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
                findNavController().navigate(action)
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }


}