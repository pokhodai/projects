package ru.pokhodai.projects.di.network.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.pokhodai.projects.di.annotations.BaseUrlMarvel
import ru.pokhodai.projects.di.annotations.MarvelApiRetrofit
import ru.pokhodai.projects.di.annotations.MarvelHttpClient

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @MarvelApiRetrofit
    fun provideMarvelRetrofit(
        @BaseUrlMarvel baseUrlMarvel: String,
        @MarvelHttpClient client: OkHttpClient,
        factory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(factory)
            .client(client)
            .baseUrl(baseUrlMarvel)
            .build()
    }
}