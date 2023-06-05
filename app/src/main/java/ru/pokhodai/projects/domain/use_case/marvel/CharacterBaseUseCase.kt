package ru.pokhodai.projects.domain.use_case.marvel

import kotlinx.coroutines.flow.Flow
import ru.pokhodai.projects.core.base.BaseUseCase
import ru.pokhodai.projects.data.repository.MarvelRepository
import ru.pokhodai.projects.model.response.marvel.CharacterResponse
import ru.pokhodai.projects.utils.ApiResult
import javax.inject.Inject

class CharacterBaseUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) : BaseUseCase() {

    suspend operator fun invoke(): Flow<ApiResult<CharacterResponse.Data>> =
        run(marvelRepository.getCharacters()) {
            it.data
        }

    suspend operator fun invoke(id: Int) = run(marvelRepository.getCharacterById(id)) { it.results }
}