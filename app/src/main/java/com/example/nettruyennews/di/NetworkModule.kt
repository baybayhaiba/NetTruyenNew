package com.example.nettruyennews.di

import com.example.nettruyennews.data.remote.JsoupNetTruyen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideJsoup() = JsoupNetTruyen.getInstance()



}