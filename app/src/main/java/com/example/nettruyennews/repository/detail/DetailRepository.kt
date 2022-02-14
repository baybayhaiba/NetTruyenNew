package com.example.nettruyennews.repository.detail

import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.DescriptionBook

interface DetailRepository {
    suspend fun detail(descriptionBook: DescriptionBook, currentIndex: Int): List<String>
    suspend fun saveChapter(root: String, chapter: Chapter): Long
}