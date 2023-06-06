package ru.pokhodai.projects.presentation.fragment.marvel.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import ru.pokhodai.projects.core.base.BaseViewModel
import ru.pokhodai.projects.domain.use_case.marvel.CharacterUseCase
import ru.pokhodai.projects.domain.use_case.marvel.ComicsUseCase
import ru.pokhodai.projects.model.response.marvel.CharacterResponse
import ru.pokhodai.projects.utils.launchDefault
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val comicsUseCase: ComicsUseCase,
): BaseViewModel() {

    private val _charactersStateFlow = MutableUIStateFlow<List<CharacterResponse.Data.Result>>()
    val charactersStateFlow = _charactersStateFlow.asStateFlow()

    fun onLoadCharacters() {
        launchDefault {
            characterUseCase(limit = 10, 0)
                .collectRequest(_charactersStateFlow)
        }
    }
}