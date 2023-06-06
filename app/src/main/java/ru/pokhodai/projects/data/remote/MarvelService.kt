package ru.pokhodai.projects.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pokhodai.projects.model.response.marvel.CharacterResponse
import ru.pokhodai.projects.model.response.marvel.ComicsResponse

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun characters(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Response<CharacterResponse>

    @GET("/v1/public/characters/{characterId}")
    suspend fun characterById(
        @Path("characterId") id: Int
    ): Response<CharacterResponse.Data>

    @GET("/v1/public/comics")
    suspend fun comics(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Response<ComicsResponse>

    @GET("/v1/public/comics/{comicsId}")
    suspend fun comicsById(
        @Path("comicsId") id: Int
    ): Response<ComicsResponse.Data>
}