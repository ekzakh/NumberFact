package com.ekzak.numberfact.presentation.numbers

import com.ekzak.numberfact.domain.NumberResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNumbersRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> NumberResult,
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communications: NumbersCommunications,
        private val resultMapper: NumberResult.Mapper<Unit>,
    ) : HandleNumbersRequest {
        override fun handle(coroutineScope: CoroutineScope, block: suspend () -> NumberResult) {
            communications.shopProgress(true)
            coroutineScope.launch(dispatchers.io()) {
                val result = block.invoke()
                result.map(resultMapper)
                communications.shopProgress(false)
            }
        }
    }
}
