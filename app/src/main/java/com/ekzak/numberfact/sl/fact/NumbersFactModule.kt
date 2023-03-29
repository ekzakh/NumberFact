package com.ekzak.numberfact.sl.fact

import com.ekzak.numberfact.presentation.fact.FactViewModel
import com.ekzak.numberfact.sl.main.Module
import com.ekzak.numberfact.sl.main.ProvideFactDetails

class NumbersFactModule(
    private val provideFactDetails: ProvideFactDetails
): Module<FactViewModel> {
    override fun viewModel(): FactViewModel = FactViewModel(provideFactDetails.provideFactDetails())
}
