package com.muhammhassan.reminderobat.core.api

import android.os.Build
import com.muhammhassan.reminderobat.core.datastore.DataStorePreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val dataStore: DataStorePreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        runBlocking {
            val token = dataStore.getToken().first()
            request.addHeader("platform", "android")
            request.addHeader("version", Build.VERSION.SDK_INT.toString())
            token?.let{
                request.addHeader("Authorization", it)
            }
        }
        return chain.proceed(request.build())
    }
}