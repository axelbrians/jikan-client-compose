package com.machina.jikan_client_compose.presentation.data

import com.machina.jikan_client_compose.core.wrapper.Event

data class DataStateWrapper<T>(
	val data: T,
	val isLoading: Boolean = false,
	val error: Event<String?> = Event(null)
) {
	companion object {
		fun <T> loading(default: T): DataStateWrapper<T> {
			return DataStateWrapper(data = default, isLoading = true)
		}

		fun <T> default(default: T): DataStateWrapper<T> {
			return DataStateWrapper(data = default)
		}

		fun <T> DataStateWrapper<T>.copyKeepData(
			data: DataStateWrapper<T>
		): DataStateWrapper<T> {
			return this.copy(
				isLoading = data.isLoading,
				error = data.error
			)
		}
	}
}
