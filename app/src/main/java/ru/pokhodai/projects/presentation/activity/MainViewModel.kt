package ru.pokhodai.projects.presentation.activity

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pokhodai.projects.core.base.BaseViewModel
import ru.pokhodai.projects.domain.CharactersUseCase
import ru.pokhodai.projects.utils.launchDefault
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CharactersUseCase
): BaseViewModel() {

    init {
        launchDefault {
            useCase().collect() {

            }
        }

    }


}