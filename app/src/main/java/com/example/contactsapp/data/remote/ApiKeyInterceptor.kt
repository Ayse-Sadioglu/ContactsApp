package com.example.contactsapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("ApiKey", apiKey)
            .addHeader("accept", "text/plain")
            .build()

        return chain.proceed(request)
    }
}

