package ru.pokhodai.projects.domain.use_case.marvel

import ru.pokhodai.projects.core.base.BaseUseCase
import ru.pokhodai.projects.data.repository.MarvelRepository
import javax.inject.Inject

class ComicsUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) : BaseUseCase() {

    suspend operator fun invoke(limit: Int, offset: Int) =
        run(marvelRepository.getComics(limit = limit, offset = offset)) {
            it.data.results
        }

    suspend operator fun invoke(id: Int) =
        run(marvelRepository.getComicsById(id)) {
            it.results
        }
}