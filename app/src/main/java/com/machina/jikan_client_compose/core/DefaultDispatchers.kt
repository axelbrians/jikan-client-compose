package com.machina.jikan_client_compose.core

import kotlinx.coroutines.Dispatchers

class DefaultDispatchers {
    val default = Dispatchers.Default
    val network = Dispatchers.IO
    val main = Dispatchers.Main
    val unconfined = Dispatchers.Unconfined
}