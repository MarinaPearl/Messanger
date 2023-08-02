package ru.demchuk.messenger.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.demchuk.messenger.data.api.GetStreamsZulipApi
import ru.demchuk.messenger.data.`object`.UserHolder

object ConnectWithApi {
    private const val BASE_URL = "https://tinkoff-android-spring-2023.zulipchat.com/api/v1/"
    private val interceptor = AuthorizationInterceptor(UserHolder())
    private val interceptorLogger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(interceptorLogger)
        .build()
    private val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType))
        .build()
    val mainApi = retrofit.create(GetStreamsZulipApi::class.java)




}