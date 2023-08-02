package ru.demchuk.messenger.data

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import ru.demchuk.messenger.data.model.UserHolder

class AuthorizationInterceptor(private val userHolder: UserHolder) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
            .header("Authorization", Credentials.basic(userHolder.mail, userHolder.api_key))
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}