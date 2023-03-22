package com.ekzak.numberfact.data.cache

import com.ekzak.numberfact.data.NumberData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NumbersCacheDataSourceTest {

    private lateinit var dataSource: NumbersCacheDataSource
    private lateinit var dataToCacheMapper: NumberData.Mapper<NumberCache>
    private lateinit var dao: TestDao

    @Before
    fun setUp() {
        dao = TestDao()
        dataToCacheMapper = TestNumberDataToCache(0)
        dataSource = NumbersCacheDataSource.Base(dao, dataToCacheMapper)
    }

    @Test
    fun test_all_numbers_empty() = runTest {
        val expected = emptyList<NumberCache>()
        val actual = dataSource.allNumbers()

        assertEquals(expected, actual)
    }

    @Test
    fun test_all_numbers_not_empty() = runTest {
        dao.insert(NumberCache("1", "fact 1", 1))
        dao.insert(NumberCache("2", "fact 2", 2))
        dao.insert(NumberCache("3", "fact 3", 3))

        val expected = listOf(
            NumberData("1", "fact 1"),
            NumberData("2", "fact 2"),
            NumberData("3", "fact 3")
        )
        val actual = dataSource.allNumbers()

        assertEquals(expected, actual)
    }

    @Test
    fun test_contains() = runTest {
        dao.data.add(NumberCache("1", "fact 1", 1))

        assertFalse(dataSource.contains("5"))
        assertTrue(dataSource.contains("1"))
    }

    @Test
    fun test_save_number_fact() = runTest {
        dataSource.saveNumberFact(NumberData("9", "fact 9"))

        val expected = NumberCache("9", "fact 9", 0)
        val actual = dao.data[0]

        assertEquals(expected, actual)
    }

    @Test
    fun test_number_fact_contains() = runTest {
        dao.data.add(NumberCache("1","fact 1", 0))

        val expected = NumberData("1", "fact 1")
        val actual = dataSource.numberFact("1")

        assertEquals(expected, actual)
    }

    @Test
    fun test_number_fact_not_contains() = runTest {
        dao.data.add(NumberCache("1","fact 1", 0))

        val expected = NumberData("", "")
        val actual = dataSource.numberFact("2")

        assertEquals(expected, actual)
    }

}

class TestDao: NumbersDao {
    val data = mutableListOf<NumberCache>()

    override suspend fun allNumbers(): List<NumberCache> {
        return data
    }

    override suspend fun insert(numberCache: NumberCache) {
        data.add(numberCache)
    }

    override suspend fun numberFact(number: String): NumberCache? {
        return data.find { it.number == number }
    }

}

private class TestNumberDataToCache(private val date: Long) : NumberData.Mapper<NumberCache> {

    override fun map(number: String, fact: String): NumberCache = NumberCache(number, fact, date)

}
