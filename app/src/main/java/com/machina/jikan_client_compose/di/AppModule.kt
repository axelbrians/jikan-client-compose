package com.machina.jikan_client_compose.di

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.data.network.SafeCall
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.MangaService
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImplKtor
import com.machina.jikan_client_compose.data.repository.MangaRepositoryImpl
import com.machina.jikan_client_compose.data.utils.ErrorConverter
import com.machina.jikan_client_compose.domain.repository.AnimeRepository
import com.machina.jikan_client_compose.domain.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun provideDefaultDispatchers(): DefaultDispatchers {
    return DefaultDispatchers()
  }

  @Provides
  @Singleton
  fun provideSafeCall(): SafeCall {
    return SafeCall()
  }

  @Provides
  @Singleton
  fun provideKtorClient(): HttpClient {
    return HttpClient(Android) {
      engine {
        // this: AndroidEngineConfig
        connectTimeout = 15_000
        socketTimeout = 100_000
      }
      install(JsonFeature) {
        serializer = KotlinxSerializer()
      }
      install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
      }
    }
  }

  @Provides
  fun provideAnimeRepositoryImplKtor(client: HttpClient): AnimeRepositoryImplKtor {
    return AnimeRepositoryImplKtor(client)
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(Endpoints.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideAnimeService(retrofit: Retrofit): AnimeService {
    return retrofit.create(AnimeService::class.java)
  }

  @Provides
  @Singleton
  fun provideMangaService(retrofit: Retrofit): MangaService {
    return retrofit.create(MangaService::class.java)
  }

  @Provides
  @Singleton
  fun provideErrorConverter(retrofit: Retrofit): ErrorConverter {
    return ErrorConverter(retrofit)
  }

  @Provides
  @Singleton
  fun provideAnimeRepository(
    service: AnimeService,
    errorConverter: ErrorConverter,
    safeCall: SafeCall
  ): AnimeRepository {
    return AnimeRepositoryImpl(service, errorConverter, safeCall)
  }

  @Provides
  @Singleton
  fun provideMangaRepository(
    service: MangaService,
    errorConverter: ErrorConverter,
    safeCall: SafeCall
  ): MangaRepository {
    return MangaRepositoryImpl(service, errorConverter, safeCall)
  }
}