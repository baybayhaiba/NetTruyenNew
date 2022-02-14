package com.example.nettruyennews.repository.detail

import com.example.nettruyennews.data.remote.BookService
import com.example.nettruyennews.data.remote.ChapterDao
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.model.room.ChapterRoom

class DetailRepositoryImpl(
    private val bookService: BookService,
    private val chapterDao: ChapterDao
) : DetailRepository {
    override suspend fun detail(descriptionBook: DescriptionBook, currentIndex: Int) =
        bookService.detail(descriptionBook = descriptionBook, currentChapter = currentIndex)

    override suspend fun saveChapter(root: String, chapter: Chapter) =
        chapterDao.saveChapter(ChapterRoom(root, chapter))
}