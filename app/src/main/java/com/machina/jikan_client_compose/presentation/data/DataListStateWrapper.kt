package com.machina.jikan_client_compose.presentation.data

import com.machina.jikan_client_compose.core.wrapper.Event

data class DataListStateWrapper<T>(
	val data: List<T> = listOf(),
	val isLoading: Boolean = false,
	val error: Event<String?> = Event(null)
) {
	companion object {
		fun <T> loading(): DataListStateWrapper<T> {
			return DataListStateWrapper(isLoading = true)
		}

		fun <T> default(): DataListStateWrapper<T> {
			return DataListStateWrapper()
		}

		fun <T> DataListStateWrapper<T>.copyKeepData(
			data: DataListStateWrapper<T>
		): DataListStateWrapper<T> {
			return this.copy(
				isLoading = data.isLoading,
				error = data.error
			)
		}
	}
}
