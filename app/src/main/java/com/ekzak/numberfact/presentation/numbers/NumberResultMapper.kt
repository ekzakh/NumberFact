package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberFact
import com.ekzak.numberfact.domain.NumberResult

class NumberResultMapper(
    private val communications: NumbersCommunications,
    private val mapperToUi: NumberFact.Mapper<NumberUi>,
) : NumberResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) {
        communications.showState(
            if (errorMessage.isEmpty()) {
                val numbersList = list.map { it.map(mapperToUi) }
                if (numbersList.isNotEmpty()) {
                    communications.showList(numbersList)
                }
                UiState.Success
            } else {
                UiState.Error(errorMessage)
            }
        )
    }
}
