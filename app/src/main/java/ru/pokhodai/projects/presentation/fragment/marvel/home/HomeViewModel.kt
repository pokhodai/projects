package ru.pokhodai.projects.presentation.fragment.marvel.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.pokhodai.projects.domain.use_case.marvel.CharacterUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
): ViewModel() {


}