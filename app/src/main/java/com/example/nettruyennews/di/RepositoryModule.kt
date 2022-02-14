package com.example.nettruyennews.di

import com.example.nettruyennews.data.remote.ChapterDao
import com.example.nettruyennews.data.remote.JsoupNetTruyen
import com.example.nettruyennews.data.database.BookDatabase
import com.example.nettruyennews.repository.description.DescriptionRepository
import com.example.nettruyennews.repository.description.DescriptionRepositoryImpl
import com.example.nettruyennews.repository.detail.DetailRepository
import com.example.nettruyennews.repository.detail.DetailRepositoryImpl
import com.example.nettruyennews.repository.home.HomeRepository
import com.example.nettruyennews.repository.home.HomeRepositoryImpl
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
    fun provideHomeRepository(jsoup: JsoupNetTruyen) : HomeRepository = HomeRepositoryImpl(jsoup)

    @Provides
    @ViewModelScoped
    fun provideDescriptionRepository(
        jsoup: JsoupNetTruyen,
        database: BookDatabase,
    ) : DescriptionRepository =
        DescriptionRepositoryImpl(jsoup, database.bookDao, database.chapterDao)

    @Provides
    @ViewModelScoped
    fun provideDetailRepository(jsoup: JsoupNetTruyen, chapterDao: ChapterDao) : DetailRepository =
        DetailRepositoryImpl(jsoup, chapterDao)
}