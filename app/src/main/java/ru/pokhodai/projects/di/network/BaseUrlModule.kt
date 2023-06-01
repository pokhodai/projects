package ru.pokhodai.projects.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.pokhodai.projects.di.annotations.BaseUrlMarvel

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @Provides
    @BaseUrlMarvel
    fun provideMarvelBaseUrl(): String = "https://gateway.marvel.com:443/"
}