package com.ekzak.numberfact.domain

abstract class DomainException : IllegalStateException()

class NoConnectionException : DomainException()
class ServiceUnavailableException : DomainException()
