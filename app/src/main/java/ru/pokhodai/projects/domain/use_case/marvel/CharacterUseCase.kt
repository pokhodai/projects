package ru.pokhodai.projects.domain.use_case.marvel

import ru.pokhodai.projects.core.base.BaseUseCase
import ru.pokhodai.projects.data.repository.MarvelRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) : BaseUseCase() {

    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ) = run(
        marvelRepository.getCharacters(
            limit = limit,
            offset = offset
        )
    ) {
        it.data.results
    }

    suspend operator fun invoke(id: Int) = run(marvelRepository.getCharacterById(id)) {
        it.results
    }
}