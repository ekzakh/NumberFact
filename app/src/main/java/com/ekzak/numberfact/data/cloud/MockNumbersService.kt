package com.ekzak.numberfact.data.cloud

import okhttp3.Headers.Companion.toHeaders
import retrofit2.Response

class MockNumbersService : NumbersService {

    private var count = 0

    override suspend fun fact(id: String): String = "Fact about $id"

    override suspend fun randomFact(): Response<String> {
        count++
        return Response.success(
            "Fact about $count",
            mapOf("X-Numbers-API-Number" to count.toString()).toHeaders()
        )
    }
}
