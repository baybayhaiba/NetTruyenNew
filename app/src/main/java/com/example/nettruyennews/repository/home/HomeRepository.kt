package com.example.nettruyennews.repository.home

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nettruyennews.model.Book
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun home(url: String, page: Int): List<Book>
    suspend fun category(): List<Pair<String, String>>
    suspend fun ranking(): List<Pair<String, String>>
    fun pageConfig(): PagingConfig
    fun pageFlow(url: String): Flow<PagingData<Book>>
}