package com.ekzak.numberfact.data

import com.ekzak.numberfact.domain.HandleError
import com.ekzak.numberfact.domain.NumberFact

interface HandleDataRequest {
    suspend fun handle(block: suspend () -> NumberData): NumberFact

    class Base(
        private val handleError: HandleError<Exception>,
        private val cacheDataSource: NumbersCacheDataSource,
        private val mapperToDomain: NumberData.Mapper<NumberFact>,
    ) : HandleDataRequest {
        override suspend fun handle(block: suspend () -> NumberData): NumberFact = try {
            val result = block.invoke()
            cacheDataSource.saveNumberFact(result)
            result.map(mapperToDomain)
        } catch (e: Exception) {
            throw handleError.handle(e)
        }
    }
}
