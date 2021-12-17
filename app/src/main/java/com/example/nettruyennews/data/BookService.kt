package com.example.nettruyennews.data

import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.DescriptionBook

interface BookService {

    suspend fun home(url: String): List<Book>

    suspend fun description(book: Book): DescriptionBook

    suspend fun detail(currentChapter: Int, descriptionBook: DescriptionBook): List<String>
}