package com.machina.jikan_client_compose.domain.use_case.pref_content_small_grid_size

import com.machina.jikan_client_compose.data.local.shared_pref.SharedPrefsContract
import javax.inject.Inject

class SetContentSmallGridSizeUseCase @Inject constructor(
	private val prefsContract: SharedPrefsContract
) {

	operator fun invoke() {
		val value = if (prefsContract.getContentSmallGridSize() == 4) 3 else 4
		prefsContract.writeContentSmallGridSize(value)
	}
}