package com.ekzak.numberfact.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface CloudModule {
    fun <T> service(clazz: Class<T>): T

    class Base : CloudModule {

        override fun <T> service(clazz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return retrofit.create(clazz)
        }

        companion object {
            private const val BASE_URL = "http://numbersapi.com/"
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Mock : CloudModule {
        override fun <T> service(clazz: Class<T>): T {
            return MockNumbersService() as T
        }
    }
}
