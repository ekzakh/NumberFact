package com.ekzak.numberfact.sl.main

import com.ekzak.numberfact.presentation.main.MainViewModel

class MainModule(private val provideNavigation: ProvideNavigation) : Module<MainViewModel> {

    override fun viewModel(): MainViewModel = MainViewModel(provideNavigation.provideNavigation())
}
