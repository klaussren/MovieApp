package com.example.movieapp.di

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url();

        val url: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", "d22413a6de096b7fe139b54a0c90b97d")
            .build();

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url);

        val request: Request = requestBuilder.build();
        return chain.proceed(request);

    }
}