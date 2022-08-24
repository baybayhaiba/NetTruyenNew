package com.example.nettruyennews.util.file

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.nettruyennews.BookApp
import com.example.nettruyennews.model.Book
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

    fun getImages(context: Context): MutableList<String> {
        val contentResolver = context.contentResolver
        val images = mutableListOf<String>()

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = null
        val selection = null
        val selectionArgs = null
        val sort = "${MediaStore.Images.ImageColumns.DATE_TAKEN}" //DESC LIMIT 2 OFFSET 2"

        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sort)

        cursor?.let {
            if (it.moveToFirst()) {
                //index folder -> la ten folder chua file, neu file nam trong thu muc goc thi se null
                val indexFolder =
                    it.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)

                //index path -> la duong dan den file
                val indexPath = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)

                do {
                    images.add(it.getString(indexPath))
                } while (it.moveToNext());

                cursor.close()
            }
        }

        return images;
    }
}