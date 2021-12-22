package com.example.nettruyennews.extension

import android.net.Uri
import androidx.core.net.toUri
import java.io.File

fun String.toUriExtension(): Uri {
    return File(this).toUri()
}