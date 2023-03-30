package com.ekzak.numberfact.presentation.main

import com.ekzak.numberfact.presentation.fact.FactFragment
import com.ekzak.numberfact.presentation.numbers.NumbersFragment

sealed class Screen {

    abstract fun fragment(): Class<out BaseFragment<*>>

    object Details: Screen() {
        override fun fragment(): Class<out BaseFragment<*>> = FactFragment::class.java
    }

    object Numbers: Screen() {
        override fun fragment(): Class<out BaseFragment<*>> = NumbersFragment::class.java
    }
}
