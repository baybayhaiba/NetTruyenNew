package com.example.nettruyennews.repository.description

import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.model.room.BookRoom
import com.example.nettruyennews.model.room.ChapterRoom

interface DescriptionRepository {
    suspend fun description(book: Book): DescriptionBook
    suspend fun saveBook(bookRoom: BookRoom): Long
    suspend fun updateBook(bookRoom: BookRoom): Int
    suspend fun deleteBook(bookRoom: BookRoom): Int

    suspend fun getBookByLink(link: String): List<BookRoom>
    suspend fun getChapterByLink(link: String): List<ChapterRoom>

    suspend fun saveChapter(root: String, chapter: Chapter): Long

}