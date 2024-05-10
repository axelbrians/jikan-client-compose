package com.machina.jikan_client_compose.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.data.local.shared_pref.SharedPrefsAccess
import com.machina.jikan_client_compose.data.local.shared_pref.SharedPrefsContract
import com.machina.jikan_client_compose.data.local.shared_pref.SharedPrefsKey
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.data.remote.anime.AnimeServiceImpl
import com.machina.jikan_client_compose.data.remote.anime.MangaService
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.data.remote.anime_search.AnimeSearchService
import com.machina.jikan_client_compose.data.repository.AnimeDetailsRepository
import com.machina.jikan_client_compose.data.repository.AnimeSearchRepository
import com.machina.jikan_client_compose.data.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

	@Provides
	@Singleton
	fun provideDispatchersProvider(): DispatchersProvider {
		return DefaultDispatchers(
			default = Dispatchers.Default,
			main = Dispatchers.Main,
			io = Dispatchers.IO,
			mainImmediate = Dispatchers.Main,
			unconfined = Dispatchers.Unconfined
		)
	}

	@Provides
	@Singleton
//  @AndroidKtorClient
	fun provideKtorClient(): HttpClient {
		return HttpClient(Android) {
			expectSuccess = false
			engine {
				connectTimeout = 15_000
				socketTimeout = 100_000
			}
			installJsonFeature()
			install(Logging) {
				logger = Logger.DEFAULT
				level = LogLevel.HEADERS
			}
			install(DefaultRequest) {
				method = HttpMethod.Get
			}
		}
	}

	@Provides
	@Singleton
	@AndroidKtorClient // @AndroidOkHttpClient
	fun provideOkHttpClient(@ApplicationContext context: Context): HttpClient {
		return HttpClient(OkHttp) {
			expectSuccess = false
			engine {
				addInterceptor(
					ChuckerInterceptor.Builder(context)
						.collector(ChuckerCollector(context))
						.maxContentLength(250000L)
						.redactHeaders(emptySet())
						.alwaysReadResponseBody(true)
						.build()
				)
			}

			installJsonFeature()
		}
	}

	private fun<T : HttpClientEngineConfig> HttpClientConfig<T>.installJsonFeature() {
		install(JsonFeature) {
			serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
				ignoreUnknownKeys = true
				coerceInputValues = true
				prettyPrint = true
			})
		}
	}

	@Provides
	@Singleton
	fun provideSharedPrefsContract(
		@ApplicationContext context: Context
	): SharedPrefsContract {
		return SharedPrefsAccess(
			context.getSharedPreferences(SharedPrefsKey.Group.General, 0)
		)
	}

	@Provides
	@Singleton
	fun provideAnimeService(
		@AndroidKtorClient client: HttpClient
	): AnimeService {
		return AnimeServiceImpl(client)
	}

	@Provides
	@Singleton
	fun provideAnimeDetailsService(
		@AndroidKtorClient client: HttpClient
	): AnimeDetailsService {
		return AnimeDetailsRepository(client)
	}

	@Provides
	@Singleton
	fun provideAnimeSearchService(
		@AndroidKtorClient client: HttpClient
	): AnimeSearchService {
		return AnimeSearchRepository(client)
	}

	@Provides
	@Singleton
	fun provideMangaService(
		@AndroidKtorClient client: HttpClient
	): MangaService {
		return MangaRepository(client)
	}

}