package com.machina.jikan_client_compose.presentation.content_small_view_all.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.use_case.pref_content_small_grid_size.GetContentSmallGridSizeUseCase
import com.machina.jikan_client_compose.domain.use_case.pref_content_small_grid_size.SetContentSmallGridSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentSmallGridSizeViewModel @Inject constructor(
	private val getContentSmallGridSizeUseCase: GetContentSmallGridSizeUseCase,
	private val setContentSmallGridSizeUseCase: SetContentSmallGridSizeUseCase
): ViewModel() {

	private val _gridSize : MutableState<Int> = mutableStateOf(getContentSmallGridSizeUseCase())
	val gridSize : State<Int> = _gridSize

	fun getGridSize() {
		viewModelScope.launch {
			_gridSize.value = getContentSmallGridSizeUseCase()
		}
	}

	fun setGridSize() {
		viewModelScope.launch {
			setContentSmallGridSizeUseCase()
			getGridSize()
		}
	}

}