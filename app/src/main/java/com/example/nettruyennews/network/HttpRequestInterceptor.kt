package com.example.nettruyennews.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = request.newBuilder().url(request.url).build()

        Log.d("network", "intercept: $response")

        return chain.proceed(response)
    }

}