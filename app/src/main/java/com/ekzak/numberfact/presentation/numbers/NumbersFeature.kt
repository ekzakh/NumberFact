package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberResult
import com.ekzak.numberfact.presentation.main.Handle
import com.ekzak.numberfact.presentation.main.UiFeature
import kotlinx.coroutines.Job

abstract class NumbersFeature(
    private val communications: NumbersCommunications,
    private val resultMapper: NumberResult.Mapper<Unit>,
) : UiFeature, suspend () -> NumberResult {

    override fun handle(handle: Handle): Job {
        communications.shopProgress(true)
        return handle.handle(this) { result ->
            result.map(resultMapper)
            communications.shopProgress(false)
        }
    }

    protected fun showUi(result: NumberResult) = result.map(resultMapper)
}
