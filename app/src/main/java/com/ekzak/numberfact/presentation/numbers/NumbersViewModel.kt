package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekzak.numberfact.R
import com.ekzak.numberfact.domain.NumbersInteractor
import com.ekzak.numberfact.presentation.ManageResources

class NumbersViewModel(
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val manageResources: ManageResources,
    private val handleResult: HandleNumbersRequest,
) : ViewModel(), ObserveNumbers, FetchNumbers {

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
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        } else {
            handleResult.handle(viewModelScope) {
                interactor.fetchNumberFact(number)
            }
        }
    }
}

interface FetchNumbers {
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}
