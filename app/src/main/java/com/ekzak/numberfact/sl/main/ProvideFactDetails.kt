package com.ekzak.numberfact.sl.main

import com.ekzak.numberfact.data.NumberFactDetails

interface ProvideFactDetails {
    fun provideFactDetails(): NumberFactDetails.Mutable
}
