package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekzak.numberfact.R
import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.domain.NumbersInteractor
import com.ekzak.numberfact.presentation.ManageResources
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val dispatchers: DispatchersList,
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val resultMapper: NumberResult.Mapper<Unit>,
    private val manageResources: ManageResources,
) : ViewModel(), ObserveNumbers, FetchNumbers {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)

    override fun observeNumbersList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeNumbersList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            communications.shopProgress(true)
            viewModelScope.launch {
                val result = interactor.init()
                communications.shopProgress(false)
                result.map(resultMapper)
            }
        }
    }

    override fun fetchRandomNumberFact() {
        communications.shopProgress(true)
        viewModelScope.launch(dispatchers.io()) {
            val result = interactor.fetchRandomNumberFact()
            communications.shopProgress(false)
            result.map(resultMapper)
        }
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty()) {
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        } else {
            communications.shopProgress(true)
            viewModelScope.launch(dispatchers.io()) {
                val result = interactor.fetchNumberFact(number)
                result.map(resultMapper)
                communications.shopProgress(false)
            }
        }
    }
}

interface FetchNumbers {
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}
