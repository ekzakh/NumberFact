package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ekzak.numberfact.presentation.main.NavigationCommunication
import com.ekzak.numberfact.presentation.main.NavigationStrategy

abstract class BaseTest {
    protected class TestNumbersCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
        val numbersList = mutableListOf<NumberUi>()
        var showListCalled = 0

        override fun shopProgress(show: Boolean) {
            progressCalledList.add(show)
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


    protected class TestNavigationCommunication: NavigationCommunication.Mutable {

        lateinit var strategy: NavigationStrategy
        var count = 0

        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) = Unit

        override fun map(source: NavigationStrategy) {
            strategy = source
            count++
        }
    }
}
