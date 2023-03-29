package com.ekzak.numberfact.presentation.fact

import androidx.lifecycle.ViewModel
import com.ekzak.numberfact.data.NumberFactDetails

class FactViewModel(
    private val data: NumberFactDetails.Read,
) : ViewModel(), NumberFactDetails.Read {

    override fun read(): String = data.read()
}
