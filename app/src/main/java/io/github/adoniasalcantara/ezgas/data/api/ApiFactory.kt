package io.github.adoniasalcantara.ezgas.data.api

import android.content.Context
import com.squareup.moshi.Moshi
import io.github.adoniasalcantara.ezgas.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiFactory(context: Context) {

    private val retrofit: Retrofit

    init {
        val apiKey = context.getString(R.string.api_key)
        val apiKeyInterceptor = HeaderInterceptor("apiKey" to apiKey)

        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(logging)
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .build()

        val converter = Moshi.Builder()
            .add(BigDecimalAdapter())
            .add(OffsetDateTimeAdapter())
            .build()
            .run(MoshiConverterFactory::create)

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .build()
    }

    fun stationApi(): StationApi = retrofit.create(StationApi::class.java)

    private companion object {
        const val BASE_URL = "https://ezgas-backend.herokuapp.com/"
        const val CALL_TIMEOUT = 60L // Seconds
    }
}