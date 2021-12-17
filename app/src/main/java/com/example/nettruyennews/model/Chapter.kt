package com.example.nettruyennews.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Chapter(open val title: String, val link: String, val timeAgo: String, val view: String) :
    Parcelable