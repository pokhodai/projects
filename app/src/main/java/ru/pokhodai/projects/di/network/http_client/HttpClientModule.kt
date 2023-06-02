package ru.pokhodai.projects.di.network.http_client

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.pokhodai.projects.App
import ru.pokhodai.projects.di.annotations.LoggingInterceptor
import ru.pokhodai.projects.di.annotations.MarvelHttpClient
import ru.pokhodai.projects.di.annotations.MarvelInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    @MarvelHttpClient
    fun provideMarvelHttpClient(
       @MarvelInterceptor interceptor: Interceptor,
       @LoggingInterceptor loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        addInterceptor(loggingInterceptor)
        connectTimeout(120L, TimeUnit.SECONDS)
        writeTimeout(120L, TimeUnit.SECONDS)
        readTimeout(120L, TimeUnit.SECONDS)
    }.build()

//    @Provides
//    @Singleton
//    fun provideCache(
//        app: App
//    ): Cache? {
//        var cache: Cache? = null
//        try {
//            cache = Cache(File(app.cacheDir, "http-cache"), (10 * 1024 * 1024).toLong())
//        } catch (e: Exception) {
//            Log.d("Test", "Could not create Cache!")
//        }
//        return cache
//    }
}