package com.example.nettruyennews.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nettruyennews.data.BookService
import com.example.nettruyennews.model.Book

class BookPagingSource(private val bookService: BookService, val url: String) :
    PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val page = params.key ?: BookService.DEFAULT_PAGE_INDEX

        return try {
            val response = bookService.home(url = url, page = page)

            LoadResult.Page(
                response, prevKey = if (page == BookService.DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (page >= 5) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return 1
    }

}