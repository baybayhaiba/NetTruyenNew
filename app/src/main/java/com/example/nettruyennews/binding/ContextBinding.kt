package com.example.nettruyennews.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.nettruyennews.util.show

object ContextBinding {

    @JvmStatic
    @BindingAdapter("toastView")
    fun bindToast(view: View, text: String?) {
        text?.let { view.context.show(it) }
    }
}