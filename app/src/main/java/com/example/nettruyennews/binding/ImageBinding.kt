package com.example.nettruyennews.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl

object ImageBinding {

    @JvmStatic
    @BindingAdapter("imageGlide")
    fun bindImage(view: View, url: String) {
        if (view is ImageView) {
            Glide.with(view.context)
                .load("https:$url")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("imageRequest")
    fun bindingImageRequest(view: View, url: String) {
        if (view is ImageView) {
            val glideUrl = GlideUrl("https:$url") {
                mapOf(
                    "Referer" to "http://www.nettruyengo.com/"
                )
            }
            Glide.with(view.context)
                .load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
        }
    }
}