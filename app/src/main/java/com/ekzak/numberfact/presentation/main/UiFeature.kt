package com.ekzak.numberfact.presentation.main

import kotlinx.coroutines.Job

interface UiFeature {

    fun handle(handle: Handle): Job
}
