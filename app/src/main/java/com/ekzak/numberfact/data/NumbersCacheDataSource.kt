package com.ekzak.numberfact.data

interface NumbersCacheDataSource : FetchFact {

    suspend fun allNumbers(): List<NumberData>

    suspend fun saveNumberFact(numberData: NumberData)

    suspend fun contains(number: String): Boolean
}

interface FetchFact {
    suspend fun fact(number: String): NumberData
}
