package com.ekzak.numberfact.data.cloud

import retrofit2.Response

class MockNumbersService : NumbersService {
    override suspend fun fact(id: String): String = "Fact about $id"

    override suspend fun randomFact(): Response<String> {
        TODO("Not yet implemented")
    }
}
