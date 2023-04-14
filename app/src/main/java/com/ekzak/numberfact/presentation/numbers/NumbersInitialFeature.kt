package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.domain.NumbersInitialUseCase

class NumbersInitialFeature(
    communications: NumbersCommunications,
    resultMapper: NumberResult.Mapper<Unit>,
    private val useCase: NumbersInitialUseCase,
) : NumbersFeature(communications, resultMapper), suspend () -> NumberResult {

    override suspend fun invoke(): NumberResult = useCase.init()
}
