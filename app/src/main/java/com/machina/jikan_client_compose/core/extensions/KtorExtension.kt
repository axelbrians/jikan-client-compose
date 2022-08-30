package com.machina.jikan_client_compose.core.extensions

import com.machina.jikan_client_compose.core.constant.Endpoints
import io.ktor.client.request.*
import io.ktor.http.*

inline fun HttpRequestBuilder.defaultUrl(crossinline block: URLBuilder.(URLBuilder) -> Unit) {
	url {
		protocol = URLProtocol.HTTPS
		host = Endpoints.HOST_V4
		block(this)
	}
}