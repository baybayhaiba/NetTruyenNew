package com.example.nettruyennews.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nettruyennews.adapter.Paging.BookPagingSource
import com.example.nettruyennews.data.remote.BookService
import com.example.nettruyennews.model.Book
import kotlinx.coroutines.flow.Flow

data class HomeRepositoryImpl(private val bookService: BookService) : HomeRepository {
    override suspend fun home(url: String, page: Int) = bookService.home(url, page)
    override suspend fun category() = bookService.category()
    override suspend fun ranking() = bookService.ranking()

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    override fun pageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    override fun pageFlow(url: String): Flow<PagingData<Book>> {
        return Pager(
            config = pageConfig(),
            pagingSourceFactory = { BookPagingSource(bookService, url) }
        ).flow
    }



}