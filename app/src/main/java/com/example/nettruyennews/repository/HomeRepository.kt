package com.example.nettruyennews.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.nettruyennews.adapter.BookPagingSource
import com.example.nettruyennews.data.BookService

data class HomeRepository(private val bookService: BookService) {
    suspend fun home(url: String) = bookService.home(url)
    suspend fun category() = bookService.category()
    suspend fun ranking() = bookService.ranking()

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    private fun getDefaultConfig() =
        PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)

    fun bookPagingFlow(pagingConfig: PagingConfig = getDefaultConfig(), url: String) = Pager(
        config = pagingConfig,
        pagingSourceFactory = { BookPagingSource(bookService, url) }
    ).flow
}