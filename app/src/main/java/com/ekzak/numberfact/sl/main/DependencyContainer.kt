package com.ekzak.numberfact.sl.main

import androidx.lifecycle.ViewModel
import com.ekzak.numberfact.presentation.fact.FactViewModel
import com.ekzak.numberfact.presentation.main.MainViewModel
import com.ekzak.numberfact.presentation.numbers.NumbersViewModel
import com.ekzak.numberfact.sl.fact.NumbersFactModule
import com.ekzak.numberfact.sl.numbers.NumbersModule

interface DependencyContainer {

    fun <T : ViewModel> module(clazz: Class<T>): Module<*>

    class Error : DependencyContainer {
        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> {
            throw IllegalStateException("no module found for $clazz")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error(),
    ) : DependencyContainer {

        override fun <T : ViewModel> module(clazz: Class<T>): Module<*> = when (clazz) {
            MainViewModel::class.java -> MainModule(core)
            NumbersViewModel.Base::class.java -> NumbersModule(core)
            FactViewModel::class.java -> NumbersFactModule(core)
            else -> dependencyContainer.module(clazz)
        }
    }
}
