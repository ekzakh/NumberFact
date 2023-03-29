package com.ekzak.numberfact.domain

import com.ekzak.numberfact.data.NumberFactDetails

interface NumbersInteractor {
    suspend fun init(): NumberResult

    suspend fun fetchNumberFact(number: String): NumberResult

    suspend fun fetchRandomNumberFact(): NumberResult

    fun saveDetails(details: String)

    class Base(
        private val repository: NumbersRepository,
        private val handleRequest: HandleRequest,
        private val numberFactDetails: NumberFactDetails.Save
    ) : NumbersInteractor {
        override suspend fun init(): NumberResult {
            return NumberResult.Success(repository.allNumbers())
        }

        override suspend fun fetchNumberFact(number: String): NumberResult = handleRequest.handle {
            repository.numberFact(number)
        }

        override suspend fun fetchRandomNumberFact(): NumberResult = handleRequest.handle {
            repository.randomNumberFact()
        }

        override fun saveDetails(details: String) {
            numberFactDetails.save(details)
        }
    }
}


interface HandleRequest {

    suspend fun handle(block: suspend () -> Unit): NumberResult

    class Base(
        private val handleError: HandleError<String>,
        private val repository: NumbersRepository,
    ) : HandleRequest {
        override suspend fun handle(block: suspend () -> Unit): NumberResult = try {
            block.invoke()
            NumberResult.Success(repository.allNumbers())
        } catch (e: Exception) {
            NumberResult.Failure(handleError.handle(e))
        }
    }
}
