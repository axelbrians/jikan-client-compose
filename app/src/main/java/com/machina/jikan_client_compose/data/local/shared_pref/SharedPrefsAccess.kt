package com.machina.jikan_client_compose.data.local.shared_pref

import android.content.SharedPreferences

class SharedPrefsAccess(
	private val prefs: SharedPreferences
): SharedPrefsContract {

	private val editor: SharedPreferences.Editor = prefs.edit()

	override fun writeContentSmallGridSize(value: Int) {
		editor.putInt(SharedPrefsKey.ContentSmallGridSize, value)
		editor.commit()
	}

	override fun getContentSmallGridSize(): Int {
		return prefs.getInt(SharedPrefsKey.ContentSmallGridSize, 4)
	}

}