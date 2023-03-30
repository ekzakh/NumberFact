package com.ekzak.numberfact.presentation.main

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface NavigationStrategy {

    fun navigate(manager: FragmentManager, containerId: Int)

    abstract class Abstract(protected open val screen: Screen) : NavigationStrategy {

        override fun navigate(manager: FragmentManager, containerId: Int) {
            manager.beginTransaction()
                .executeTransaction(containerId)
                .commit()
        }

        protected abstract fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction
    }

    data class Replace(override val screen: Screen) : Abstract(screen) {

        override fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction =
            replace(containerId, screen.fragment().newInstance())
    }

    data class Add(override val screen: Screen) : Abstract(screen) {

        override fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction =
            screen.fragment().let { fragmentClass ->
                add(containerId, fragmentClass.newInstance())
                    .addToBackStack(fragmentClass.simpleName)
            }
    }
}
