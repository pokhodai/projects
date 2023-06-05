package ru.pokhodai.projects.presentation.activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pokhodai.projects.domain.use_case.marvel.CharacterUseCase
import ru.pokhodai.projects.utils.launchDefault
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CharacterUseCase
): ViewModel() {

    init {
        launchDefault {

        }
    }


}