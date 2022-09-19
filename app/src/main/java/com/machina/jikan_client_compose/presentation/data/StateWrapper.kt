package com.machina.jikan_client_compose.presentation.data

import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.core.wrapper.Event

data class StateWrapper<T>(
	val data: T? = null,
	val isLoading: Boolean = false,
	val error: Event<String?> = Event(null)
) {

	companion object {
//		fun <T> loading(default: T): StateWrapper<T> {
//			return StateWrapper(data = default, isLoading = true)
//		}
//
//		fun <T> default(default: T): StateWrapper<T> {
//			return StateWrapper(data = default)
//		}

		fun <T> loading(): StateWrapper<T> {
			return StateWrapper(isLoading = true)
		}

		fun <T> default(): StateWrapper<T> {
			return StateWrapper()
		}

		fun <T> error(message: String?): StateWrapper<T> {
			return StateWrapper(error = Event(message ?: MyError.UNKNOWN_ERROR))
		}

		fun <T> StateWrapper<T>.isSuccess(): Boolean {
			return error.peekContent() == null && data != null
		}

		fun <T> StateWrapper<T>.copyKeepData(
			state: StateWrapper<T>
		): StateWrapper<T> {
			return this.copy(
				isLoading = state.isLoading,
				error = state.error
			)
		}

		fun <T> StateWrapper<T>.copyNewError(
			error: String
		): StateWrapper<T> {
			return this.copy(
				error = Event(error)
			)
		}
	}
}
