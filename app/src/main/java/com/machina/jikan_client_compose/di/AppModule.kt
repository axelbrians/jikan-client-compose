package com.machina.jikan_client_compose.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.SafeCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.okhttp.*
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
//  @AndroidKtorClient
  fun provideKtorClient(): HttpClient {
    return HttpClient(Android) {
      engine {
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
  @AndroidKtorClient // @AndroidOkHttpClient
  fun provideOkHttpClient(@ApplicationContext context: Context): HttpClient {
    return HttpClient(OkHttp) {
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

      install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
          ignoreUnknownKeys = true
          coerceInputValues = true
        })
      }
    }
  }

  @Provides
  @Singleton
  fun provideCall(): SafeCall {
    return SafeCall()
  }
}