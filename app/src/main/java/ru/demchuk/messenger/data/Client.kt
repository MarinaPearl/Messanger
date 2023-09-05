package ru.demchuk.messenger.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.demchuk.messenger.data.api.StreamsGetterZulipApi
import ru.demchuk.messenger.data.api.PeopleGetterZulipApi


object Client {
    private const val BASE_URL = ""
    private val interceptor = AuthorizationInterceptor(UserHolder())
    private val interceptorLogger =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(interceptorLogger)
        .build()
    private val contentType = "application/json".toMediaType()
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    fun getStreams(): StreamsGetterZulipApi {
        return retrofit.create(StreamsGetterZulipApi::class.java)
    }

    fun getUsers(): PeopleGetterZulipApi {
        return retrofit.create(PeopleGetterZulipApi::class.java)
    }


}