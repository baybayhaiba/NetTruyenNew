package com.example.nettruyennews.repository

import com.example.nettruyennews.data.JsoupNetTruyen

data class HomeRepository(private val jsoupService: JsoupNetTruyen) {
    suspend fun home(url: String) = jsoupService.home(url)
    suspend fun category() = jsoupService.category()
    suspend fun ranking() = jsoupService.ranking()
}