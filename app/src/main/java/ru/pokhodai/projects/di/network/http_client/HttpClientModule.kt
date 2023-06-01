package ru.pokhodai.projects.di.network.http_client

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import ru.pokhodai.projects.di.annotations.MarvelHttpClient
import ru.pokhodai.projects.di.annotations.MarvelInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    @MarvelHttpClient
    fun provideMarvelHttpClient(
       @MarvelInterceptor interceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        connectTimeout(120L, TimeUnit.SECONDS)
        writeTimeout(120L, TimeUnit.SECONDS)
        readTimeout(120L, TimeUnit.SECONDS)
    }.build()
}