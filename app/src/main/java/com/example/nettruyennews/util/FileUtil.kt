package com.example.nettruyennews.util

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.nettruyennews.BookApp
import com.example.nettruyennews.data.database.BookDatabase
import com.example.nettruyennews.model.Book
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object FileUtil {

    fun saveImageToDevice(
        application: Application = BookApp.getInstance(),
        book: Book
    ) {
        try {
            // Use the compress method on the BitMap object to write image to the OutputStream
            Glide.with(application)
                .asBitmap()
                .load("https:${book.image}")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                         saveToInternalStorage(
                            bitmapImage = resource,
                            name = book.title
                        )
                    }

                    override fun onLoadCleared(placeholddder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        } catch (e: Exception) {
            Log.d("huy112312", "saveImageToDevice: ${e.message}")
        } finally {

        }
    }

    private fun saveToInternalStorage(
        application: Application = BookApp.getInstance(),
        bitmapImage: Bitmap,
        name: String
    ): String? {
        val cw = ContextWrapper(application)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, name)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    fun getImageFromFile(
        application: Application = BookApp.getInstance(),
        url: String
    ): String? {
        val path: String = application.filesDir.parentFile!!.path.plus("/app_imageDir/")
        val file = File(path, url)
        return if (file.exists()) {
            file.path
        } else {
            null
        }
    }
}