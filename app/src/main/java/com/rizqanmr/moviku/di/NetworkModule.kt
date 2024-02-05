package com.rizqanmr.moviku.di

import com.rizqanmr.moviku.BuildConfig
import com.rizqanmr.moviku.network.ApiService
import com.rizqanmr.moviku.network.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val retrofitProvider by lazy {
        RetrofitProvider(getOkHttpClient())
    }

    @Provides
    fun provideApiService(): ApiService {
        return retrofitProvider.getApiService()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            addInterceptor(createAuthInterceptor())
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    private fun createAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            val request = chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        }
    }
}