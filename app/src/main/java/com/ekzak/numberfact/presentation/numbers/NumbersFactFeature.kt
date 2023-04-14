package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.R
import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.domain.NumbersFactUseCase
import com.ekzak.numberfact.presentation.main.Handle
import com.ekzak.numberfact.presentation.main.ManageResources
import com.ekzak.numberfact.presentation.main.UiFeature
import kotlinx.coroutines.Job

interface NumbersFactFeature : UiFeature, suspend () -> NumberResult {

    fun number(number: String): UiFeature

    class Base(
        communications: NumbersCommunications,
        mapper: NumberResult.Mapper<Unit>,
        private val useCase: NumbersFactUseCase,
        private val manageResources: ManageResources,
    ) : NumbersFeature(communications, mapper), NumbersFactFeature {

        private var number: String = "0"

        override fun number(number: String): UiFeature {
            this.number = number
            return this
        }

        override fun handle(handle: Handle): Job =
            if (number.isEmpty()) {
                handle.handle({
                    NumberResult.Failure(manageResources.string(R.string.empty_number_error_message))
                }) {
                    showUi(it)
                }
            } else {
                super.handle(handle)
            }


        override suspend fun invoke(): NumberResult = useCase.fetchNumberFact(number)
    }
}
