package com.example.nettruyennews.di

import android.app.Application
import com.example.nettruyennews.data.database.BookDatabase
import com.example.nettruyennews.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class PersistentModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) =
        BookDatabase.getInstance(application.applicationContext)

    @Singleton
    @Provides
    fun provideBookDao(bookDatabase: BookDatabase) = bookDatabase.bookDao

    @Singleton
    @Provides
    fun provideChapterDao(bookDatabase: BookDatabase) = bookDatabase.chapterDao
}