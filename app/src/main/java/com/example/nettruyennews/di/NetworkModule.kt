package com.example.nettruyennews.di

import com.example.kotlinmvvm_7.network.HttpRequestInterceptor
import com.example.nettruyennews.data.JsoupNetTruyen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideJsoup() = JsoupNetTruyen.getInstance()



}