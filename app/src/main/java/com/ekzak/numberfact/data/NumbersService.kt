package com.ekzak.numberfact.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersService {

    @GET("{id}")
    suspend fun fact(@Path("id") id: String): String

    @GET("random/math")
    suspend fun randomFact(): Response<String>
}
