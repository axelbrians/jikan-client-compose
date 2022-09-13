package com.machina.jikan_client_compose.presentation.data

import com.machina.jikan_client_compose.core.wrapper.Event

data class StateListWrapper<T>(
	val data: List<T> = listOf(),
	val isLoading: Boolean = false,
	val error: Event<String?> = Event(null)
) {
	companion object {
		fun <T> loading(): StateListWrapper<T> {
			return StateListWrapper(isLoading = true)
		}

		fun <T> default(): StateListWrapper<T> {
			return StateListWrapper()
		}

		fun <T> StateListWrapper<T>.copyKeepData(
			data: StateListWrapper<T>
		): StateListWrapper<T> {
			return this.copy(
				isLoading = data.isLoading,
				error = data.error
			)
		}
	}
}
