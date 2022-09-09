package com.machina.jikan_client_compose.domain.use_case.pref_content_small_grid_size

import com.machina.jikan_client_compose.data.local.shared_pref.SharedPrefsContract
import javax.inject.Inject

class GetContentSmallGridSizeUseCase @Inject constructor(
	private val prefs: SharedPrefsContract
) {

	operator fun invoke(): Int {
		return prefs.getContentSmallGridSize()
	}
}