package com.ekzak.numberfact.presentation.numbers

import org.junit.Assert.*
import org.junit.Test

class NumbersViewModelTest {
    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and wait for result
     */

    fun `test init and re-init`() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        viewModel.init(isFirstRun = true)
        interactor.changeExpectedResult(NumbersResult.Success())

        //show progress
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        //hide progress
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        //result success
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(emptyList<NumberFact>()), communications.stateCalledList[0])
        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.showListCalled)

        //get random fact with failure
        interactor.changeExpectedResult(NumbersResult.Failure("No internet connection"))
        viewModel.fetchRandomNumberFact()
        //show progress
        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchRandomNumberFactCalledList.size)
        //hide progress
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])
        //result
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("No internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.showListCalled)

        //rotate screen
        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(1, communications.showListCalled)
    }

    /**
     * Try to get fact about empty number
     */
    @Test
    fun `test about empty number`() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        viewModel.fetchFact("")
        //show progress
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(0, interactor.fetchNumberFactCalledList.size)
        //hide progress
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])
        //result
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("Entered number is empty"), communications.stateCalledList[0])
        assertEquals(0, communications.showListCalled)
    }

    /**
     * Try to get fact about some number
     */
    @Test
    fun `test about some number`() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        viewModel.fetchFact("89")
        interactor.changeExpectedResult(NumbersResult.Success(listOf(NumberFact("89", "Fact about 89"))))
        //show progress
        assertEquals(0, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchNumberFactCalledList.size)
        assertEquals(NumberFact("89", "Fact about 89"), interactor.fetchNumberFactCalledList[0])
        //hide progress
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])
        //result
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Succes(""), communications.stateCalledList[0])
        assertEquals(1, communications.showListCalled)
        assertEquals(NumberUi("89", "Fact about 89"), communications.numbersList[0])
    }

    class TestNumbersCommunications : NumbersCommunications {
        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
        val numbersList = mutableListOf<NumberUi>()
        var showListCalled = 0

        override fun shopProgress(show: Boolean) {
            progressCalledList.add(true)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            showListCalled++
            numbersList.addAll(list)
        }
    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()
        val fetchNumberFactCalledList = mutableListOf<NumbersResult>()
        val fetchRandomNumberFactCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun fetchNumberFact(number: String): NumbersResult {
            fetchNumberFactCalledList.add(result)
            return result
        }

        override suspend fun fetchRandomNumberFact(): NumbersResult {
            fetchRandomNumberFactCalledList.add(result)
            return result
        }

    }
}
