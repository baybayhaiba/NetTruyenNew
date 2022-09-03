package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nettruyennews.BookApp
import com.example.nettruyennews.R
import com.orhanobut.logger.Logger
import io.flutter.embedding.android.FlutterFragment
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FlutterFragment : Fragment() {


    companion object {
        const val fragment = "flutter_fragment"
    }

    private val channel by lazy {
        MethodChannel(
            BookApp.flutterEngineCache.get(BookApp.fragmentFlutterMain)?.dartExecutor!!,
            BookApp.method_channel
        )
    }


    private fun setupMethodChannel() {
        channel.setMethodCallHandler { call, result ->

            Logger.d("banh mi su kem => ${call.method}")

            if (call.method == "exit") {
                findNavController().navigateUp()
            }
            result.notImplemented()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMethodChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.findViewById<FragmentContainerView>(R.id.frame_container)

        val flutterFragment: FlutterFragment =
            FlutterFragment.withCachedEngine(BookApp.fragmentFlutterMain).build()




        fragmentManager
            ?.beginTransaction()
            ?.add(R.id.frame_container, flutterFragment, fragment)
            ?.commit()


        val randomScreen = listOf("red", "green", "blue")
        val randomTheme = listOf("dark", "light")

        lifecycleScope.launch {

            channel.invokeMethod(
                BookApp.protocol_parameters, """
            {"initRouter" : "${randomScreen.random()}",
            "functionName" : "hasagi",
            "jsonObject" : "hacker lo~"}
        """.trimIndent()
            )

            channel.invokeMethod(
                BookApp.protocol_config, """
            {"languages" : "VietNam" , "theme" : "${randomTheme.random()}"}
        """.trimIndent()
            )
        }



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    flutterFragment.onBackPressed()
                }
            })

        return inflater.inflate(R.layout.fragment_flutter, container, false)
    }

}