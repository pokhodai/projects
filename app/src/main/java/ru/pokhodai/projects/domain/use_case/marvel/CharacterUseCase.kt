package ru.pokhodai.projects.domain.use_case.marvel

import kotlinx.coroutines.flow.Flow
import ru.pokhodai.projects.core.base.UseCase
import ru.pokhodai.projects.data.repository.MarvelRepository
import ru.pokhodai.projects.model.response.marvel.CharacterResponse
import ru.pokhodai.projects.utils.ApiResult
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
): UseCase() {

    suspend operator fun invoke(): Flow<ApiResult<CharacterResponse.Data>> {
        return run(marvelRepository.getCharacters()) {
            it.data
        }
    }

    suspend operator fun invoke(id: Int) = run(marvelRepository.getCharacterById(id)) {
        it.results
    }


}