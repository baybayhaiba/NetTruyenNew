package com.example.nettruyennews.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.nettruyennews.ui.layout.ItemNavigationChapter
import com.example.nettruyennews.util.show

object ContextBinding {

    @JvmStatic
    @BindingAdapter("toastView")
    fun bindToast(view: View, text: String?) {
        text?.let { view.context.show(it) }
    }


    @JvmStatic
    @BindingAdapter("android:isVisible")
    fun setIsVisible(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("set_chapter_content")
    fun bindChapter(view: View, content: String?) {
        if (view !is ItemNavigationChapter) return
        content?.let { view.setContent(it) }

    }
}