package com.example.nettruyennews.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.nettruyennews.util.showLoading

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {

    abstract val mViewModel: VM
    abstract val layoutId: Int
    private var mViewBinding: VB? = null
    val binding get() = mViewBinding!!
    val loadingProgress: AlertDialog? by lazy {
        showLoading()
    }

    abstract fun observerScreen()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mViewBinding == null) {
            mViewBinding =
                DataBindingUtil.inflate(inflater, layoutId, container, false)
            observerScreen()

            Log.d("huy3351", "onCreateView: 123456 ${mViewBinding!!::class.java.name}")
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }

}