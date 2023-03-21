package com.ekzak.numberfact.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface CloudModule {
    fun <T> service(clazz: Class<T>): T

    abstract class Abstract : CloudModule {

        protected abstract val level: HttpLoggingInterceptor.Level
        protected open val baseUrl = "http://numbersapi.com/"

        override fun <T> service(clazz: Class<T>): T {
            //todo refactor when added serviceLocator
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(level)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return retrofit.create(clazz)
        }
    }

    class Debug : CloudModule.Abstract() {

        override val level = HttpLoggingInterceptor.Level.BODY
    }

    class Base : CloudModule.Abstract() {

        override val level = HttpLoggingInterceptor.Level.NONE
    }
}
