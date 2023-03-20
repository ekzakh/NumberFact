package com.ekzak.numberfact.data

data class NumberData(
    private val number: String,
    private val fact: String,
) {
    interface Mapper<T> {
        fun map(number: String, fact: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(number, fact)

    fun matches(other: String) = other == number
}
