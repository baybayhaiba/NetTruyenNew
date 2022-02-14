package com.example.nettruyennews.repository.description

import com.example.nettruyennews.data.remote.BookDao
import com.example.nettruyennews.data.remote.BookService
import com.example.nettruyennews.data.remote.ChapterDao
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom

class DescriptionRepositoryImpl(
    private val bookService: BookService,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) : DescriptionRepository {
    override suspend fun description(book: Book) = bookService.description(book)
    override suspend fun saveBook(bookRoom: BookRoom) = bookDao.saveBook(bookRoom)
    override suspend fun updateBook(bookRoom: BookRoom) = bookDao.updateBook(bookRoom)
    override suspend fun deleteBook(bookRoom: BookRoom) = bookDao.deleteBook(bookRoom)

    override suspend fun getBookByLink(link: String) = bookDao.getBookByLink(link)
    override suspend fun getChapterByLink(link: String) = chapterDao.chapterByLink(link)

    override suspend fun saveChapter(root: String, chapter: Chapter) =
        chapterDao.saveChapter(ChapterRoom(root, chapter))
}