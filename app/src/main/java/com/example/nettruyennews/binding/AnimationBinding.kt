package com.example.nettruyennews.binding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import com.example.nettruyennews.util.show


object AnimationBinding {

    const val KEY_VISIBLE = 1001

    @JvmStatic
    @BindingAdapter("animatedVisibility")
    fun animationVisibility(view: View, isVisibility: Boolean) {


        view.animate()
            .translationY(view.height.toFloat())
            .alpha(if (isVisibility) 0.0f else 1.0f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)

                    view.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                    view.visibility = View.GONE

                }
            })


    }


}