package ru.pokhodai.projects.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import ru.pokhodai.projects.App
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(@ApplicationContext context: Context): App = context.applicationContext as App

    @Provides
    @Singleton
    fun provideKotlinConvertFactory(json: Json): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideJson() = Json { ignoreUnknownKeys = true }
}