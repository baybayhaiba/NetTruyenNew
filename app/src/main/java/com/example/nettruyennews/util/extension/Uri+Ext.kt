package com.example.nettruyennews.util.extension

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.ByteArrayOutputStream


fun Uri.fileName(context: Context): String {
    var result: String? = null
    if (this.getScheme().equals("content")) {
        val cursor = context.getContentResolver().query(this, null, null, null, null)

        cursor?.let {

            try {

                if (it.moveToFirst()) {
                    result = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                it.close()
            }
        }

    }
    if (result == null) {
        result = this.getPath()
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "Unknown"
}

fun getImageUri(context: Context?, src: Bitmap?, format: CompressFormat?, quality: Int): Uri? {
    if (src == null) return null
    val os = ByteArrayOutputStream()
    src.compress(format, quality, os)
    val path = MediaStore.Images.Media.insertImage(context?.contentResolver, src, "title", null)
    return Uri.parse(path)
}

fun Uri.size(context: Context): Long {
    var cursor: Cursor? = null
    var fileSize: Long? = null
    return try {
        val proj = arrayOf(MediaStore.Audio.Media.SIZE)
        cursor = context.contentResolver.query(this, proj, null, null, null)

        cursor?.let {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            cursor.moveToFirst()
            val temp = cursor.getString(column_index)

            fileSize = temp.toLongOrNull()
        }

        fileSize ?: 0
    } finally {
        cursor?.close()
    }
}