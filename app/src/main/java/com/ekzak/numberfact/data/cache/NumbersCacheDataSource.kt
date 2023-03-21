package com.ekzak.numberfact.data.cache

import com.ekzak.numberfact.data.NumberData

interface NumbersCacheDataSource : FetchFact {

    suspend fun allNumbers(): List<NumberData>

    suspend fun saveNumberFact(numberData: NumberData)

    suspend fun contains(number: String): Boolean
}

interface FetchFact {
    suspend fun fact(number: String): NumberData
}
