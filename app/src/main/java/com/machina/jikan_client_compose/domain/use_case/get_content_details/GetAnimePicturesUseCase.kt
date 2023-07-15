package com.machina.jikan_client_compose.domain.use_case.get_content_details

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimePicturesUseCase @Inject constructor(
	private val repository: AnimeDetailsService,
	private val dispatchers: DispatchersProvider
) {
	operator fun invoke(malId: Int): Flow<StateListWrapper<String>> = flow {
		emit(StateListWrapper.loading())
		val state = when (val res = repository.getAnimePictures(malId)) {
			is Resource.Success -> {
				StateListWrapper(
					data = res.data?.data?.map {
						it.jpg.getHighestResImgUrl()
					}.orEmpty()
				)
			}
			is Resource.Error -> StateListWrapper.error(message = res.message)
		}

		emit(state)
	}.flowOn(dispatchers.io)
}