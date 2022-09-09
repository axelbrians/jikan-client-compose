package com.machina.jikan_client_compose.data.local.shared_pref

interface SharedPrefsContract {

	fun writeContentSmallGridSize(value: Int)

	fun getContentSmallGridSize(): Int
}