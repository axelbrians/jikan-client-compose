package com.machina.jikan_client_compose.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class DispatchersProvider(
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val mainImmediate: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher
)

class DefaultDispatchers(
    default: CoroutineDispatcher,
    io: CoroutineDispatcher,
    main: CoroutineDispatcher,
    mainImmediate: CoroutineDispatcher,
    unconfined: CoroutineDispatcher
) : DispatchersProvider(default, io, main, mainImmediate, unconfined)

class TestDispatchers(
    default: CoroutineDispatcher,
    io: CoroutineDispatcher,
    main: CoroutineDispatcher,
    mainImmediate: CoroutineDispatcher,
    unconfined: CoroutineDispatcher
): DispatchersProvider(
    default = Dispatchers.Unconfined,
    io = Dispatchers.Unconfined,
    main = Dispatchers.Unconfined,
    mainImmediate = Dispatchers.Unconfined,
    unconfined = Dispatchers.Unconfined
)