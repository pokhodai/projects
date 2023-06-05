package ru.pokhodai.projects.presentation.activity

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import ru.pokhodai.projects.core.base.BaseViewModel
import ru.pokhodai.projects.domain.use_case.marvel.CharacterUseCase
import ru.pokhodai.projects.utils.launchDefault
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CharacterUseCase
): BaseViewModel() {

    init {
        launchDefault {
            useCase().collect {

            }
        }

    }


}