package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ekzak.numberfact.domain.NumberFact
import com.ekzak.numberfact.domain.NumbersInteractor
import com.ekzak.numberfact.domain.NumberResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : BaseTest(){

    private lateinit var communications: TestNumbersCommunications
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var viewModel: NumbersViewModel

    @Before
    fun init() {
        communications = TestNumbersCommunications()
        interactor = TestNumbersInteractor()
        viewModel = NumbersViewModel(
            communications,
            interactor,
            NumberResultMapper(communications, NumberUiMapper())
        )
    }
    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and wait for result
     */
    @Test
    fun `test init and re-init`() {

        viewModel.init(isFirstRun = true)
        interactor.changeExpectedResult(NumberResult.Success())

        //show progress
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.initCalledList.size)

        //hide progress
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        //result success
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])
        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.showListCalled)

        //get random fact with failure
        interactor.changeExpectedResult(NumberResult.Failure("No internet connection"))
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

        viewModel.fetchNumberFact("")
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

        viewModel.fetchNumberFact("89")
        interactor.changeExpectedResult(NumberResult.Success(listOf(NumberFact("89", "Fact about 89"))))
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
        assertEquals(UiState.Success, communications.stateCalledList[0])
        assertEquals(1, communications.showListCalled)
        assertEquals(NumberUi("89", "Fact about 89"), communications.numbersList[0])
    }



    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumberResult = NumberResult.Success()

        val initCalledList = mutableListOf<NumberResult>()
        val fetchNumberFactCalledList = mutableListOf<NumberResult>()
        val fetchRandomNumberFactCalledList = mutableListOf<NumberResult>()

        fun changeExpectedResult(newResult: NumberResult) {
            result = newResult
        }

        override suspend fun init(): NumberResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun fetchNumberFact(number: String): NumberResult {
            fetchNumberFactCalledList.add(result)
            return result
        }

        override suspend fun fetchRandomNumberFact(): NumberResult {
            fetchRandomNumberFactCalledList.add(result)
            return result
        }
    }
}
