package com.example.nettruyennews.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.nettruyennews.model.Book
import java.util.*

@Entity(tableName = BookRoom.TABLE_NAME)
class BookRoom constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val timeAgo: Long = Date().time,
    title: String,
    link: String,
    image: String,
    var categories: MutableList<Int> = mutableListOf()
) : Book(title, link, image) {

    constructor(book: Book) : this(
        title = book.title,
        link = book.link,
        image = book.image,
    )

    companion object {
        const val TABLE_NAME = "table_book"
        const val FAVORITE = 1
        const val READED = 2
    }
}
