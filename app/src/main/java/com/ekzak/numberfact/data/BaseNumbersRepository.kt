package com.ekzak.numberfact.data

import com.ekzak.numberfact.domain.NumberFact
import com.ekzak.numberfact.domain.NumbersRepository

class BaseNumbersRepository: NumbersRepository {

    override suspend fun allNumbers(): List<NumberFact> {
        TODO("Not yet implemented")
    }

    override suspend fun numberFact(number: String): NumberFact {
        TODO("Not yet implemented")
    }

    override suspend fun randomNumberFact(): NumberFact {
        TODO("Not yet implemented")
    }
}
