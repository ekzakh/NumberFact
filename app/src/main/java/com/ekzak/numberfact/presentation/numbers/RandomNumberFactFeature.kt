package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.domain.RandomNumberFactUseCase

class RandomNumberFactFeature(
    communications: NumbersCommunications,
    mapper: NumberResult.Mapper<Unit>,
    private val useCase: RandomNumberFactUseCase,
): NumbersFeature(communications, mapper) {
    override suspend fun invoke(): NumberResult = useCase.fetchRandomNumberFact()
}
