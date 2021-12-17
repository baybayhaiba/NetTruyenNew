package com.example.nettruyennews.di

import com.example.nettruyennews.data.ChapterDao
import com.example.nettruyennews.data.JsoupNetTruyen
import com.example.nettruyennews.data.database.BookDatabase
import com.example.nettruyennews.repository.DescriptionRepository
import com.example.nettruyennews.repository.DetailRepository
import com.example.nettruyennews.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(jsoup: JsoupNetTruyen) = HomeRepository(jsoup)

    @Provides
    @ViewModelScoped
    fun provideDescriptionRepository(jsoup: JsoupNetTruyen, database: BookDatabase) =
        DescriptionRepository(jsoup, database.bookDao, database.chapterDao)

    @Provides
    @ViewModelScoped
    fun provideDetailRepository(jsoup: JsoupNetTruyen, chapterDao: ChapterDao) =
        DetailRepository(jsoup, chapterDao)
}