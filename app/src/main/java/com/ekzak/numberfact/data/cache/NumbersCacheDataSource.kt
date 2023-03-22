package com.ekzak.numberfact.data.cache

import com.ekzak.numberfact.data.NumberData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface NumbersCacheDataSource : FetchFact {

    suspend fun allNumbers(): List<NumberData>

    suspend fun saveNumberFact(numberData: NumberData)

    suspend fun contains(number: String): Boolean

    class Base(
        private val dao: NumbersDao,
        private val dataToCacheMapper: NumberData.Mapper<NumberCache>,
    ) : NumbersCacheDataSource {

        private val mutex = Mutex()

        override suspend fun allNumbers(): List<NumberData> = mutex.withLock {
            val data = dao.allNumbers()
            return data.map { NumberData(it.number, it.fact) }
        }

        override suspend fun saveNumberFact(numberData: NumberData) = mutex.withLock {
            dao.insert(numberData.map(dataToCacheMapper))
        }

        override suspend fun contains(number: String): Boolean = mutex.withLock {
            val data = dao.numberFact(number)
            return data != null
        }

        override suspend fun numberFact(number: String): NumberData = mutex.withLock {
            val data = dao.numberFact(number) ?: return NumberData("", "")
            return NumberData(data.number, data.fact)
        }
    }
}

interface FetchFact {
    suspend fun numberFact(number: String): NumberData
}
