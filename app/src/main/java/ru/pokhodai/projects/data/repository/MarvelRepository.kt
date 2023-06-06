package ru.pokhodai.projects.data.repository

import ru.pokhodai.projects.core.base.BaseRepository
import ru.pokhodai.projects.data.remote.MarvelService
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val marvelService: MarvelService,
): BaseRepository() {

    fun getCharacters(
        limit: Int,
        offset: Int
    ) = toResultFlow {
        marvelService.characters(
            limit = limit,
            offset = offset
        )
    }

    fun getCharacterById(id: Int) = toResultFlow {
        marvelService.characterById(id = id)
    }

    fun getComics(limit: Int, offset: Int) = toResultFlow {
        marvelService.comics(
            limit = limit,
            offset = offset
        )
    }

    fun getComicsById(id: Int) = toResultFlow {
        marvelService.comicsById(id = id)
    }
}