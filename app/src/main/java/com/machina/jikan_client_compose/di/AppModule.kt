package com.machina.jikan_client_compose.di

import com.machina.jikan_client_compose.core.Call
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
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
  fun provideCall(): Call {
    return Call()
  }

  @Provides
  fun provideAnimeRepositoryImplKtor(client: HttpClient, call: Call): AnimeRepositoryImpl {
    return AnimeRepositoryImpl(client, call)
  }
}