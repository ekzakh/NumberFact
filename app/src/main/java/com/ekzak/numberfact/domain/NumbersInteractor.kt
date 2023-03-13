package com.ekzak.numberfact.domain

interface NumbersInteractor {
    suspend fun init(): NumberResult

    suspend fun fetchNumberFact(number: String): NumberResult

    suspend fun fetchRandomNumberFact(): NumberResult
}
