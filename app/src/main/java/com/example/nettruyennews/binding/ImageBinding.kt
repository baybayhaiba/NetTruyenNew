package com.example.nettruyennews.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.example.nettruyennews.R
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.util.FileUtil

object ImageBinding {

    @JvmStatic
    @BindingAdapter("imageGlide")
    fun bindImage(view: View, url: String) {
        if (view is ImageView) {
            Glide.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("imageGlide")
    fun bindImage(view: View, book: Book) {
        if (view is ImageView) {

            val path = FileUtil.getImageFromFile(url = book.title) ?: "https:${book.image}"

            bindImage(view, path)
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
                .placeholder(R.drawable.ic_baseline_preview_24)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
        }
    }
}