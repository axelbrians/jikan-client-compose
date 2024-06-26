package com.machina.jikan_client_compose.di

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.use_case.anime.GetHomeSectionsUseCase
import com.machina.jikan_client_compose.domain.use_case.anime.GetHomeSectionsUseCaseImpl
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCaseImpl
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCaseImpl
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
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

	@Provides
	@ViewModelScoped
	fun provideGetAnimeScheduleUseCase(
		service: AnimeService,
		dispatchers: DispatchersProvider
	): GetAnimeScheduleUseCase {
		return GetAnimeScheduleUseCaseImpl(service, dispatchers)
	}

	@Provides
	@ViewModelScoped
	fun provideGetAnimeTopUseCase(
		service: AnimeService,
		dispatchers: DispatchersProvider
	): GetAnimeTopUseCase {
		return GetAnimeTopUseCase(service, dispatchers)
	}

	@Provides
	@ViewModelScoped
	fun provideHomeContentUseCase(
		animeAiringPopularUseCase: GetAnimeAiringPopularUseCase,
		animeScheduleUseCase: GetAnimeScheduleUseCase,
		animeTopUseCase: GetAnimeTopUseCase,
		dispatchers: DispatchersProvider
	): GetHomeSectionsUseCase {
		return GetHomeSectionsUseCaseImpl(
			airingPopularUseCase = animeAiringPopularUseCase,
			animeScheduleUseCase = animeScheduleUseCase,
			animeTopUseCase = animeTopUseCase
		)
	}
}