package ru.pokhodai.projects.di.network.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.pokhodai.projects.data.remote.MarvelService
import ru.pokhodai.projects.di.annotations.MarvelApiRetrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MarvelServiceModule {

    @Provides
    @Singleton
    fun provideMarvelService(@MarvelApiRetrofit retrofit: Retrofit): MarvelService =
        retrofit.create(MarvelService::class.java)
}