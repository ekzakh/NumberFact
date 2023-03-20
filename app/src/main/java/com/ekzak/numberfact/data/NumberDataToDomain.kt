package com.ekzak.numberfact.data

import com.ekzak.numberfact.domain.NumberFact

class NumberDataToDomain : NumberData.Mapper<NumberFact> {
    override fun map(number: String, fact: String): NumberFact = NumberFact(number, fact)
}
