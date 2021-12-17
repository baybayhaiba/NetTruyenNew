package com.example.notejetpack.utils

import android.text.format.DateUtils

fun Long.timeAgo(): String {
    return DateUtils.getRelativeTimeSpanString(this).toString()
}