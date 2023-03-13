package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseTest {
    protected class TestNumbersCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
        val numbersList = mutableListOf<NumberUi>()
        var showListCalled = 0

        override fun shopProgress(show: Boolean) {
            progressCalledList.add(true)
        }

        override fun showState(uiState: UiState) {
            stateCalledList.add(uiState)
        }

        override fun showList(list: List<NumberUi>) {
            showListCalled++
            numbersList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) = Unit

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit
    }
}
