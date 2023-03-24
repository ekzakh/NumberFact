package com.ekzak.numberfact.presentation.numbers

import android.widget.TextView

data class NumberUi(
    private val number: String,
    private val fact: String,
) : Mapper<Boolean, NumberUi> {

    fun ui() = "$number\n\n$fact"

    fun map(head: TextView, subTitle: TextView) {
        head.text = number
        subTitle.text = fact
    }

    override fun map(source: NumberUi): Boolean = source.number == number
}
