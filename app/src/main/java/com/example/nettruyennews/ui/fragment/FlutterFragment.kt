package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.example.nettruyennews.BookApp
import com.example.nettruyennews.R
import com.example.nettruyennews.util.Constant.TAG
import io.flutter.embedding.android.FlutterFragment
import io.flutter.plugin.common.MethodChannel

class FlutterFragment : Fragment() {

    var flutterNeedExit: Boolean = false;

    private fun setupMethodChannel() {
        MethodChannel(
            BookApp.flutterEngineCache.get("fragment")?.dartExecutor,
            "flutter/MethodChannelDemo"
        ).setMethodCallHandler { call, result ->


            Log.d(TAG, "setupMethodChannel: ${call.method}");
            ///route_current : "main"
            if (call.method == "exit") {
                flutterNeedExit = true
                requireActivity().onBackPressed()
            }
            result.success(null)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        view?.findViewById<FragmentContainerView>(R.id.frame_container)

        //setupMethodChannel()

        val flutterFragment: FlutterFragment =
            FlutterFragment.withCachedEngine("fragment").build()

        fragmentManager
            ?.beginTransaction()
            ?.add(R.id.frame_container, flutterFragment, "fragment")
            ?.commit()

        setupMethodChannel()


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (flutterNeedExit) {
                        findNavController().navigateUp()


                        return
                    }
                    flutterFragment.onBackPressed()
                }
            })


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flutter, container, false)
    }

}