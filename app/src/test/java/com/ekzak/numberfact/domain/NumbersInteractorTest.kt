package com.ekzak.numberfact.domain

import com.ekzak.numberfact.presentation.ManageResources
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumbersInteractorTest {
    private lateinit var interactor: NumbersInteractor
    private lateinit var repository: TestRepository
    private lateinit var manageResources: TestManageResources

    @Before
    fun setUp() {
        repository = TestRepository()
        manageResources = TestManageResources()
        interactor =
            NumbersInteractor.Base(
                repository,
                HandleRequest.Base(HandleError.Base(manageResources), repository)
            )
    }

    @Test
    fun `test init success`() = runTest {
        repository.changeExpectedResult(emptyList())
        val actual = interactor.init()
        val expected = NumberResult.Success(emptyList())
        assertEquals(expected, actual)
        assertEquals(1, repository.allNumbersCalledCount)
    }

    @Test
    fun `test about number fact success`() = runTest {
        repository.changeExpectedFactOfNumber(NumberFact("7", "fact 7"))

        val actual = interactor.fetchNumberFact("7")
        val expected = NumberResult.Success(listOf(NumberFact("7", "fact 7")))

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun `test about number fact error`() = runTest {
        repository.expectingErrorGetFact(true)
        manageResources.changeExpected("No internet connection")

        val actual = interactor.fetchNumberFact("7")
        val expected = NumberResult.Failure("No internet connection")

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun `test about number random fact success`() = runTest {
        repository.changeExpectedRandomFact(NumberFact("10", "fact 10"))

        val actual = interactor.fetchRandomNumberFact()
        val expected = NumberResult.Success(listOf(NumberFact("10", "fact 10")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactList.size)
    }

    @Test
    fun `test about number random fact error`() = runTest {
        repository.expectingErrorGetFact(true)
        manageResources.changeExpected("No internet connection")
        val actual = interactor.fetchNumberFact("10")
        val expected = NumberResult.Failure("No internet connection")

        assertEquals(expected, actual)
        assertEquals(0, repository.randomNumberFactList.size)
    }

    private class TestRepository : NumbersRepository {
        private val allNumbers = mutableListOf<NumberFact>()
        private var numberFact = NumberFact("", "")
        private var randomFact = NumberFact("", "")
        private var errorWhileGetNumberFact = false
        var allNumbersCalledCount = 0
        var numberFactCalledList = mutableListOf<String>()
        var randomNumberFactList = mutableListOf<String>()

        fun changeExpectedResult(numbers: List<NumberFact>) {
            allNumbers.clear()
            allNumbers.addAll(numbers)
        }

        fun changeExpectedFactOfNumber(expectedNumberFact: NumberFact) {
            numberFact = expectedNumberFact
        }

        fun changeExpectedRandomFact(expectedRandomFact: NumberFact) {
            randomFact = expectedRandomFact
        }

        fun expectingErrorGetFact(error: Boolean) {
            errorWhileGetNumberFact = error
        }

        override suspend fun allNumbers(): List<NumberFact> {
            allNumbersCalledCount++
            return allNumbers
        }

        override suspend fun numberFact(number: String): NumberFact {
            numberFactCalledList.add(number)
            if (errorWhileGetNumberFact) throw NoConnectionException()
            allNumbers.add(numberFact)
            return numberFact
        }

        override suspend fun randomNumberFact(): NumberFact {
            randomNumberFactList.add("")
            if (errorWhileGetNumberFact) throw NoConnectionException()
            allNumbers.add(randomFact)
            return randomFact
        }
    }

    private class TestManageResources : ManageResources {
        private var string: String = ""

        fun changeExpected(expected: String) {
            string = expected
        }

        override fun string(id: Int): String {
            return string
        }
    }
}
