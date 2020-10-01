package io.github.adoniasalcantara.ezgas.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val entry: Pair<String, String>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val (key, value) = entry
        val request = chain
            .request()
            .newBuilder()
            .addHeader(key, value)
            .build()

        return chain.proceed(request)
    }
}