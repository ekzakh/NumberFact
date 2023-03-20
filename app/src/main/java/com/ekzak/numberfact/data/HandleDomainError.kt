package com.ekzak.numberfact.data

import com.ekzak.numberfact.domain.HandleError
import com.ekzak.numberfact.domain.NoConnectionException
import com.ekzak.numberfact.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {
    override fun handle(e: Exception): Exception {
        return when (e) {
            is UnknownHostException -> NoConnectionException()
            else -> ServiceUnavailableException()
        }
    }
}
