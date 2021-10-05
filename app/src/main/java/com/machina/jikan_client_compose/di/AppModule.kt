package com.machina.jikan_client_compose.di

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.data.network.NetworkResponseAdapterFactory
import com.machina.jikan_client_compose.data.network.SafeCall
import com.machina.jikan_client_compose.data.source.AnimeService
import com.machina.jikan_client_compose.data.utils.ErrorConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideErrorConverter(retrofit: Retrofit): ErrorConverter {
        return ErrorConverter(retrofit)
    }
}