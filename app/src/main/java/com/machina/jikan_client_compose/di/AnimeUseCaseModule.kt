package com.machina.jikan_client_compose.di

import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AnimeUseCaseModule {

	@Provides
	@ViewModelScoped
	fun provideGetAnimeAiringPopularUseCase(service: AnimeService): GetAnimeAiringPopularUseCase {
		return GetAnimeAiringPopularUseCaseImpl(service)
	}
}