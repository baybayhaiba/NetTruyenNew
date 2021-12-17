package com.example.nettruyennews.repository

import com.example.nettruyennews.data.BookDao
import com.example.nettruyennews.data.ChapterDao
import com.example.nettruyennews.data.JsoupNetTruyen
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.room
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom

class DescriptionRepository(
    private val jsoupService: JsoupNetTruyen,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) {
    suspend fun description(book: Book) = jsoupService.description(book)
    suspend fun saveBook(book: Book) = bookDao.saveBook(book.room())
    suspend fun updateBook(bookRoom: BookRoom) = bookDao.updateBook(bookRoom)
    suspend fun deleteBook(book: Book) = bookDao.deleteBook(book.room())
    suspend fun deleteBook(title: String) = bookDao.deleteBook(title)

    suspend fun getBookByLink(link: String) = bookDao.getBookByLink(link)
    suspend fun getChapterByLink(link: String) = chapterDao.chapterByLink(link)

    suspend fun saveChapter(root: String, chapter: Chapter) =
        chapterDao.saveChapter(ChapterRoom(root, chapter))
}