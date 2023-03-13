package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumbersCommunications : ObserveNumbers {
    fun shopProgress(show: Boolean)

    fun showState(uiState: UiState)

    fun showList(list: List<NumberUi>)

    class Base(
        private val progress: ProgressCommunication,
        private val state: NumbersStateCommunication,
        private val numbersList: NumbersListCommunication,
    ) : NumbersCommunications {

        override fun shopProgress(show: Boolean) = progress.map(show)

        override fun showState(uiState: UiState) = state.map(uiState)

        override fun showList(list: List<NumberUi>) = numbersList.map(list)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            progress.observe(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
            state.observe(owner, observer)

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
            numbersList.observe(owner, observer)
    }
}

interface ObserveNumbers {
    fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>)
    fun observeState(owner: LifecycleOwner, observer: Observer<UiState>)
    fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>)
}

interface ProgressCommunication : Communications.Mutable<Boolean> {
    class Base : Communications.Post<Boolean>(), ProgressCommunication
}

interface NumbersStateCommunication : Communications.Mutable<UiState> {
    class Base : Communications.Post<UiState>(), NumbersStateCommunication
}

interface NumbersListCommunication : Communications.Mutable<List<NumberUi>> {
    class Base : Communications.Post<List<NumberUi>>(), NumbersListCommunication
}
