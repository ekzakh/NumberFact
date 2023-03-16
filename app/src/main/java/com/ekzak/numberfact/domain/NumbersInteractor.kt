package com.ekzak.numberfact.domain

interface NumbersInteractor {
    suspend fun init(): NumberResult

    suspend fun fetchNumberFact(number: String): NumberResult

    suspend fun fetchRandomNumberFact(): NumberResult

    class Base : NumbersInteractor {
        override suspend fun init(): NumberResult {
            TODO("Not yet implemented")
        }

        override suspend fun fetchNumberFact(number: String): NumberResult {
            TODO("Not yet implemented")
        }

        override suspend fun fetchRandomNumberFact(): NumberResult {
            TODO("Not yet implemented")
        }

    }
}
