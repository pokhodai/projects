package ru.pokhodai.projects.di.network.interceptor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import ru.pokhodai.projects.core.Constant
import ru.pokhodai.projects.di.annotations.LoggingInterceptor
import ru.pokhodai.projects.di.annotations.MarvelInterceptor
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    @MarvelInterceptor
    fun provideMarvelInterceptor(): Interceptor = Interceptor { chain ->
        val ts = Timestamp(System.currentTimeMillis())
        val hash = "${ts.time}${Constant.PRIVATE_API_KEY}${Constant.PUBLIC_API_KEY}"
        val hashInMD5 = BigInteger(1, MessageDigest
            .getInstance("MD5")
            .digest(hash.toByteArray()))
            .toString(16)
            .padStart(32, '0')

        val httpUrl = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter("apikey", Constant.PUBLIC_API_KEY)
            .addQueryParameter("ts", ts.time.toString())
            .addQueryParameter("hash", hashInMD5)
            .build()

        chain.proceed(chain.request().newBuilder().url(httpUrl).build())
    }

    @Provides
    @Singleton
    @LoggingInterceptor
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}