package com.ekzak.numberfact.presentation.numbers

interface Mapper<R, S> {
    fun map(source: S): R

    interface Unit<S>: Mapper<kotlin.Unit, S>
}
