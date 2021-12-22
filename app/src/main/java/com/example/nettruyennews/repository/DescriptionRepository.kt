package com.example.nettruyennews.repository

import com.example.nettruyennews.data.BookDao
import com.example.nettruyennews.data.BookService
import com.example.nettruyennews.data.ChapterDao
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom

class DescriptionRepository(
    private val bookService: BookService,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) {
    suspend fun description(book: Book) = bookService.description(book)
    suspend fun saveBook(bookRoom: BookRoom) = bookDao.saveBook(bookRoom)
    suspend fun updateBook(bookRoom: BookRoom) = bookDao.updateBook(bookRoom)
    suspend fun deleteBook(bookRoom: BookRoom) = bookDao.deleteBook(bookRoom)

    suspend fun getBookByLink(link: String) = bookDao.getBookByLink(link)
    suspend fun getChapterByLink(link: String) = chapterDao.chapterByLink(link)

    suspend fun saveChapter(root: String, chapter: Chapter) =
        chapterDao.saveChapter(ChapterRoom(root, chapter))
}