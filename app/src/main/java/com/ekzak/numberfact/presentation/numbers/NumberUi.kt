package com.ekzak.numberfact.presentation.numbers

import android.widget.TextView

data class NumberUi(
    private val number: String,
    private val fact: String,
) : Mapper<Boolean, NumberUi> {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(number, fact)

    interface Mapper<T> {
        fun map(number: String, fact: String): T
    }

    override fun map(source: NumberUi): Boolean = source.number == number
}

class DetailsUi : NumberUi.Mapper<String> {
    override fun map(number: String, fact: String): String = "$number\n\n$fact"
}

class ListItemUi(
    private val head: TextView,
    private val subTitle: TextView,
) : NumberUi.Mapper<Unit> {
    override fun map(number: String, fact: String) {
        head.text = number
        subTitle.text = fact
    }
}
