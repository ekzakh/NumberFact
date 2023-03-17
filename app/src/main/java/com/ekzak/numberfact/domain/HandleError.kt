package com.ekzak.numberfact.domain

import com.ekzak.numberfact.R
import com.ekzak.numberfact.presentation.ManageResources

interface HandleError {
    fun handle(e: Exception): String

    class Base(private val manageResources: ManageResources) : HandleError {
        override fun handle(e: Exception): String = manageResources.string(
            when (e) {
                is NoConnectionException -> R.string.no_connection_message
                else -> R.string.service_not_available_message
            }
        )
    }

}
