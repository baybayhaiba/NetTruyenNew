package com.example.nettruyennews.data

import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.util.Constant

interface BookService {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }


    suspend fun home(url: String = Constant.URL, page: Int = DEFAULT_PAGE_INDEX): List<Book>

    suspend fun description(book: Book): DescriptionBook

    suspend fun detail(currentChapter: Int, descriptionBook: DescriptionBook): List<String>

    suspend fun category(url: String = Constant.URL): List<Pair<String, String>>

    suspend fun ranking(url: String = Constant.URL): List<Pair<String, String>>
}