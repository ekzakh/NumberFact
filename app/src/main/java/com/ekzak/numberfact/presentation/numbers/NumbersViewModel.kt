package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekzak.numberfact.R
import com.ekzak.numberfact.domain.NumbersInteractor
import com.ekzak.numberfact.presentation.main.Init
import com.ekzak.numberfact.presentation.main.ManageResources
import com.ekzak.numberfact.presentation.main.NavigationCommunication
import com.ekzak.numberfact.presentation.main.NavigationStrategy
import com.ekzak.numberfact.presentation.main.Screen

interface NumbersViewModel : Init, ObserveNumbers, FetchNumbers, ClearError {

    fun showFact(number: NumberUi)

    class Base(
        private val communications: NumbersCommunications,
        private val interactor: NumbersInteractor,
        private val manageResources: ManageResources,
        private val handleResult: HandleNumbersRequest,
        private val navigationCommunication: NavigationCommunication.Mutate,
        private val detailsMapper: NumberUi.Mapper<String>,
    ) : ViewModel(), NumbersViewModel {

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            communications.observeProgress(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
            communications.observeState(owner, observer)

        override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
            communications.observeNumbersList(owner, observer)

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) {
                handleResult.handle(viewModelScope) {
                    interactor.init()
                }
            }
        }

        override fun fetchRandomNumberFact() {
            handleResult.handle(viewModelScope) {
                interactor.fetchRandomNumberFact()
            }
        }

        override fun fetchNumberFact(number: String) {
            if (number.isEmpty()) {
                communications.showState(UiState.ShowError(manageResources.string(R.string.empty_number_error_message)))
            } else {
                handleResult.handle(viewModelScope) {
                    interactor.fetchNumberFact(number)
                }
            }
        }

        override fun clearError() {
            communications.showState(UiState.ClearError)
        }

        override fun showFact(number: NumberUi) {
            val details = number.map(detailsMapper)
            interactor.saveDetails(details)
            navigationCommunication.map(NavigationStrategy.Add(Screen.Details))
        }
    }
}

interface FetchNumbers {
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}

interface ClearError {
    fun clearError()
}
