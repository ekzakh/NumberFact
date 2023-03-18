package com.ekzak.numberfact.data

import com.ekzak.numberfact.domain.NoConnectionException
import com.ekzak.numberfact.domain.NumbersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseRepositoryTest {

    private lateinit var repository: NumbersRepository
    private lateinit var cloudDataSource: TestNumbersCloudDataSource
    private lateinit var cacheDataSource: TestNumbersCacheDataSource

    @Before
    fun setUp() {
        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        repository = BaseNumbersRepository(cloudDataSource, cacheDataSource)
    }

    @Test
    fun `test allNumbers`() = runTest {
        cacheDataSource.replaceData(
            listOf(
                NumberData("4", "fact about 4"),
                NumberData("5", "fact about 5")
            )
        )
        val actual = repository.allNumbers()
        val expected = listOf(
            NumberData("4", "fact about 4"),
            NumberData("5", "fact about 5")
        )
        actual.forEachIndexed { index, item ->
            assertEquals(expected[index], item)
        }
        assertEquals(1, cacheDataSource.allNumbersCalled)
    }

    @Test
    fun `test number fact not cached success`() = runTest {
        cloudDataSource.changeExpected(NumberData("10", "fact 10"))
        cacheDataSource.replaceData(emptyList())

        val actual = repository.numberFact("10")
        val expected = NumberData("10", "fact 10")

        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(expected, cacheDataSource.data[0])
        assertEquals(1, cloudDataSource.numberFactCalledCount)
    }

    @Test(expected = NoConnectionException::class)
    fun `test number fact not cached failure`() = runTest {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.numberFact("10")
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(1, cloudDataSource.numberFactCalledCount)
    }

    @Test
    fun `test number fact cached`() = runTest {
        cloudDataSource.changeConnection(true)
        cloudDataSource.changeExpected(NumberData("10", "cloud fact 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val actual = repository.numberFact("10")
        val expected = NumberData("10", "fact 10")
        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cacheDataSource.numberFactCalled.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun `test random number fact not cached success`() = runTest {
        cloudDataSource.changeExpected(NumberData("10", "fact 10"))
        cacheDataSource.replaceData(emptyList())

        val actual = repository.randomNumberFact()
        val expected = NumberData("10", "fact 10")

        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(expected, cacheDataSource.data[0])
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
    }

    @Test(expected = NoConnectionException::class)
    fun `test random number fact not cached failure`() = runTest {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.randomNumberFact()
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(0, cacheDataSource.numberFactCalled.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
    }

    @Test
    fun `test random number fact cached`() = runTest {
        cloudDataSource.changeConnection(true)
        cloudDataSource.changeExpected(NumberData("10", "cloud fact 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val actual = repository.randomNumberFact()
        val expected = NumberData("10", "cloud 10")
        assertEquals(expected, actual)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.numberFactCalled.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    private class TestNumbersCloudDataSource : NumbersCloudDataSource {

        private var isConnection = true
        private var numberData = NumberData("", "")
        var numberFactCalledCount = 0
        var randomNumberFactCalledCount = 0

        fun changeConnection(connected: Boolean) {
            isConnection = connected
        }

        fun changeExpected(expected: NumberData) {
            numberData = expected
        }

        override suspend fun numberFact(number: String): NumberData {
            numberFactCalledCount++
            return if (isConnection) {
                numberData
            } else {
                throw UnknownHostException()
            }
        }

        override suspend fun randomNumberFact(): NumberData {
            randomNumberFactCalledCount++
            return if (isConnection) {
                numberData
            } else {
                throw UnknownHostException()
            }
        }
    }

    private class TestNumbersCacheDataSource : NumbersCacheDataSource {
        var allNumbersCalled = 0
        var saveNumberFactCalledCount = 0
        var containsCalledList = mutableListOf<Boolean>()
        var numberFactCalled = mutableListOf<String>()

        val data = mutableListOf<NumberData>()

        fun replaceData(newData: List<NumberData>): Unit = with(data) {
            clear()
            addAll(newData)
        }

        override suspend fun allNumbers(): List<NumberData> {
            allNumbersCalled++
            return data
        }

        override suspend fun numberFact(number: String): NumberData {
            numberFactCalled.add(number)
            return data[0]
        }

        override suspend fun saveNumberFact(numberData: NumberData) {
            saveNumberFactCalledCount++
            data.add(numberData)
        }

        override fun contains(number: String): Boolean {
            val result = data.find { it.matches() } != null
            containsCalledList.add(result)
            return result
        }
    }
}
