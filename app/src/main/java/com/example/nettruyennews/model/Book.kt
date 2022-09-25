package com.example.nettruyennews.model

import android.os.Parcelable
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.util.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
open class Book(
    val title: String,
    val link: String,
    val image: String,
) : Parcelable {
    override fun toString(): String {
        return "Book(title='$title', link='$link', image='$image')"
    }
}

//extension
fun Book.room(): BookRoom = BookRoom(
    title = title,
    image = image,
    link = link.replace(Constant.URL_ORIGINAL, "")
)

fun Book.linkBookRoom() = link.replace(Constant.URL_ORIGINAL, "")