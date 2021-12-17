package com.example.nettruyennews.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DescriptionBook(
    val view: String,
    val categories: List<String>,
    val author: String,
    val status: String,
    val rating: String,
    val description: String,
    val book: Book,
    val chapter: List<Chapter>
) : Parcelable