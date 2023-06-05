package ru.pokhodai.projects.domain.character_use_case

import kotlinx.coroutines.flow.Flow
import ru.pokhodai.projects.core.base.BaseUseCase
import ru.pokhodai.projects.data.repository.MarvelRepository
import ru.pokhodai.projects.model.response.marvel.CharacterResponse
import ru.pokhodai.projects.utils.UIState
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
): BaseUseCase() {

    override suspend fun invoke(): Flow<UIState<CharacterResponse.Data>> {
        return run(flow = marvelRepository.getCharacters()) {
            it.data
        }
    }
}