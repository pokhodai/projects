package ru.pokhodai.projects.data.repository

import kotlinx.coroutines.flow.collect
import kotlinx.serialization.builtins.serializer
import ru.pokhodai.projects.core.base.BaseRepository
import ru.pokhodai.projects.data.remote.MarvelService
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val marvelService: MarvelService
): BaseRepository() {

    suspend fun getCharacters(
        limit: Int = 10,
        offset: Int = 0
    ) = toResultFlow {
        marvelService.characters(
            limit = limit,
            offset = offset
        )
    }
}