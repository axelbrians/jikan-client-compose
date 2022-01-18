package com.machina.jikan_client_compose.di

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
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
  fun provideKtorClient(): HttpClient {
    return HttpClient(Android) {
      engine {
        // this: AndroidEngineConfig
        connectTimeout = 15_000
        socketTimeout = 100_000
      }
      install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
          ignoreUnknownKeys = true
          coerceInputValues = true
        })
      }
      install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
      }
    }
  }

  @Provides
  @Singleton
  fun provideCall(): SafeCall {
    return SafeCall()
  }

  @Provides
  fun provideAnimeRepositoryImplKtor(client: HttpClient, call: SafeCall): AnimeRepository {
    return AnimeRepository(client, call)
  }
}