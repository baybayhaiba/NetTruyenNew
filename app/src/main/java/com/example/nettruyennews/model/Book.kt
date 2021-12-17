package com.example.nettruyennews.model

import android.os.Parcelable
import com.example.nettruyennews.model.room.BookRoom
import kotlinx.parcelize.Parcelize

@Parcelize
open class Book(
    val title: String,
    val link: String,
    val image: String,
) : Parcelable

//extension
fun Book.room(): BookRoom {
    return BookRoom(this)
}