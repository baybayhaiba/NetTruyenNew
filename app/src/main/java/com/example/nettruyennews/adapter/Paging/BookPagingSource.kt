package com.example.nettruyennews.adapter.Paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nettruyennews.data.BookService
import com.example.nettruyennews.model.Book
import java.io.IOException

class BookPagingSource(private val bookService: BookService, val url: String) :
    PagingSource<Int, Book>() {

    private val DEFAULT_PAGE_INDEX = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val page = params.key ?: DEFAULT_PAGE_INDEX

        return try {
            val response = bookService.home(url = url, page = page)

            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }


}