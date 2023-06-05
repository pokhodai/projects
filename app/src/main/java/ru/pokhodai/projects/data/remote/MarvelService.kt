package ru.pokhodai.projects.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pokhodai.projects.model.response.marvel.CharacterResponse

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun characters(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("nameStartsWith") nameStartsWith: String? = null,
    ): Response<CharacterResponse>

    @GET("/v1/public/characters/{characterId}")
    suspend fun characterById(
        @Path("characterId") id: Int
    ): Response<CharacterResponse.Data>
}