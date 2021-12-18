package com.example.nettruyennews.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nettruyennews.model.Chapter
import java.util.*

@Entity(tableName = ChapterRoom.TABLE_NAME)
class ChapterRoom(
    @PrimaryKey
    val root: String,
    var timeAgoSave: Long = Date().time,
    title: String,
    link: String,
    timeAgo: String,
    view: String
) :
    Chapter(title, link, timeAgo, view) {

    constructor(root: String, chapter: Chapter) : this(
        root = root,
        timeAgoSave = Date().time,
        title = chapter.title,
        link = chapter.link,
        timeAgo = chapter.timeAgo,
        view = chapter.view
    )

    companion object {
        const val TABLE_NAME = "table_chapter"
    }

}