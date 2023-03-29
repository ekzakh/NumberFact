package com.ekzak.numberfact.presentation.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ekzak.numberfact.presentation.numbers.Communications
import com.ekzak.numberfact.presentation.numbers.NumbersFragment

class MainViewModel(
    private val navigationCommunication: NavigationCommunication.Mutable,
) : ViewModel(), Init, Communications.Observe<NavigationStrategy> {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            navigationCommunication.map(NavigationStrategy.Replace(NumbersFragment()))
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) {
        navigationCommunication.observe(owner, observer)
    }
}
