package com.example.nettruyennews.network

import okhttp3.OkHttpClient
import okhttp3.Request


object OkHttpHtml {
    private var INSTANCE: OkHttpClient? = null

    private fun getInstance(): OkHttpClient {
        if (INSTANCE == null) {
            INSTANCE = OkHttpClient.Builder()
                .addInterceptor(HttpRequestInterceptor())
                .build()
        }

        return INSTANCE!!
    }

    fun webToHtml(url: String): String {
        val request: Request = Request.Builder()
            .url(url)
            .build()

        val html = this.getInstance()
            .newCall(request).execute().body?.string()

        return html ?: ""
    }

}