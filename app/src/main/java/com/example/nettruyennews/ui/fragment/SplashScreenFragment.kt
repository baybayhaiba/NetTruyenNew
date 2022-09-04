package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nettruyennews.R
import com.example.nettruyennews.extension.getUrlNettruyen
import com.example.nettruyennews.util.Constant
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Firebase.getUrlNettruyen { url ->
            lifecycleScope.launch {

                Constant.URL_ORIGINAL = url ?: Constant.URL_ORIGINAL_CUSTOM

                delay(2000)
                val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }


}