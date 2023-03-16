package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberFact
import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.domain.NumbersInteractor
import com.ekzak.numberfact.presentation.ManageResources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : BaseTest() {

    private lateinit var communications: TestNumbersCommunications
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var viewModel: NumbersViewModel
    private lateinit var manageResources: TestManageResources
    private lateinit var dispatchersList: TestDispatchersList
    private lateinit var handleNumbersRequest: HandleNumbersRequest

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        communications = TestNumbersCommunications()
        interactor = TestNumbersInteractor()
        manageResources = TestManageResources()
        dispatchersList = TestDispatchersList()
        handleNumbersRequest = HandleNumbersRequest.Base(
            dispatchersList,
            communications,
            NumberResultMapper(communications, NumberUiMapper())
        )
        viewModel = NumbersViewModel(
            communications,
            interactor,
            manageResources,
            handleNumbersRequest
        )
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and wait for result
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test init and re-init`() = runTest {

        viewModel.init(isFirstRun = true)
        interactor.changeExpectedResult(NumberResult.Success())

        //show progress
        assertEquals(true, communications.progressCalledList[0])
        //hide progress
        assertEquals(false, communications.progressCalledList[1])

        //result success
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])
        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.showListCalled)

        //get random fact with failure
        interactor.changeExpectedResult(NumberResult.Failure("No internet connection"))
        viewModel.fetchRandomNumberFact()
        //show progress
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchRandomNumberFactCalledList.size)
        //hide progress
        assertEquals(false, communications.progressCalledList[3])
        //result
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("No internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.numbersList.size)

        //rotate screen
        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
    }

    /**
     * Try to get fact about empty number
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test about empty number`() = runTest {

        manageResources.makeExpectedAnswer("Please input a number")
        viewModel.fetchNumberFact("")
        //don't show progress
        assertEquals(0, communications.progressCalledList.size)

        assertEquals(0, interactor.fetchNumberFactCalledList.size)
        //result
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("Please input a number"), communications.stateCalledList[0])
        assertEquals(0, communications.showListCalled)
    }

    /**
     * Try to get fact about some number
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test about some number`() = runTest {

        interactor.changeExpectedResult(NumberResult.Success(listOf(NumberFact("89", "Fact about 89"))))
        viewModel.fetchNumberFact("89")
        //show progress
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchNumberFactCalledList.size)
        assertEquals(
            NumberResult.Success(listOf(NumberFact("89", "Fact about 89"))),
            interactor.fetchNumberFactCalledList[0]
        )
        //hide progress
        assertEquals(false, communications.progressCalledList[1])
        //result
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])
        assertEquals(1, communications.showListCalled)
        assertEquals(NumberUi("89", "Fact about 89"), communications.numbersList[0])
    }

    private class TestManageResources : ManageResources {
        private var stringValue: String = ""
        override fun string(id: Int): String {
            return stringValue
        }

        fun makeExpectedAnswer(expected: String) {
            stringValue = expected
        }
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    ) : DispatchersList {
        override fun io(): CoroutineDispatcher = dispatcher

        override fun ui(): CoroutineDispatcher = dispatcher
    }

}
