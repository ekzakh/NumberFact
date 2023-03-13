package com.ekzak.numberfact.presentation.numbers

import android.widget.TextView

data class NumberUi(
    private val number: String,
    private val fact: String
) {
    fun map(head: TextView, subTitle: TextView) {
        head.text = number
        subTitle.text = fact
    }
}
