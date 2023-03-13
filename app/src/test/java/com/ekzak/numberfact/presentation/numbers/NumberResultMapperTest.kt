package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberFact
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumberResultMapperTest: BaseTest() {

    private lateinit var communications: TestNumbersCommunications
    private lateinit var mapper: NumberResultMapper

    @Before
    fun init() {
        communications = TestNumbersCommunications()
        mapper = NumberResultMapper(communications, NumberUiMapper())
    }
    @Test
    fun `test error`() {
        mapper.map(emptyList(), "error message")
        assertEquals(UiState.Error("error message"), communications.stateCalledList[0])
    }

    @Test
    fun `test success empty result`() {
        mapper.map(emptyList(), "")
        assertEquals(UiState.Success, communications.stateCalledList[0])
        assertEquals(0, communications.showListCalled)
    }

    @Test
    fun `test success some result`() {
        mapper.map(listOf(NumberFact("22","fact about 22")), "")
        assertEquals(listOf(NumberUi("22","fact about 22")), communications.numbersList)
        assertEquals(UiState.Success, communications.stateCalledList[0])
        assertEquals(1, communications.showListCalled)
    }
}
