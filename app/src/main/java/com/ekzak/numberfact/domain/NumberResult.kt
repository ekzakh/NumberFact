package com.ekzak.numberfact.domain

sealed class NumberResult {

    interface Mapper<T> {
        fun map(list: List<NumberFact>, errorMessage: String) : T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    class Success(private val list: List<NumberFact> = emptyList()): NumberResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list, "")
    }

    class Failure(private val message: String): NumberResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(emptyList(), message)
    }
}
