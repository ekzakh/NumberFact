package com.ekzak.numberfact.data

import com.ekzak.numberfact.data.cache.NumbersCacheDataSource
import com.ekzak.numberfact.data.cloud.NumbersCloudDataSource
import com.ekzak.numberfact.domain.NumberFact
import com.ekzak.numberfact.domain.NumbersRepository

class BaseNumbersRepository(
    private val cloudDataSource: NumbersCloudDataSource,
    private val cacheDataSource: NumbersCacheDataSource,
    private val mapperToDomain: NumberData.Mapper<NumberFact>,
    private val handleDataRequest: HandleDataRequest,
) : NumbersRepository {

    override suspend fun allNumbers(): List<NumberFact> {
        val data = cacheDataSource.allNumbers()
        return data.map { it.map(mapperToDomain) }
    }

    override suspend fun numberFact(number: String): NumberFact = handleDataRequest.handle {
        val dataSource = if (cacheDataSource.contains(number)) {
            cacheDataSource
        } else {
            cloudDataSource
        }
        dataSource.numberFact(number)
    }

    override suspend fun randomNumberFact(): NumberFact = handleDataRequest.handle {
        cloudDataSource.randomNumberFact()
    }
}
