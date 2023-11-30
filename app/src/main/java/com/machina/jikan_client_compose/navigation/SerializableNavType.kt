package com.machina.jikan_client_compose.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

open class SerializableNavType<T>(
	private val serializer: KSerializer<T>
) : NavType<T>(false) {

	open fun requireGet(bundle: Bundle?, key: String): T {
		return requireNotNull(get(requireNotNull(bundle), key))
	}

	override fun get(bundle: Bundle, key: String): T? {
		return bundle.getString(key)?.let { parseValue(it) }
	}

	override fun put(bundle: Bundle, key: String, value: T) {
		bundle.putString(key, serializeAsValue(value))
	}

	override fun parseValue(value: String): T {
		val uriDecoded = Uri.decode(value)
		return Json.decodeFromString(serializer, uriDecoded)
	}

	override fun serializeAsValue(value: T): String {
		val stringEncoded = Json.encodeToString(serializer, value)
		return Uri.encode(stringEncoded)
	}
}
