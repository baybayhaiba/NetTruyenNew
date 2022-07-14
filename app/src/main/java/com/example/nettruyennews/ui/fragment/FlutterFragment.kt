package com.example.nettruyennews.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import com.example.nettruyennews.R
import io.flutter.embedding.android.FlutterFragment

class FlutterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        view?.findViewById<FragmentContainerView>(R.id.frame_container)

        //setupMethodChannel()

        val flutterFragment: FlutterFragment = FlutterFragment.withCachedEngine("fragment").build()

        fragmentManager
            ?.beginTransaction()
            ?.add(R.id.frame_container, flutterFragment, "fragment")
            ?.commit()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flutter, container, false)
    }
}