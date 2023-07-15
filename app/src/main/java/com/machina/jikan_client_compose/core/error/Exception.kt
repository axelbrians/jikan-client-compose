package com.machina.jikan_client_compose.core.error

class EmptyDataException(
	override val message: String = "Success but data is empty"
): Exception()