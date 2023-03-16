package com.ekzak.numberfact.presentation.numbers

sealed class UiState {

    interface Mapper<T> {
        fun map(message: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    object Success : UiState() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map("")
    }

    data class Error(private val message: String): UiState() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map( message)
    }
}
