package ru.pokhodai.projects.data.remote

import retrofit2.http.GET

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun getCharacters() {

    }
}