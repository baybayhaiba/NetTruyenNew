package com.example.nettruyennews.repository

import com.example.nettruyennews.data.ChapterDao
import com.example.nettruyennews.data.JsoupNetTruyen
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.model.room.ChapterRoom

class DetailRepository(
    private val jsoupService: JsoupNetTruyen,
    private val chapterDao: ChapterDao
) {
    suspend fun detail(descriptionBook: DescriptionBook, currentIndex: Int) =
        jsoupService.detail(descriptionBook = descriptionBook, currentChapter = currentIndex)

    suspend fun saveChapter(root: String, chapter: Chapter) =
        chapterDao.saveChapter(ChapterRoom(root, chapter))
}