package com.ekzak.numberfact.domain

import com.ekzak.numberfact.R
import com.ekzak.numberfact.presentation.main.ManageResources

interface HandleError<T> {
    fun handle(e: Exception): T

    class Base(private val manageResources: ManageResources) : HandleError<String> {
        override fun handle(e: Exception): String = manageResources.string(
            when (e) {
                is NoConnectionException -> R.string.no_connection_message
                else -> R.string.service_not_available_message
            }
        )
    }

}
