package ru.pokhodai.projects.domain.use_case.marvel

import ru.pokhodai.projects.core.base.BaseUseCase
import ru.pokhodai.projects.data.repository.MarvelRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) : BaseUseCase() {

    suspend operator fun invoke() = run(marvelRepository.getCharacters()) {
        it.data
    }

    suspend operator fun invoke(id: Int) = run(marvelRepository.getCharacterById(id)) {
        it.results
    }
}