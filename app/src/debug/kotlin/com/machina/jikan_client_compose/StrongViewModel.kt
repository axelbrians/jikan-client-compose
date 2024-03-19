package com.machina.jikan_client_compose

import androidx.lifecycle.ViewModel
import com.machina.jikan_client_compose.StrongArgumentNavigation.StrongArgument
import timber.log.Timber

class StrongViewModel: ViewModel() {

	private var startTimestamp: Long = 0L
	private var endTimestamp: Long = 0L

	var argument: StrongArgument = StrongArgument.Empty
		set(value) {
			start()
			field = value
		}
		get() {
			end()
			return field
		}


	fun start() {
		Timber.tag("puyo").d("=== Start ===")
		startTimestamp = System.currentTimeMillis()
	}

	fun end() {
		endTimestamp = System.currentTimeMillis()
		Timber.tag("puyo").d("=== End ===")

		Timber.tag("puyo").d("elapsed: ${endTimestamp - startTimestamp}")
	}
}