package com.machina.jikan_client_compose.core.extensions

import com.machina.jikan_client_compose.core.constant.Endpoints
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath

/**
 * Points the request at the Jikan v4 API. [block] sets the endpoint path
 * (e.g. `encodedPath = Endpoints.ANIME_TOP`), which is then placed under the `/v4` prefix.
 */
inline fun HttpRequestBuilder.defaultUrl(crossinline block: URLBuilder.(URLBuilder) -> Unit) {
	url {
		protocol = URLProtocol.HTTPS
		host = Endpoints.HOST
		block(this)
		encodedPath = Endpoints.PATH_V4 + encodedPath.let { if (it.startsWith("/")) it else "/$it" }
	}
}
