package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ekzak.numberfact.domain.NumberDetailsUseCase
import com.ekzak.numberfact.presentation.main.BaseViewModel
import com.ekzak.numberfact.presentation.main.Init
import com.ekzak.numberfact.presentation.main.NavigationCommunication
import com.ekzak.numberfact.presentation.main.NavigationStrategy
import com.ekzak.numberfact.presentation.main.Screen
import com.ekzak.numberfact.presentation.main.UiFeature

interface NumbersViewModel : Init, ObserveNumbers, FetchNumbers, ClearError, ShowDetails {

    class Base(
        dispatchersList: DispatchersList,
        private val initial: UiFeature,
        private val numberFact: NumbersFactFeature,
        private val randomNumberFact: RandomNumberFactFeature,
        private val showDetails: ShowDetails,
        private val communications: NumbersCommunications,
    ) : BaseViewModel(dispatchersList), NumbersViewModel {

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            communications.observeProgress(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
            communications.observeState(owner, observer)

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
            communications.observeNumbersList(owner, observer)

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) initial.handle(this)
        }

        override fun fetchRandomNumberFact() {
            randomNumberFact.handle(this)
        }

        override fun fetchNumberFact(number: String) {
            numberFact.number(number).handle(this)
        }

        override fun clearError() {
            communications.showState(UiState.ClearError)
        }

        override fun showDetails(number: NumberUi) = showDetails.showDetails(number)
    }
}

interface FetchNumbers {
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}

interface ShowDetails {
    fun showDetails(number: NumberUi)

    class Base(
        private val useCase: NumberDetailsUseCase,
        private val navigationCommunication: NavigationCommunication.Mutate,
        private val detailsMapper: NumberUi.Mapper<String>,
    ) : ShowDetails {
        override fun showDetails(number: NumberUi) {
            val details = number.map(detailsMapper)
            useCase.saveDetails(details)
            navigationCommunication.map(NavigationStrategy.Add(Screen.Details))
        }

    }
}

interface ClearError {
    fun clearError()
}
