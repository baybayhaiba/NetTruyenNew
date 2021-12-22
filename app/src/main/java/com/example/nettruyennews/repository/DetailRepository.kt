package com.example.nettruyennews.repository

import com.example.nettruyennews.data.BookService
import com.example.nettruyennews.data.ChapterDao
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.model.room.ChapterRoom

class DetailRepository(
    private val bookService: BookService,
    private val chapterDao: ChapterDao
) {
    suspend fun detail(descriptionBook: DescriptionBook, currentIndex: Int) =
        bookService.detail(descriptionBook = descriptionBook, currentChapter = currentIndex)

    suspend fun saveChapter(root: String, chapter: Chapter) =
        chapterDao.saveChapter(ChapterRoom(root, chapter))
}