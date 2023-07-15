package com.machina.jikan_client_compose.presentation.content_detail_screen.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.use_case.anime_characters.GetAnimeCharactersUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_recommendations.GetAnimeRecommendationsUseCase
import com.machina.jikan_client_compose.domain.use_case.get_content_details.GetAnimePicturesUseCase
import com.machina.jikan_client_compose.domain.use_case.get_content_details.GetContentDetailsUseCase
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentDetailsViewModel @Inject constructor(
  private val getContentDetailsUseCase: GetContentDetailsUseCase,
  private val getAnimeCharactersUseCase: GetAnimeCharactersUseCase,
  private val getAnimeRecommendationsUseCase: GetAnimeRecommendationsUseCase,
  private val getAnimePicturesUseCase: GetAnimePicturesUseCase
): ViewModel() {

  private val _contentDetailsState : MutableState<ContentDetailsState> = mutableStateOf(ContentDetailsState.Loading)
  val contentDetailsState : State<ContentDetailsState> = _contentDetailsState

  private val _animeCharactersState : MutableState<StateListWrapper<AnimePortraitDataModel>> = mutableStateOf(StateListWrapper.loading())
  val animeCharactersState : State<StateListWrapper<AnimePortraitDataModel>> = _animeCharactersState

  private val _animeRecommendationsState : MutableState<StateListWrapper<AnimePortraitDataModel>> = mutableStateOf(StateListWrapper.loading())
  val animeRecommendationsState : State<StateListWrapper<AnimePortraitDataModel>> = _animeRecommendationsState

  private val _animePicturesState : MutableState<StateListWrapper<String>> = mutableStateOf(StateListWrapper.loading())
  val animePicturesState : State<StateListWrapper<String>> = _animePicturesState



  fun getContentDetails(contentType: String?, malId: Int?) {
    getContentDetailsUseCase(contentType, malId).onEach { state ->
      _contentDetailsState.value = state
    }.launchIn(viewModelScope)
  }

  fun getAnimeCharacters(malId: Int) {
    getAnimeCharactersUseCase(malId).onEach { state ->
      _animeCharactersState.value = state
    }.launchIn(viewModelScope)
  }

  fun getAnimeRecommendations(malId: Int) {
    getAnimeRecommendationsUseCase.getAsStateListWrapper(malId).onEach { state ->
      _animeRecommendationsState.value = state
    }.launchIn(viewModelScope)
  }

  fun getAnimePictures(malId: Int) {
    getAnimePicturesUseCase(malId).onEach { state ->
      _animePicturesState.value = state
    }.launchIn(viewModelScope)
  }
}