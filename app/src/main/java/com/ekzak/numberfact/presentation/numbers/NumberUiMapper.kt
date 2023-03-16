package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberFact

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(number: String, fact: String): NumberUi = NumberUi(number, fact)
}
