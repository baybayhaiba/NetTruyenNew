package com.example.nettruyennews.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.Target
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
    @BindingAdapter("imageLocal")
    fun bindingImageLocal(view: View, resource: Int) {
        if (view is ImageView) {
            view.setImageResource(resource)
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
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(Target.SIZE_ORIGINAL)
                .into(view)
        }
    }
}