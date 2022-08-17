package com.machina.jikan_client_compose.core.error

class UnknownError: Throwable() {

    override val message = "Unknown error occured."
}