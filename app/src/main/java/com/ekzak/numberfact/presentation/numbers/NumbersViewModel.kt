package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.domain.NumbersInteractor
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val resultMapper: NumberResult.Mapper<Unit>,
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
        TODO("Not yet implemented")
    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
    }
}

interface FetchNumbers {
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}
