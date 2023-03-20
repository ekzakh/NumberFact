package com.ekzak.numberfact.data

interface NumbersCloudDataSource : FetchFact {

    suspend fun randomNumberFact(): NumberData
}
