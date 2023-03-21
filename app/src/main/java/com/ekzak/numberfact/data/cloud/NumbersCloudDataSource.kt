package com.ekzak.numberfact.data.cloud

import com.ekzak.numberfact.data.cache.FetchFact
import com.ekzak.numberfact.data.NumberData

interface NumbersCloudDataSource : FetchFact {

    suspend fun randomNumberFact(): NumberData

    class Base(private val service: NumbersService) : NumbersCloudDataSource {
        override suspend fun randomNumberFact(): NumberData {
            val response = service.randomFact()
            val body = response.body() ?: throw IllegalStateException("Service unavailable")
            val headers = response.headers()
            headers.find { (key, _) -> key == RANDOM_API_HEADER }?.let { (_, value) ->
                return NumberData(value, body)
            }
            throw IllegalStateException("Service unavailable")
        }

        override suspend fun fact(number: String): NumberData {
            val fact = service.fact(number)
            return NumberData(number, fact)
        }

        companion object {
            private const val RANDOM_API_HEADER = "X-Numbers-API-Number"
        }
    }
}
