package com.ekzak.numberfact.sl.main

import com.ekzak.numberfact.presentation.main.NavigationCommunication

interface ProvideNavigation {
    fun provideNavigation(): NavigationCommunication.Mutable
}
